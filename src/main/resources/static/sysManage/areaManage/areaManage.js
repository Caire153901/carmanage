$(function(){
    areaModel();
    initAreaTable();
    orgTreeTable();
});
//组织资源初始化
function orgTreeTable() {
    $('#org_treelist').treegrid({
        url:getRootPath__() + '/sys_manage/area_manage/getOrg',
        idField:'id',
        treeField:'orgname',
        fit:true,
        fitColumns : true,
        checkbox:true,
        loadFilter : function(re){
              return re;
        },
        columns: [[
            {field: 'id',hidden:true},
            {field: 'orgname', title: '组织名',width:300, align: 'left'},
            {field: 'intro', title: '组织描述', width:300,align: 'left'}
        ]]
    });
}
//主表初始化
function initAreaTable() {
    $("#area_tab").datagrid({
        checkOnSelect: true,
        url: getRootPath__()+'/sys_manage/area_manage/getAreaManageList',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        rownumbers:true,
        columns: [[
            { field: 'areaid', title: 'areaid', hidden: 'true'},
            { field: 'areacode', title: '区域编号',width:100},
            { field: 'areaname', title: '区域名称', width:100},
            { field: 'areaintro', title: '区域介绍',width:100}
        ]],
        onSelect:function(index, row){
            selectArea(row);
        },
        onLoadSuccess:function(data){//重新加载后明细表进行初始化
            $('#areaOrg_save').linkbutton('disable');
            $("#areaId").val("");
            orgTreeTable();
        }
    });
}
// 初始化弹出层
function areaModel(){
    $("#area_data").dialog({
        title: '区域新增',
        width: 350,
        height:250,
        closed: true,
        cache: false,
        modal: true,
        buttons: [{ //设置下方按钮数组
            text: '保存',
            iconCls: 'icon-ok',
            handler: function () {
                $('#areaForm').form('submit', {
                    success: function () {
                        $("#area_data").dialog('close');
                        $('#area_tab').datagrid('load');
                    }
                }); // 提交表单

            }
        }, {
            text: '取消',
            handler: function () {
                $("#area_data").dialog('close');
            }
        }]
    });
    // 居中
    $('#area_data').dialog('center');
}
//新增
function createArea() {
    $('#areaForm').form('clear');
    $("#area_data").dialog("open");
}
//编辑
function editArea() {
    var selectRows = $("#area_tab").datagrid("getSelections");
    if(selectRows.length >0){
        var id=selectRows[0].areaid;
        $('#areaForm').form('load', getRootPath__() + '/sys_manage/area_manage/getAreaManage?areaid=' + id);//表单加载
        $('#area_code').attr("readonly", "true");
        $('#area_data').dialog('open').dialog('setTitle', '编辑区域主表');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'warning');
    }
}
//删除
function deleteArea() {
    var selectRows = $("#area_tab").datagrid("getSelections");
    if (selectRows.length > 0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id=selectRows[0].areaid;
                $.ajax({
                    type: 'get',
                    url: getRootPath__()+'/sys_manage/area_manage/deleteAreaManage?areaid='+id,
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#area_tab').datagrid('load');
                        }else{
                            msg("删除失败！");
                            $('#area_tab').datagrid('load');
                        }
                    }
                });
            }
        });
    }
    else {
        $.messager.alert("提示", "请选择要删除的行！", 'info');
    }
}
//选择主表后的操作
function selectArea(row) {
    var areaId=row.areaid;
    $('#areaOrg_save').linkbutton('enable');
    $("#areaId").val(areaId);
    $.ajax({
        url: getRootPath__() + "/sys_manage/area_manage/getAreaOrg?areaId=" + areaId,
        dataType: "text",
        success:function (msg) {
            var $treegrid = $("#org_treelist");
            $treegrid.treegrid("uncheckAll")
            $.each(JSON.parse(msg), function(i,v) {
                var r = $treegrid.treegrid("find", v.orgid);
                if(!r){
                    console.log(v.orgid);
                }else{
                    if($treegrid.treegrid("getChildren", v.orgid).length === 0){
                        $treegrid.treegrid("checkNode", v.orgid);
                    }

                }
            });

        }
    });
}
//保存中间表
function saveAreaOrg() {
    var areaId = $("#areaId").val();
    var orgs = $("#org_treelist").treegrid("getCheckedNodes");
    var orgIds = [];
    $.each(orgs, function (i, v) {
        orgIds.push(v.id);
    });
    if (orgIds.length > 0) {
        $.messager.progress({title: '请等待', msg: '正在保存...'});
        $.ajax({
            url: getRootPath__() + "/sys_manage/area_manage/saveAreaOrg?areaId=" + areaId + "&orgIds=" + orgIds,
            dataType: "text",
            complete: function (data) {
                if (data.status == "200") {
                    $.messager.progress('close');
                    msg("保存成功！");
                } else {
                    $.messager.progress('close');
                    msg("保存失败！")
                }
            }
        });
    } else {
        $.messager.alert("提示", "请选择组织资源！", 'warning');
    }
}