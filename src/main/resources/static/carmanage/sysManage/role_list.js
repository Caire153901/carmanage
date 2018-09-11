
/* 启动时加载 */
$(function(){
    $("#role_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/role/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModified',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectRole(row);
        },
        onLoadSuccess:function(data){
            buttonDisbles();
        },
        columns: [[
            { field: 'id', title: '角色ID', hidden: 'true'},
            { field: 'roleName', title: '角色名',sortable:true, width:100, align: 'left', halign: 'center',align: 'center'},
            { field: 'roleNote', title: '描述',sortable:true, width:50, halign: 'center',align: 'center'},
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

    // 初始化树形表格
    $('#tt').treegrid({
        url: getRootPath__() + '/authority/list',
        idField: 'id',
        fit : true,
        treeField: 'authorityName',
        iconCls: 'icon-ok',
        fitColumns : true,
        animate: true,
        collapsible: true,
        checkbox: true,
        method:'get',
        columns: [[
            {title: 'id', field: 'id', width: 180,  hidden: 'true'},
            {title: '资源名称', field: 'authorityName', width: 180 },
            {title: '图标', field: 'icon', width: 180},
        ]],
        loadFilter: function (data) {
            return recursiveModify(data.data,"icon","icon",function (oldProperty) {
                return "<span class='iconfont icon-" + oldProperty + "'></span>";
            });
        }
    });
});
//按钮禁用初始化
function buttonDisbles(){
    $('#role_edit').linkbutton('disable');//修改按钮
    $('#role_del').linkbutton('disable');//删除按钮
    $('#role_start').linkbutton('disable');//启用按钮
    $('#role_dis').linkbutton('disable');//禁用按钮
    $("#saveRoleResource").linkbutton('disable');
}

// 选择角色后联动选中 对应的资源
function selectRole(row) {
    $("#saveRoleResource").linkbutton('disable');
    $(".tree-checkbox").off("click");
    var state = row.useStatus;
    $('#role_edit').linkbutton('enable');//修改按钮可用
    $('#role_del').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#role_start').linkbutton('enable');//启用按钮可用
        $('#role_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#role_dis').linkbutton('enable');//禁用按钮可用
        $('#role_start').linkbutton('disable');//启用按钮禁用
    }
    var roleId = row.id;
    $.ajax({
        method: "get",
        url: getRootPath__() + "/authority/list/by_roleId?roleId="+roleId,
        success: function (data) {
            var $treegrid = $("#tt");
            $treegrid.treegrid("uncheckAll");
            $.each(data.data, function (i, v) {
                var r = $treegrid.treegrid("find", v.id);
                if(!r){
                    console.log(v);
                }else{
                    $treegrid.treegrid("checkNode", v.id);
                }
            });
            //选中后，禁用保存授予资源按钮
            $(".tree-checkbox").on("click", function () {
                $("#saveRoleResource").linkbutton("enable")
            });
        }

    });
}

// 新建角色
function createRole() {
    $('#roleForm').form('clear');
    $('#role_state').combobox('setValue','0');
    $('#role_state').combobox('disable');
    $("#role_data").dialog("setTitle", "新建角色").dialog("open");
}

// 编辑角色
function editRole() {
    var selectRows = $("#role_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的角色ID
        var state=0;//状态参数初始化，默认为禁用
        if(!selectRows[0].state){
            state=0;//启用
        }
        $('#roleForm').form('load', selectRows[0]);//表单加载
        $('#role_state').combobox('setValue',state);//状态下拉框赋值
        $('#role_state').combobox('enable');
        $('#role_data').dialog('open').dialog('setTitle', '编辑角色');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}

/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#role_state').combobox('enable');
    $("#roleForm").form("submit", {
        url: getRootPath__()+'/role/addOrUpdateRole',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#role_tab").datagrid("load");
                $("#role_data").dialog("close");
                buttonDisbles();
            }else if(re.data==="异常"){
                $.messager.alert("提示信息", re.errorMsg, 'warning');
            }else{
                msg("保存数据失败！+,"+re.errorMsg);
            }
        }
    });
}

/**
 * 保存授予资源
 */
function saveRoleResource() {
    var role = $("#role_tab").datagrid("getSelected");
    var resources = $("#tt").treegrid("getCheckedNodes");
    var resourcesIds = [];
    $.each(resources, function (i, v) {
        resourcesIds.push(v.id);
    });
    $.ajax({
        method: "post",
        url: getRootPath__() + "/role/saveRoleResource",
        data: {
            id: role.id,
            resourcesIds: resourcesIds
        },
        dataType: "text",
        success: function () {
            msg("角色资源保存成功！");
        }
    });

}


/**删除*/
function removeRole() {
    var selectRows = $("#role_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/role/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
                        }else{
                            msg("删除失败！");
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
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
function startRole(){
    var selectRows = $("#role_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/role/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
                        }else{
                            msg("启用失败！")
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
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
function disableRole(){
    var selectRows = $("#role_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/role/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
                        }else{
                            msg("禁用失败！");
                            $('#role_tab').datagrid('load');
                            buttonDisbles();
                        }
                    }

                }) ;
            }
        });
    }else {
        $.messager.alert("提示", "请选择要禁用的行！", 'info');

    }
}