	/* 启动时加载 */
	$(function(){
		userData();
		platData();
	});
	
	var stationIds=[];
	function findProIds(userIds,callback){
		var proIds =[];
	    $.ajax({
	        url: getRootPath__() + "/sys_manage/authority_manage/getAuthorityManage?userId="+userIds,
	        async: false,
	        success: function (data) {
	          $.each(data, function (j, v) {
	        	proIds.push(v.platid);
	         });
	          callback(proIds)
	       }
	  }); 
    }
	function userData(){
		$("#authority_user_tab").datagrid({
	        checkOnSelect: true,
	        url: getRootPath__()+'/sys_manage/user_manage/getUserLists?state=0',
            fitColumns:true,
            pagination:true,
			pageSize:20,
			pageNumber:1,
            singleSelect:true,
            fit:true,
            rownumbers:true,
            onSelect:function(index, row){
                var userId=row.userid;
                	findProIds(userId,function(data){
                		selectUser(row,data);
                	});
	        },
	        onLoadSuccess:function(data){
	        	$("#userId").val("");
	        	$('#authority_save').linkbutton('disable');
	        	$('#authority_plat_tab').datagrid('clearChecked'); 
	        },
	        columns: [[
	            { field: 'userid', title: 'id', hidden: 'true'},
	         	{ field: 'orgid', title: '公司Id', hidden: 'true'},
	         	{ field: 'username', title: '用户名',sortable:true, width:60, align: 'left', halign: 'center'},
	         	{ field: 'realname', title: '姓名',  align: 'left', halign: 'center',sortable:true,width:50},
	         	{ field: 'gender', title: '性别',  align: 'center', halign: 'center',sortable:true,width:40,
	         		formatter: function(value,row,index){
	         			if (value=='0'){
	         				return '男';
	         			} 
	         			if (value=='1'){
	         				return '女';
	         			}
	         		}
	         	},
	         	{ field: 'orgname', title: '所属公司', align: 'left', halign: 'center'}
	        ]]	       
	    });
	}
	//电站初始化
	function platData(){
		 $("#authority_plat_tab").datagrid({
		        checkOnSelect: true,
		        pagination:true,
				pageSize:20,
				pageNumber:1,
		        url: getRootPath__()+'/sys_manage/authority_manage/getPlantConfigList?',
	            fitColumns:true,
	            fit:true,
	            onLoadSuccess:function(data){ 
	            	$("#authority_platname").val("");
	            },
		        columns: [[
                    {field:'id', checkbox:true, width:50},
                    {field:'powername',title:'电站名称',halign:'center',width:100},
			        {field:'organization',title:'所属公司',halign:'center',width:140,
		        		formatter: function(value,row,index){
		        			for(var i in value){
		        				if(i=="orgname"){
		        					return value[i];
		        				}
		        			}
					  }
		        	},    
			        {field:'dictionaryItem',title:'区域',halign:'center',width:100,
		        		formatter: function(value,row,index){
		        			for(var i in value){
		        				if(i=="name"){
		        					return value[i];
		        				}
		        			}
					  }
		            },    
			        {field:'user',title:'业主名称',align:'center',width:100,
		        		formatter: function(value,row,index){
		        			for(var i in value){
		        				if(i=="realname"){
		        					return value[i];
		        				}
		        			}
					  }
		            },
			        {field:'installedcapacity',title:'装机容量(KW)',align:'center',width:100},      
			        {field:'state',title:'状态',align:'center',width:100,
				        	formatter: function(value,row,index){
								if (value == '0'){
									return "禁用";
								} else if (value == '1') {
									return "正常";
								}
							}
			        }    
		        ]]
		    });
	}
	
    // 选择用户主表后联动电站表
    function selectUser(row,plantIds) {
        var userId=row.userid;
           stationIds=plantIds;
    	$('#authority_save').linkbutton('enable');
   	    $("#userId").val(userId);
   	    $("#authority_plat_tab").datagrid({
	        checkOnSelect: true,
	        pagination:true,
			pageSize:20,
			pageNumber:1,
	        url: getRootPath__()+'/sys_manage/authority_manage/getPlantConfigList',
            fitColumns:true,
            fit:true,
	        columns: [[               
	        	{field:'id', checkbox:true, width:50}, 
		        {field:'organization',title:'所属公司',halign:'center',width:140,
	        		formatter: function(value,row,index){
	        			for(var i in value){
	        				if(i=="orgname"){
	        					return value[i];
	        				}
	        			}
				  }
	        	},    
		        {field:'dictionaryItem',title:'区域',halign:'center',width:100,
	        		formatter: function(value,row,index){
	        			for(var i in value){
	        				if(i=="name"){
	        					return value[i];
	        				}
	        			}
				  }
	            },    
		        {field:'user',title:'业主名称',align:'center',width:100,
	        		formatter: function(value,row,index){
	        			for(var i in value){
	        				if(i=="realname"){
	        					return value[i];
	        				}
	        			}
				  }
	            },    
		        {field:'powername',title:'电站名称',halign:'center',width:100},  
		        {field:'installedcapacity',title:'装机容量(KW)',align:'center',width:100},      
		        {field:'state',title:'状态',align:'center',width:100,
			        	formatter: function(value,row,index){
							if (value == '0'){
								return "禁用";
							} else if (value == '1') {
								return "正常";
							}
						}
		        }    
	        ]], 
	        onLoadSuccess:function(data){ 
	        	var platData=$("#authority_plat_tab").datagrid("getData");
	            for(var i=0;i<platData.rows.length;i++){
	        		var platId=platData.rows[i].id;
	                $.each(plantIds, function (j, v) {
	                	if(platId==v){
	                		var index=$("#authority_plat_tab").datagrid("getRowIndex",platData.rows[i]);
	                		  $('#authority_plat_tab').datagrid("checkRow",index);
	                	}
	                });
	        	}
	        },
	        onCheck:function(rowIndex, rowData){      //单个复选框被选中时触发  
	            var rows = $('#authority_plat_tab').datagrid('getChecked');  
	            for (var i = 0; i < rows.length; i++) {  
	                if (checkExist(plantIds,rows[i].id) == -1) {   
	                	plantIds.push(rows[i].id);  
	                }  
	            } 
	            stationIds=plantIds;
	        },  
	        onCheckAll:function(){//批量复选框选中时触发  
	            var rows = $('#authority_plat_tab').datagrid('getChecked');  
	            for (var i = 0; i < rows.length; i++) {  
	                if (checkExist(plantIds,rows[i].id) == -1) {  
	                	plantIds.push(rows[i].id);  
	                }  
	            } 
	            stationIds=plantIds;
	        },  
	        onUncheck:function(rowIndex, rowData){//单记录复选框取消时触发  
	            var k = checkExist(plantIds,rowData.id); 
	            if (k != -1) {  
	            	plantIds.splice(k, 1); 
	            }  
	            stationIds=plantIds;
	        },  
	        onUncheckAll:function(rows){//批量复选框取消选中时触发
	            for (var i = 0; i < rows.length; i++) { 
	                var k = checkExist(plantIds,rows[i].id);  
	                if (k != -1) {  
	                	plantIds.splice(k, 1);
	                }  
	            } 
	            stationIds=plantIds;
	        }
	    });
    }

	//主表查询
	function queryUser(){
		var userName = $('#authority_username').val(); 
        $('#authority_user_tab').datagrid('options').url=getRootPath__()+'/sys_manage/user_manage/getUserLists?';
		$('#authority_user_tab').datagrid('load',{
			username:userName
		});        
    }
	//电站信息查询
	function queryPlat(){
		var platName = $('#authority_platname').val(); 
        $('#authority_plat_tab').datagrid('options').url=getRootPath__()+'/sys_manage/authority_manage/getPlantConfigList';
		$('#authority_plat_tab').datagrid('load',{
			powername:platName,
		});    
	}
   //保存
   function savePlatUser(){
		var userId=$("#userId").val();
		if (stationIds.length > 0) {  
	         $.messager.progress({title:'请等待',msg:'正在保存...'});
	         $.ajax({
	                url: getRootPath__()+'/sys_manage/authority_manage/saveAuthorityManage?userId='+userId+'&platIds='+stationIds,  
                    complete:function(data){
                    	if(data.status=="200"){
                    		$.messager.progress('close');
                    		msg("保存成功!");
                    	}else{
                    		$.messager.progress('close');
                            msg("保存失败！");
                    	}
                    }
	         });
       }else {  
           $.messager.alert("提示", "请选择电站信息！", 'warning');  
       } 
   }
	  //检测选中的记录,或者要删除的记录在原数组中是否存在  
   function checkExist(proIds,proId) {  
       for (var i = 0; i < proIds.length; i++) {  
           if (proIds[i] == proId) {
           	return i;  
           }
       }  
       return -1;  
   } 
	