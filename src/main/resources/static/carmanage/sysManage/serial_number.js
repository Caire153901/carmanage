
/* 启动时加载 */
$(function(){
    $("#serialnumber_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/serial_number/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModified',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectSerialNumber(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '流水号ID', hidden: 'true'},
            { field: 'authorityId', title: '权限ID', hidden: 'true'},
            { field: 'authorityName', title: '菜单名',sortable:true, width:100, align: 'left', halign: 'center',align: 'center'},
            { field: 'configTemplet', title: '序列号模板',sortable:true, width:100, halign: 'center',align: 'center'},
            { field: 'currutSerial', title: '当前序列号',  align: 'center', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'maxSerial', title: '最大位数',  align: 'center', halign: 'center',width:100,align: 'center'},
            { field: 'useStatus', title: '状态',  align: 'center', halign: 'center',sortable:true,width:60,
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
//选中后按钮状态
function selectSerialNumber(row){
    var state=row.useStatus;
    $('#SerialNumber_edit').linkbutton('enable');//修改按钮可用
    $('#SerialNumber_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#SerialNumber_start').linkbutton('enable');//启用按钮可用
        $('#SerialNumber_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#SerialNumber_dis').linkbutton('enable');//禁用按钮可用
        $('#SerialNumber_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#SerialNumber_edit').linkbutton('disable');//修改按钮
    $('#SerialNumber_remove').linkbutton('disable');//删除按钮
    $('#SerialNumber_start').linkbutton('disable');//启用按钮
    $('#SerialNumber_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var authorityName = $('#authorityName').val();
    $('#serialnumber_tab').datagrid('options').url=getRootPath__()+'/serial_number/list';
    $('#serialnumber_tab').datagrid('reload',{
        authorityName:authorityName,
    });
}
/** 重置 **/
function reset(){
    $('#authorityName').val("");
    $('#serialnumber_tab').datagrid('load',{});
}
/** 新增初始化 */
function addSerialNumber(){
    $('#serialNumberForm').form('clear');
    $('#user_state').combobox('setValue','0');
    $('#user_state').combobox('disable');
    getAuthoritySelect();
    $("#serialNumber_data").dialog("setTitle","添加用户信息").dialog("open");
}


/**编辑初始化*/
function editSerialNumber() {
    var selectRows = $("#serialnumber_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        var authId=selectRows[0].authorityId;
        if(!selectRows[0].state){
            state=0;//启用
        }
        $('#serialNumberForm').form('load', selectRows[0]);//表单加载
        $('#user_state').combobox('setValue',state);//状态下拉框赋值
        $('#user_state').combobox('enable');
        getAuthoritySelect();
        $('#authority_id').combobox('setValue',authId);
        $('#serialNumber_data').dialog('open').dialog('setTitle', '编辑用户');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#user_state').combobox('enable');
    $("#serialNumberForm").form("submit", {
        url: getRootPath__()+'/serial_number/saveOrUpdateSerialNumber',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#serialnumber_tab").datagrid("load");
                $("#serialNumber_data").dialog("close");
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
function deleteSerialNumber() {
    var selectRows = $("#serialnumber_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/serial_number/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#serialnumber_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#serialnumber_tab').datagrid('load');
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
    var selectRows = $("#serialnumber_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/serial_number/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#serialnumber_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("启用失败！")
                            $('#serialnumber_tab').datagrid('load');
                            buttonDisble();
                        }
                    }
                });

            }
        });
    }
    else {
        $.messager.alert("提示", "请选择要启用的行！", 'info');
    }
}
/**禁用*/
function disableUsing(){
    var selectRows = $("#serialnumber_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/serial_number/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#serialnumber_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("禁用失败！");
                            $('#serialnumber_tab').datagrid('load');
                            buttonDisble();
                        }
                    }

                }) ;
            }
        });
    }
    else {
        $.messager.alert("提示", "请选择要禁用的行！", 'info');

    }
}

/** 菜单列表 **/
function getAuthoritySelect(){
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/authority/authority_select',
        complete:function(data){
            if(data.status=="200"){
                var authorityData = data.responseJSON.data;
                $('#authority_id').combobox({
                    data:authorityData,
                    valueField:'id',
                    textField:'authorityName',
                });
            }
        }
    });

}
