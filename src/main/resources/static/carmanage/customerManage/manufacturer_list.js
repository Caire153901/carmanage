
/* 启动时加载 */
$(function(){
    $("#manufacturer_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/manufacturer/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectManufacturer(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '厂商ID', hidden: 'true'},
            { field: 'manufacturerCode', title: '厂商编号',sortable:true, width:80, align: 'left', halign: 'center',align: 'center'},
            { field: 'manufacturerName', title: '厂商名',sortable:true, width:50, align: 'left', halign: 'center',align: 'center'},
            { field: 'linkman', title: '联系人',sortable:true, width:50, align: 'left', halign: 'center',align: 'center'},
            { field: 'telphone', title: '联系电话',  align: 'left', halign: 'center',sortable:true,width:80,align: 'center'},
            { field: 'postcode', title: '邮编',  align: 'left', halign: 'center',width:80,align: 'center'},
            { field: 'address', title: '地址',  align: 'left', halign: 'center',width:120,align: 'center'},
            { field: 'useStatus', title: '状态',  align: 'center', halign: 'center',sortable:true,width:30,
                formatter: function(value,row,index){
                    if (value=='0'){
                        return "<span class='iconfont icon-chenggong' style='color:#1AE61A'></span>";
                    }
                    if (value=='2'){
                        return "<span class='iconfont icon-iconset0187' style='color:red'></span>";
                    }
                    if(value =='1'){
                        return "<span class='iconfont icon-dengpao' style='color:#aa00ff'></span>";
                    }
                }
            }
        ]]
    });
});


function getManufacturerCode() {
    var Code="CS";
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/manufacturer/manufacturer_code?config='+Code,
        complete:function(data){
            if(data.status=="200"){
                var manufacturerCode = data.responseJSON.data;
                $("#manufacturer_code").val(manufacturerCode.manufacturerCode);
            }
        }
    });

}
//选中后按钮状态
function selectManufacturer(row){
    var state=row.useStatus;
    $('#manufacturer_edit').linkbutton('enable');//修改按钮可用
    $('#manufacturer_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#manufacturer_start').linkbutton('enable');//启用按钮可用
        $('#manufacturer_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#manufacturer_dis').linkbutton('enable');//禁用按钮可用
        $('#manufacturer_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#manufacturer_edit').linkbutton('disable');//修改按钮
    $('#manufacturer_remove').linkbutton('disable');//删除按钮
    $('#manufacturer_start').linkbutton('disable');//启用按钮
    $('#manufacturer_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var manufacturerName = $('#manufacturerName').val(); //客户名
    var manufacturerCode = $('#manufacturerCode').val(); //用户真实姓名
    $('#manufacturer_tab').datagrid('options').url=getRootPath__()+'/manufacturer/list';
    $('#manufacturer_tab').datagrid('reload',{
        manufacturerName:manufacturerName,
        manufacturerCode:manufacturerCode,
    });
}
/** 重置 **/
function reset(){
    $('#manufacturerName').val("");
    $('#manufacturerCode').val("");
    $('#provincialSelect').combobox('setValue', '');
    $('#manufacturer_tab').datagrid('load',{});
}
/** 新增初始化 */
function addManufacturer(){
    $('#manufacturerForm').form('clear');
    $('#manufacturer_state').combobox('setValue','0');
    $('#manufacturer_state').combobox('disable');
    getManufacturerCode();
    $("#manufacturer_data").dialog("setTitle","添加客户信息").dialog("open");
}

/**编辑初始化*/
function editManufacturer() {
    var selectRows = $("#manufacturer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        if(!selectRows[0].state){
            state=0;//启用
        }
        $('#manufacturerForm').form('load', selectRows[0]);//表单加载
        $('#manufacturer_state').combobox('setValue',state);//状态下拉框赋值
        $('#manufacturer_state').combobox('enable');
        $('#manufacturer_data').dialog('open').dialog('setTitle', '编辑客户信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#manufacturer_state').combobox('enable');
    $("#manufacturerForm").form("submit", {
        url: getRootPath__()+'/manufacturer/saveOrUpdateManufacturer',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#manufacturer_tab").datagrid("load");
                $("#manufacturer_data").dialog("close");
                buttonDisble();
            }else if(re.data==="异常"){
                $.messager.alert("提示信息", re.errorMsg, 'warning');
            }else{
                msg("保存数据失败！+,"+re.errorMsg);
            }
        }
    });
}

/**删除*/
function deleteManufacturer() {
    var selectRows = $("#manufacturer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/manufacturer/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的行！", 'info');
    }

}
/**启用*/
function startUsing(){
    var selectRows = $("#manufacturer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/manufacturer/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("启用失败！")
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }
                    }
                });

            }
        });
    } else {
        $.messager.alert("提示", "请选择要启用的行！", 'info');
    }
}
/**禁用*/
function disableUsing(){
    var selectRows = $("#manufacturer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/manufacturer/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("禁用失败！");
                            $('#manufacturer_tab').datagrid('load');
                            buttonDisble();
                        }
                    }

                }) ;
            }
        });
    } else {
        $.messager.alert("提示", "请选择要禁用的行！", 'info');
    }
}