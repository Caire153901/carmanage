
/* 启动时加载 */
$(function(){
    $("#user_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/user/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModified',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectUser(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '用户ID', hidden: 'true'},
            { field: 'roleId', title: '角色ID', hidden: 'true'},
            { field: 'account', title: '用户名',sortable:true, width:100, align: 'left', halign: 'center',align: 'center'},
            { field: 'roleName', title: '角色',sortable:true, width:50, halign: 'center',align: 'center'},
            { field: 'userName', title: '真实姓名',  align: 'left', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'authorityNames', title: '权限',  align: 'left', halign: 'center',width:100,align: 'center'},
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
    getUseRole();

});
//选中后按钮状态
function selectUser(row){
    var state=row.useStatus;
    $('#user_edit').linkbutton('enable');//修改按钮可用
    $('#user_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#user_start').linkbutton('enable');//启用按钮可用
        $('#user_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#user_dis').linkbutton('enable');//禁用按钮可用
        $('#user_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#user_edit').linkbutton('disable');//修改按钮
    $('#user_remove').linkbutton('disable');//删除按钮
    $('#user_start').linkbutton('disable');//启用按钮
    $('#user_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var userName = $('#userName').val(); //用户真实姓名
    var account = $('#account').val(); //用户真实姓名
    var roleId = $('#roleSelect').val(); //用户真实姓名
    $('#user_tab').datagrid('options').url=getRootPath__()+'/user/list';
    $('#user_tab').datagrid('reload',{
        userName:userName,
        account:account,
        roleId:roleId,
    });
}
/** 重置 **/
function reset(){
    $('#userName').val("");
    $('#account').val("");
    $('#roleSelect').combobox('setValue', '');
    $('#user_tab').datagrid('load',{});
}
/** 新增初始化 */
function addUser(){
    $('#password_tr').show();
    $('#userForm').form('clear');
    $('#user_state').combobox('setValue','0');
    $('#user_state').combobox('disable');
    getUseRole();
    $("#user_data").dialog("setTitle","添加用户信息").dialog("open");
}
/**用户角色*/
function getUseRole(){
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/role/select_list',
        complete:function(data){
            if(data.status=="200"){
                var roleData = data.responseJSON.data;
                $('#role_id').combobox({
                    data:roleData,
                    valueField:'id',
                    textField:'roleName',
                });
                $('#roleSelect').combobox({
                    data:roleData,
                    valueField:'id',
                    textField:'roleName',
                });

            }
        }
    });

}

/**编辑初始化*/
function editUser() {
    var selectRows = $("#user_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        var roleId=selectRows[0].roleId;
        if(!selectRows[0].state){
            state=1;//启用
        }

        $('#userForm').form('load', selectRows[0]);//表单加载
        $('#userForm').form('load', getRootPath__() + '/user/getUser?id=' + id);//表单加载

        $('#user_state').combobox('setValue',state);//状态下拉框赋值
        getUseRole();//用户类型初始化
        $('#role_id').combobox('select',roleId);//用户类型下拉框赋值
        $('#user_state').combobox('enable');
        var password=$('#user_userpwd').val();
        $('#user_repwd').val(password);
        $('#password_tr').hide();
        $('#user_data').dialog('open').dialog('setTitle', '编辑用户');
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
        $("#userForm").form("submit", {
            url: getRootPath__()+'/user/saveOrUpdateUser',
            onsubmit: function () {
                return $(this).form("validate");
            },
            success: function (result) {
                var re = JSON.parse(result);
                if (re.data) {
                    msg("提交成功！");
                    $("#user_tab").datagrid("load");
                    $("#user_data").dialog("close");
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
function deleteUser() {
    var selectRows = $("#user_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/user/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#user_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#user_tab').datagrid('load');
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
    var selectRows = $("#user_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/user/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#user_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("启用失败！")
                            $('#user_tab').datagrid('load');
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
    var selectRows = $("#user_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/user/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#user_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("禁用失败！");
                            $('#user_tab').datagrid('load');
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