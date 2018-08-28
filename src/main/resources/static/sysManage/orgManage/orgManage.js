/* 启动时加载 */
$(function(){
	initOrgType();  //初始化组织类别
    renderOrgTree($('#org_tab'), "treegrid",selectOrg);
    renderOrgTree($('#parentorg'), "combotreegrid");

});

/** 初始化新增组织 */
function addOrg(){
	$("#orgForm").form('clear');
	$("#org_data").dialog("setTitle","添加组织").dialog("open");
}

/**
 * 提交新增or更新组织
 */
function checkInput(){
     $("#orgForm").form("submit", {  
         url: getRootPath__()+'/sys_manage/org_manage/saveOrUpdateOrg' ,
         success: function (result) {
        	 if (result == "success") {
                 msg("组织保存成功！");
        		 $('#org_data').dialog('close');
        		 renderOrgTree($('#org_tab'), "treegrid",selectOrg);
        		 renderOrgTree($('#parentorg'), "combotreegrid");
        	 }
         }
     })
}
	 
/**删除组织*/
function deleteOrg() {  
    var selectRow = $("#org_tab").datagrid("getSelected");
    if (selectRow != null) {  
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {  
            if (isTrue) {  
                var orgId  = selectRow.id;
                $("#org_tab").datagrid("clearSelections");  //在删除组织后清空所选择的行
                $.ajax({  
                    type: 'post',  
                    url: getRootPath__()+'/sys_manage/org_manage/deleteOrg',
                    async:false,
                    data: {id: orgId },
                    dataType : "text",
                    success: function () {
                        msg("组织删除成功！");
                       	renderOrgTree($('#org_tab'), "treegrid",selectOrg);
                        renderOrgTree($('#parentorg'), "combotreegrid");
                    },
                    error: function(){
                        alert(arguments[1]);
                    }
                }); 
            }  
        });  
    }  
    else {  
        $.messager.alert("提示", "请选择要删除的行！", 'info');  
    } 
}

/*编辑组织*/
function editOrg(){
	var selectRow = $("#org_tab").datagrid("getSelected");
	if(selectRow != null){
		var orgId = selectRow.id;
		$.ajax({
			url: getRootPath__() + '/sys_manage/org_manage/getOrgById',
			method: 'post',
			data: {orgId : orgId },
	        dataType : "json",
	        async:false,
	        success:function(data){
	        	$('#orgForm').form('load', data);
	        }
		});
		
		$('#org_data').dialog('open').dialog('setTitle', '编辑组织');
	}else{
		$.messager.alert("提示", "请选择要修改的组织！", 'info');
		}
}
	
/*选择公司后联动选中其对应的资源*/
function selectOrg(row) {
    $("#saveRoleResource").linkbutton('disable');
    $(".tree-checkbox").off("click");
    var orgId = row.id;
    $.ajax({
        method : "post",
        url: getRootPath__() + "/sys_manage/org_manage/findResourceIdsByOrgId",
        data: {
            id: orgId
        },
        success: function (data) {
            var $treegrid = $("#tt");
            $treegrid.treegrid("uncheckAll")
            $.each(data, function (i, v) {
                var r = $treegrid.treegrid("find", v);
                if(!r){
                    console.log(v);
                }else{
                    $treegrid.treegrid("checkNode", v);
                }

            });
            // 改变权限后，启用保存授予资源按钮
            $(".tree-checkbox").on("click",function () {
                $("#saveRoleResource").linkbutton("enable")
            });
        }

    });
}	
	
/*保存组织资源*/	
function saveOrgResource() {
    var org = $("#org_tab").datagrid("getSelected");
    var resources = $("#tt").treegrid("getCheckedNodes");
    var resourcesIds = [];
    $.each(resources, function (i, v) {
        resourcesIds.push(v.id);
    });
    $.ajax({
        method : "post",
        url: getRootPath__() + "/sys_manage/org_manage/saveOrgResource",
        data: {
            orgId : org.id,
            resourcesIds : resourcesIds
        },
        dataType : "text",
        success: function () {
            msg("组织资源保存成功！");
            $("#saveRoleResource").linkbutton("disable")
        }

    });
}	
	
/*初始化组织类别*/
function initOrgType(){
	$('#orgtype').combobox({
		url:getRootPath__() + "/sys_manage/org_manage/orgTypeList",
		required:true,
		valueField:'id',
		textField:'name',
	});
}
	
	
	
	
	
	
	
	
	
	
	
	
