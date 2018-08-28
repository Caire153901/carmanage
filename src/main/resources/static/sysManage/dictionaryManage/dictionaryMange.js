	/* 启动时加载 */
	$(function(){
		$("#dictionary_tab").datagrid({
	        checkOnSelect: true,
	        pagination:true,
			pageSize:20,
			pageNumber:1,
	        url: getRootPath__()+'/sys_manage/dictionary_manage/getDictionaryList',
            fitColumns:true,
            singleSelect:true,
            fit:true,
            rownumbers:true,
	        columns: [[               
	            { field: 'id', title: 'id', hidden: 'true'},
	         	{ field: 'sn', title: '字典编号',sortable:true,width:100},
	         	{ field: 'name', title: '字典名称', sortable:true,width:100},
	         	{ field: 'intro', title: '字典介绍', sortable:true,width:100}
	        ]],
	        onSelect:function(index, row){
	        	selectDict(row);
	        },
	        onLoadSuccess:function(){//重新加载后明细表进行初始化
	        	dictItemData();
	        }
	    });
		dictItemData();
		 dictModel();
		 dictItemModel();
	});
	//数据字典明细表初始化
	function dictItemData(){
		 $("#dictionaryItem_tab").datagrid({
		        checkOnSelect: true,
		        pagination:true,
				pageSize:20,
				pageNumber:1,
		        url: getRootPath__()+'/sys_manage/dictionary_manage/getDictionaryItemList',
	            fitColumns:true,
	            singleSelect:true,
	            rownumbers:true,
	            fit:true,
		        columns: [[               
		            { field: 'id', hidden: 'true'},
		            { field: 'parentId', hidden: 'true'},
		         	{ field: 'name', title: '明细名称',sortable:true,width:100},
		         	{ field: 'dictname', title: '所属字典',sortable:true,width:100},
		         	{ field: 'intro', title: '明细介绍', sortable:true,width:100}
		        ]]
		    });
	}
	
    //获取数据字典主表
	function getDictName(id){
		var msg=$.ajax({url:getRootPath__()+'/sys_manage/dictionary_manage/getDictionary?id='+id,async:false});
		  return msg.responseJSON.name;
	}
    // 选择数据字典主表后联动加载字典明细表
    function selectDict(row) {
        var dictId = row.id;
    	$('#dictItem_add').linkbutton('enable');
    	$('#dictItem_edit').linkbutton('enable');
    	$('#dictItem_remove').linkbutton('enable');
   	    $("#parentId").val(dictId);
        $("#dictionaryItem_tab").datagrid({
	        checkOnSelect: true,
	        pagination:true,
			pageSize:20,
			pageNumber:1,
	        url: getRootPath__()+'/sys_manage/dictionary_manage/getDictionaryItemList?parentId='+dictId,
            fitColumns:true,
            singleSelect:true,
            fit:true,
	        columns: [[               
	            { field: 'id', hidden: 'true'},
	            { field: 'parentId', hidden: 'true'},
	         	{ field: 'name', title: '明细名称',sortable:true,width:100},
	         	{ field: 'dictname', title: '所属字典',sortable:true,width:100},
	         	{ field: 'intro', title: '明细介绍', sortable:true,width:100}
	         	
	        ]]
	    });
    }
    
	//主表查询
	function checkInputQuery(){
       var dictSn = $('#dictSn').val(); 
        $('#dictionary_tab').datagrid('options').url=getRootPath__()+'/sys_manage/dictionary_manage/getDictionaryList';
		$('#dictionary_tab').datagrid('load',{
			sn:dictSn,
		});        
    }
	//主表新增
	function dictModel(){
        // 初始化弹出层
        $("#dict_data").dialog({
        	title: '字典主表新增',
        	width: 350,
            height:250,
            closed: true,
            cache: false,
            modal: true,
            buttons: [{ //设置下方按钮数组
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#dictForm').form('submit', {
                    	success: function () {
                            $("#dict_data").dialog('close');
                            $('#dictionary_tab').datagrid('load'); 
                        }
                    }); // 提交表单

                }
            }, {
                text: '取消',
                handler: function () {
                    $("#dict_data").dialog('close');
                }
            }]
        });
        // 居中
        $('#dict_data').dialog('center');
	}
	function dictItemModel(){
		// 初始化弹出层
		$("#dictItem_data").dialog({
			title: '字典明细表新增',
			width: 400,
			height:300,
			closed: true,
			cache: false,
			modal: true,
			buttons: [{ //设置下方按钮数组
				text: '保存',
				iconCls: 'icon-ok',
				handler: function () {
					$('#dictItemForm').form('submit', {
						
						success: function () {
							$("#dictItem_data").dialog('close');
							$('#dictionaryItem_tab').datagrid('load'); 
						}
					}); // 提交表单
					
				}
			}, {
				text: '取消',
				handler: function () {
					$("#dictItem_data").dialog('close');
				}
			}]
		});
		// 居中
		$('#dictItem_data').dialog('center');
	}
	
	
	
    // 主表新建
    function createDict() {
    	$('#dictForm').form('clear');
        $("#dict_data").dialog("open");
    }
    // 明细表新建
    function createDictItem() {
    	$('#dictItemForm').form('clear');
    	var parentId=$('#parentId').val();
        $("#dictItem_data").dialog("open");
        $("#dictItem_parentId").val(parentId);
    }
    	
	//主表编辑初始化
    function editDict() {
    	var selectRows = $("#dictionary_tab").datagrid("getSelections");  
    	if(selectRows.length >0){
    		var id=selectRows[0].id;
    		$('#dictForm').form('load', getRootPath__() + '/sys_manage/dictionary_manage/getDictionary?id=' + id);//表单加载
    		$('#dict_sn').attr("readonly", "true"); 
    		$('#dict_data').dialog('open').dialog('setTitle', '编辑字典主表');
    	}else{
    		$.messager.alert("提示", "请选择要修改的行！", 'warning');  
    	}
    }	
    //明细表编辑初始化
    function editDictItem() {
    	var selectRows = $("#dictionaryItem_tab").datagrid("getSelections");  
    	if(selectRows.length >0){
    		var id=selectRows[0].id;
    		$('#dictItemForm').form('load', getRootPath__() + '/sys_manage/dictionary_manage/getDictionaryItem?id=' + id);//表单加载
    		$('#dictItem_name').attr("readonly", "true"); 
            $('#dictItem_data').dialog('open').dialog('setTitle', '编辑字典主表');
    	}else{
    		$.messager.alert("提示", "请选择要修改的行！", 'warning');  
    	}
    }	
	//主表删除
	function deleteDict() {  
        var selectRows = $("#dictionary_tab").datagrid("getSelections");  
        if (selectRows.length > 0) {  
            $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {  
                if (isTrue) { 
                	var id=selectRows[0].id;
                    $.ajax({  
                        type: 'get',  
                        url: getRootPath__()+'/sys_manage/dictionary_manage/deleteDict?id='+id,  
                        complete:function(data){
                        	if(data.status=="200"){
                        		msg("删除成功！");
                        		 $('#dictionary_tab').datagrid('load'); 
                        		 $('#dictionaryItem_tab').datagrid('load'); 
                        	}else{
                        		msg("删除失败！");
                        		 $('#dictionary_tab').datagrid('load'); 
                        		 $('#dictionaryItem_tab').datagrid('load'); 
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
	//明细删除
	function deltetDictItem() {  
		var selectRows = $("#dictionaryItem_tab").datagrid("getSelections");  
		if (selectRows.length > 0) {  
			$.messager.confirm("提示", "确定要删除吗？", function (isTrue) {  
				if (isTrue) { 
					var id=selectRows[0].id;
					$.ajax({  
						type: 'get',  
						url: getRootPath__()+'/sys_manage/dictionary_manage/deleteDictItem?id='+id,  
						complete:function(data){
							if(data.status=="200"){
								msg("明细删除成功！");
								$('#dictionaryItem_tab').datagrid('load'); 
							}else{
								msg("明细删除失败！");
								$('#dictionaryItem_tab').datagrid('load'); 
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