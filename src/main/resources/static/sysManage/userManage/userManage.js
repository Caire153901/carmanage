	
    /* 启动时加载 */
	$(function(){
		$("#user_tab").datagrid({
	        checkOnSelect: true,
	        pagination:true,
			pageSize:20,
			pageNumber:1,
	        url: getRootPath__()+'/sys_manage/user_manage/getUserLists',
            fitColumns:true,
            singleSelect:true,
            fit:true,
            rownumbers:true,
            onSelect:function(index, row){
	        	selectUser(row);
	        },
	        onLoadSuccess:function(data){
	        	buttonDisble();
	        },
	        columns: [[
	            { field: 'userid', title: 'id', hidden: 'true'},
	         	{ field: 'orgid', title: '公司Id', hidden: 'true'},
	         	{ field: 'username', title: '用户名',sortable:true, width:100, align: 'left', halign: 'center'},
	         	{ field: 'dictionaryItem', title: '用户类型',sortable:true, width:50, halign: 'center',
	         		formatter: function(value,row,index){
	         			for(var i in value){
	        				if(i=="name"){
	        					return value[i];
	        				}
	        			}
	         		}
	         	},
	         	{ field: 'realname', title: '姓名',  align: 'left', halign: 'center',sortable:true,width:100},
	         	{ field: 'gender', title: '性别',  align: 'center', halign: 'center',sortable:true,width:50,
	         		formatter: function(value,row,index){
	         			if (value=='0'){
	         				return '男';
	         			} 
	         			if (value=='1'){
	         				return '女';
	         			}
	         		}
	         	},
	         	{ field: 'organization', title: '所属公司', align: 'left', halign: 'center',
                    formatter: function(value,row,index){
                        for(var i in value){
                            if(i=="orgname"){
                                return value[i];
                            }
                        }
                    }
				},
	         	{ field: 'tel', title: '手机',  align: 'center', halign: 'center',sortable:true,width:100},
	         	{ field: 'email', title: '电子邮箱',  align: 'center', halign: 'center',sortable:true,width:100},
	         	{ field: 'address', title: '详细地址', align: 'left', halign: 'center', sortable:true,width:120},
	         	{ field: 'uState', title: '状态',  align: 'center', halign: 'center',sortable:true,width:60,
	         		formatter: function(value,row,index){
						if (value=='1'){
							return "<span class='iconfont icon-chenggong' style='color:#1AE61A'></span>";
						} 
						if (value=='0'){
							return "<span class='iconfont icon-iconset0187' style='color:red'></span>";
						}
					}
	         	}
	        ]]	        
	    });
	});
	
	//选中后按钮状态
	function selectUser(row){
		var state=row.uState;
    	$('#user_edit').linkbutton('enable');//修改按钮可用
    	$('#user_remove').linkbutton('enable');//删除按钮可用
    	if(state==0){
    		$('#user_start').linkbutton('enable');//启用按钮可用
    		$('#user_dis').linkbutton('disable');//禁用按钮禁用
    	}else if(state==1){
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
	//下拉框初始化（暂时没有用）
	function comboxSelect(id){
        $('#user_org').combobox({      
            url:getRootPath__() +"/sys_manage/user_manage/getOrgList?id="+id,      
            valueField:'id',      
            textField:'orgname',
            onSelect: function(rec){
                var url = getRootPath__() +"/sys_manage/user_manage/getOrgList?id="+rec.id;
		         $('#user_dep').combobox('clear');
		         $('#user_dep').combobox('reload', url);
		         $('#user_dep').combobox('enable'); 
            }    
        });  
        $('#user_dep').combobox({      
            valueField:'id',      
            textField:'orgname'  
        });  
	} 
	
	//组织机构树形选择初始化
	function comboxtree(id){
		var flag=true;
		$('#user_orgid').combotreegrid({ 
			required:true,
            idField: 'id',
            fit : true,
            panelWidth:250,//面板宽度
            treeField: 'orgname',
            fitColumns : true,
            animate: true,
            collapsible: true,
            columns: [[
                { field: 'id', width: 180,hidden: 'true'},
                { field: 'orgname', title: '组织名', align: 'left', halign: 'center'},
                { field: 'orgTypeName', title: '组织类别', align: 'left', halign: 'center'},
            ]],
            onSelect:function(rec){
            	addRoleTable(null,rec.id);
            },
            loader : function (params, success, error) {
            	 var select=$.ajax({
            		 async: false,
                     url: getRootPath__() + '/sys_manage/user_manage/getOrgById?id='+id
                 });
                var pid = id;
                if(params.id)
                    pid = params.id;
                if(flag){
                  flag=false;
                  var list=[];
                  list.push(select.responseJSON[0]);
                  success(list);
                }else{
                    $.ajax({
                        url: getRootPath__() + '/sys_manage/org_manage/getOrgList',
                        data: {
                            pid: pid
                        },
                        dataType: 'json',
                        success: function (re) {
                            success(re);
                        }
                    });
                }

            },
            loadFilter : function(data){
                $.each(data, function (i, v) {
                    v.state = 'closed';
                });
                return data;
            }
	    }); 
	}
    //组织机构树形选择初始化
    function renderOrgTree($jq,id){
        $jq.data("flag","1");
        var option = {
            required:true,
            idField: 'id',
            fit: true,
            treeField: 'orgname',
            fitColumns: true,
            animate: true,
            panelWidth:300,
            columns: [[
                {field: 'id', width: 180, hidden: 'true'},
                {field: 'orgname', title: '组织名', width: '100%', align: 'left'}
            ]],
            onSelect: function (row) {
                addRoleTable(null,row.id);
            },
            loader: function (params, success, error) {
                if ("1" === $jq.data("flag")) {
                    $jq.data("flag", "2");
                    $.ajax({
                        url: getRootPath__() + '/sys_manage/user_manage/getOrgById?id='+id,
                        dataType: 'json',
                        success: function (re) {
                            re[0].state = "closed";
                            success(re);
                        }
                    });
                } else {
                	var pid=id;
                	if(params.id)
                       pid = params.id;
                    $.ajax({
                        url: getRootPath__() + '/sys_manage/org_manage/getOrgList',
                        data: {
                            pid: pid
                        },
                        dataType: 'json',
                        success: function (re) {
                            if (re.length === 0) {
                                error();
                            }else {
                                $.each(re, function (i, v) {
                                    if(v.leaf === "0"){
                                        v.state = 'closed';
                                    }
                                });
                                success(re);
                            }
                        }
                    });
                }
            }
        };
            $jq.combotreegrid(option);

    }

	/** 查询数据条件 */
	function checkInputQuery(){
       var realName = $('#realName').val(); //用户真实姓名
        $('#user_tab').datagrid('options').url=getRootPath__()+'/sys_manage/user_manage/getUserLists';
		$('#user_tab').datagrid('reload',{
			realname:realName,
		});        
    }
	function reset(){
		$('#realName').val("");
		$('#user_tab').datagrid('load',{}); 
	}
	/**角色表初始化*/
	function addRoleTable(id,orgId){
		$("#role_tab").datagrid({
	        checkOnSelect: true,
	        pagination:true,
			pageSize:20,
			pageNumber:1,
	        url: getRootPath__()+'/sys_manage/role_manage/findRoleListByOrgId?orgId='+orgId,
	        fit: true,
	        columns: [[
	            { field: 'id', checkbox:true, width:50},
	            { field: 'roleCode', title: '角色编码',  align: 'left', halign: 'center',width:100},
	         	{ field: 'roleName', title: '角色名称',  align: 'left', halign: 'center',width:100},
	         	{ field: 'description', title: '描述信息', align: 'left', halign: 'center',width:100},
	         	{ field: 'orgName', title: '公司名称',  align: 'left', halign: 'center'},
	         	{ field: 'orgId', hidden:true,title: '公司id',width:50}
	        ]],//加载成功之后，选定数据       
	        onLoadSuccess:function(data){   
	        	var roleData=$("#role_tab").datagrid("getData");
	            if(null!=id){
		            $.ajax({
		            	async: true,
		                url: getRootPath__() + "/sys_manage/user_manage/getUserRoleList?uesrId="+id,
		                success: function (data) {
		                    for(var i=0;i<roleData.rows.length;i++){
		                		var roleId=roleData.rows[i].id;
		                        $.each(data, function (j, v) {
		                        	if(roleId==v.roleId){
		                        		var index=$("#role_tab").datagrid("getRowIndex",roleData.rows[i]);
		                        		$('#role_tab').datagrid("checkRow",index);
		                        	}
		                        });
		                	}            	  
		                }
		            });
	            }
	        }	
		
	    }); 
	}
	/** 新增初始化 */
	function addUser(orgId){
		addRoleTable(null,orgId);
		$('#password_tr').show();
		$('#userForm').form('clear');
		$('#user_gender').combobox('setValue','0');
		$('#user_state').combobox('setValue','1');
		$('#user_state').combobox('disable');
		$('#user_dep').combobox('disable');
		getUseType();
		$("#user_data").dialog("setTitle","添加用户信息").dialog("open");
	}
	/**用户类型*/
	function getUseType(){
		$('#user_usertype').combobox({    
		    url:getRootPath__() + '/sys_manage/dictionary_manage/getUserTypeList?id=2',    
		    valueField:'id',    
		    textField:'name'   
		});
	}
	/**用户类型名称*/
	function getUseTypeName(id){
		var userTypeName=$.ajax({  
			async: false,
			url: getRootPath__()+'/sys_manage/dictionary_manage/getDictionaryItem?id='+id
		}) ;
		return userTypeName.responseJSON;
	}
	//**组织名称*//*
	function getOrgName(orgId,callback){
		$.ajax({  
			async: false,
			url: getRootPath__()+'/sys_manage/org_manage/getOrgById?orgId='+orgId,
			success:function(data){
				callback(data.orgname)
			}
		}) ;
	}
	
	/**编辑初始化*/
    function editUser() {
    	var selectRows = $("#user_tab").datagrid("getSelections"); 
    	if(selectRows.length > 1){
    		 $.messager.alert("提示", "只能选择一行！", 'info'); 
    	}else if(selectRows.length >0){
    		var id =selectRows[0].userid;//获取选中行的用户ID
    		var gender=1;//性别参数初始化，默认为女
    		var state=0;//状态参数初始化，默认为禁用
    		var usertype=selectRows[0].usertypeId;
    		var org=selectRows[0].orgid;
    		var dep=selectRows[0].depId;
    		if(!selectRows[0].gender){
    			gender=0;//男
    		}
    		if(!selectRows[0].state){
    			state=1;//启用
    		}
            $('#userForm').form('load', getRootPath__() + '/sys_manage/user_manage/getUser?userid=' + id);//表单加载
            $('#user_gender').combobox('setValue',gender);//性别下拉框赋值
            $('#user_state').combobox('setValue',state);//状态下拉框赋值
            getUseType();//用户类型初始化
            $('#user_usertype').combobox('select',usertype);//用户类型下拉框赋值
            getOrgName(org,function(data){//组织初始化
            	$('#user_orgid').combotreegrid('setText',data);
            });
            $('#user_state').combobox('enable');
            var password=$('#user_userpwd').val();
            $('#user_repwd').val(password);
            $('#password_tr').hide();
            addRoleTable(id,org);//添加角色列表
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
    	var selectRows = $("#role_tab").datagrid("getSelections"); 
    	if (selectRows.length > 0) {  
	        var ids = [];  
	         for (var i = 0; i < selectRows.length; i++) {  
	              ids.push(selectRows[i].id); //将选的行的Id添加到ids数组中  
	         } 
	         $('#user_state').combobox('enable');
	         $("#userForm").form("submit", {  
	             url: getRootPath__()+'/sys_manage/user_manage/saveOrUpdateUser?roleIds='+ids,  
	             onsubmit: function () {  
	                 return $(this).form("validate");  
	             },  
	             success: function (result) {
	                 if (result == "success") {
						 msg("提交成功！");
	                	 $("#user_tab").datagrid("load");  
	                     $("#user_data").dialog("close");
	                     buttonDisble();
	                 }else if(result == "repeated"){
	                	 $.messager.alert("提示信息", "用户名已存在", 'warning');
	                 }else{
	                 	 msg("保存数据失败！");
	                 }  
	           }  
	         }); 
        }else {  
            $.messager.alert("提示", "请选择用户角色！", 'info');  
        }
    	 
    }
    
	/**删除*/
	function deleteUser() {  
        var selectRows = $("#user_tab").datagrid("getSelections");  
        if (selectRows.length > 0) {  
            $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {  
                if (isTrue) { 
                    var ids = [];  
                    for (var i = 0; i < selectRows.length; i++) {  
                        ids.push(selectRows[i].userid); //将选的行的Id添加到ids数组中  
                    } 
                    $.ajax({  
                        type: 'post',  
                        url: getRootPath__()+'/sys_manage/user_manage/deleteUser',  
                        data: {userids: ids.join(',') },  
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
        }  
        else {  
            $.messager.alert("提示", "请选择要删除的行！", 'info');  
        } 
        
    }
	/**启用*/
	function startUsing(){
		var selectRows = $("#user_tab").datagrid("getSelections");
		 if (selectRows.length > 0) {  
	            $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {  
	                if (isTrue) {  
	                    var ids = [];  
	                    for (var i = 0; i < selectRows.length; i++) {  
	                        ids.push(selectRows[i].userid); //将选的行的Id添加到ids数组中  
	                    }  
	                    $.ajax({ 
	                    	async: true,
	                        type: 'post',  
	                        url: getRootPath__()+'/sys_manage/user_manage/startUsing',  
	                        data: {
	                        	userids: ids.join(',')
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
		if (selectRows.length > 0) {  
			$.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {  
				if (isTrue) {  
					var ids = [];  
					for (var i = 0; i < selectRows.length; i++) {  
						ids.push(selectRows[i].userid); //将选的行的Id添加到ids数组中  
					}  
					$.ajax({  
						type: 'post',  
						async: true,
						url: getRootPath__()+'/sys_manage/user_manage/disableUsing',  
						data: {
							userids: ids.join(',') 
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