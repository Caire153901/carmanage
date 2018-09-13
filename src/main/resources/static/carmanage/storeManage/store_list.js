
/* 启动时加载 */
$(function(){
    $("#store_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/store/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectStore(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '仓库ID', hidden: 'true'},
            { field: 'storeName', title: '仓库名',sortable:true, width:100, align: 'left', halign: 'center',align: 'center'},
            { field: 'address', title: '仓库地址',sortable:true, width:150, halign: 'center',align: 'center'},
            { field: 'maxCapacity', title: '仓库最大容量',  align: 'left', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'capacity', title: '仓库现有容量',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'marginCapacity', title: '仓库余量',  align: 'left', halign: 'center',width:100,align: 'center'},
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
function selectStore(row){
    var state=row.useStatus;
    $('#store_edit').linkbutton('enable');//修改按钮可用
    $('#store_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#store_start').linkbutton('enable');//启用按钮可用
        $('#store_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#store_dis').linkbutton('enable');//禁用按钮可用
        $('#store_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#store_edit').linkbutton('disable');//修改按钮
    $('#store_remove').linkbutton('disable');//删除按钮
    $('#store_start').linkbutton('disable');//启用按钮
    $('#store_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var storeName = $('#storeName').val(); //用户真实姓名
    $('#store_tab').datagrid('options').url=getRootPath__()+'/store/list';
    $('#store_tab').datagrid('reload',{
        storeName:storeName,
    });
}
/** 重置 **/
function reset(){
    $('#storeName').val("");
    $('#store_tab').datagrid('load',{});
}
/** 新增初始化 */
function addStore(){
    $('#storeForm').form('clear');
    $('#use_status').val(0);
    $('#store_capacity').val(0);
    $('#margin_capacity').val(0);
    $('#store_state').combobox('disable');
    $("#store_data").dialog("setTitle","添加用户信息").dialog("open");
}

/**编辑初始化*/
function editStore() {
    var selectRows = $("#store_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        $('#storeForm').form('load', selectRows[0]);//表单加载
        $('#store_state').combobox('enable');
        $('#store_data').dialog('open').dialog('setTitle', '编辑用户');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#store_state').combobox('enable');
    $("#storeForm").form("submit", {
        url: getRootPath__()+'/store/saveOrUpdateStoreInfo',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#store_tab").datagrid("load");
                $("#store_data").dialog("close");
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
function removeStore() {
    var selectRows = $("#store_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/store/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#store_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#store_tab').datagrid('load');
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
    var selectRows = $("#store_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/store/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#store_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("启用失败！")
                            $('#store_tab').datagrid('load');
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
    var selectRows = $("#store_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/store/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#store_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("禁用失败！");
                            $('#store_tab').datagrid('load');
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
