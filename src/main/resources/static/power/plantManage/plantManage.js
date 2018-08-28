$(function(){
    renderOrgTree($('#orgId'), "combotreegrid" , loadLinkedItem);
});

/*页面加载时初始化登录用户下属电站信息*/
function initPlants(){
	$('#plantConfig_tab').datagrid({    
	    url:getRootPath__()+'/power_manage/plant_manage/getPlantListByUserId',
	    rownumbers:true,
	    singleSelect:true,
        onDblClickRow:function(i,row){
            var titles=row.powername;
            var plantId=row.id;
            var providerType=row.provider;
            var urls= '/power_manage/single_station/detailStation?plantId='+plantId+'&providerType='+providerType;
            top.addTab(titles,urls);
        },
	    pagination:true,
	    pageNumber:1,
	    fit: true,
	    pageSize:10,
	    columns:[[    
	        {field:'id',title:'电站信息ID',hidden:true , width:100},
            {field:'powername',title:'电站名称',halign:'center',width:200},
	        {field:'organization',title:'所属公司',halign:'center',width:140,
	    		formatter: function(value,row,index){
	    			for(var i in value){
	    				if(i=="orgname"){
	    					return value[i];
	    				}
	    			}
			  }
	    	},    
	        {field:'areaManage',title:'区域',align:'center',width:100,
	    		formatter: function(value,row,index){
	    			for(var i in value){
	    				if(i=="areaname"){
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
	        {field:'putproductiontime',title:'投产发电时间',align:'center',width:100,
	        	formatter: function(value){
					if (value != null){
						return getTime(value);
					} else {
						return "时间异常";
					}
				}
	        },
            {field:'runningtime',title:'设计运行时间(年)',align:'center',width:100},
	        {field:'prices',title:'售电电价',align:'center',width:100},    
	        {field:'phone',title:'联系方式',align:'center',width:120},
	        {field:'address',title:'详细地址',align:'center',width:100},
	        {field:'longitude',title:'经度',align:'center',width:100},    
	        {field:'latitude',title:'纬度',align:'center',width:100},    
	        {field:'iconcls',title:'电站图片',align:'center',width:100,
               formatter: function(value,row,index){
				   var imgName=row.powername;
				   if(value!=null && value!=""){
					   var image=value.substring(1,value.length - 1);
				   }else{
					       image="";
				   }
				   return  "<a href='#' onclick=\"openWin('"+imgName+"','"+image+"')\"><span class='iconfont icon-tupian'></span></a>";
			   }
			},
	        {field:'state',title:'状态',align:'center',width:100,
	        	formatter: function(value,row,index){
					if (value == '2'){
						return "禁用";
					} else if (value == '1') {
						return "正常";
					}
				}
	        },
	        {field:'provider',title:'供应厂商',align:'center',width:100,
	        	formatter: function(value,row,index){
					if (value == '1'){
						return "固德威";
					} else if (value == '2') {
						return "三晶";
					} else{
						return "异常";
					}
				}
	        },
	    ]]    
	});
	
}

/*展示图片*/
function openWin(title,image){
    $('#win').window({
        width:650,
        height:500,
        title:title,
        closed:true,
        modal:true
    });
    if(image==undefined || image==""){
        $.messager.alert("提示","未上传图片","warning");
    }else{
        var path =image.split("\/");
        var imgName=path[path.length-1];
        var imgPath=getRootPath__()+"/fistPages/img?imgName="+imgName;
        $("#stationImg").attr('src',imgPath);
        $("#win").window("open");
    }
}
/*时间转换*/
function getTime(value) {
	var tss = value.time || 0;
	var ts=tss.toString().substring(0,10);
    var t, y, m, d, h, i, s;
    t = ts ? new Date(ts * 1000) : new Date();
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
//    h = t.getHours();
//    i = t.getMinutes();
//    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
}

/*查询电站*/
function checkInputQuery(){
   var queryName = $('#queryName').val();
    $('#plantConfig_tab').datagrid('options').url=getRootPath__()+'/power_manage/plant_manage/getPlantListByUserId';
	$('#plantConfig_tab').datagrid('load',{
		powername:queryName,
	});        
}	

/*新增电站*/
function addPlant(){
	$("#plantConfigForm").form('clear');
	$('#areaId').combobox('clear');
	$('#areaId').combobox('readonly',true);
	$('#state').combobox('setValue','1');  //新增时为状态赋初始值
	showStation();  //新增电站时展示第三方电站信息
	$("#plantConfig_data").dialog("setTitle","添加电站").dialog("open");
}
	
/*初始化编辑电站信息*/
function editPlant(){
	var selectRow = $("#plantConfig_tab").datagrid("getSelected");
	if(selectRow != null){
		var plantId = selectRow.id;
		var orgId = selectRow.orgId;
		$('#areaId').combobox("readonly",false)
		$.ajax({
			url: getRootPath__() + '/power_manage/plant_manage/getPlantConfigById',
			method: 'post',
			data: {plantId : plantId },
            dataType : "json",
            async:false,
            success:function(data){
	            data.putproductiontime = getTime(data.putproductiontime);
            	$('#plantConfigForm').form('load', data);
            	getOrgName(orgId,function(data){  //组织名称初始化
                	$('#orgId').combotreegrid('setText',data);
                });
                getAreaName(plantId,function(data){  //区域名称初始化
                    $('#areaId').combotreegrid('setText',data);
                });
            }
		});
		$('#plantConfig_data').dialog('open').dialog('setTitle', '编辑电站');
	}else{
		$.messager.alert("提示", "请选择要编辑的电站！", 'info');
	}
}	

/*提交新增or编辑电站*/
function submitPCTable(){
    /*$('#dlg-buttons').window('close');*/
	//先进行上传电站图片操作
	$('#uploadPlantPic').form('submit', {    
	    url:getRootPath__() + '/power_manage/plant_manage/upload',    
	    success:function(data){
	    	if(data&&data!=='"'+'"'){
	    		$('#iconcls').val(data);  //将图片路径加载到电站表单的隐藏域iconcls中
	    	}
	        //提交新增/编辑表单
	    	$("#plantConfigForm").form("submit", {  
	            url: getRootPath__()+'/power_manage/plant_manage/saveOrUpdatePlantConfig' ,
	            success: function (result) {
		           	 if (result == "success") {
		           		 $.messager.show({
		                          title: '提示消息', 
		                          msg: '提交成功', 
		                          showType: 'show', 
		                          timeout: 1000, 
		                          style: { 
		                            right: '', 
		                            bottom: ''
		                          } 
		                     }); 
		           		 $("#plantConfig_data").dialog("close");
		           		 $("#plantConfig_tab").datagrid("load");
		           	 }
	            }
	        });

	    },  
	    
	});
	
}	
	
/*启用电站*/
function enablePlant(){
	var selectRow = $("#plantConfig_tab").datagrid("getSelected");
	if(selectRow != null){
		$.messager.confirm("提示", "确定要启用吗？", function (isTrue){
			if(isTrue){
				var plantId = selectRow.id;
				$.ajax({
					url: getRootPath__() + '/power_manage/plant_manage/enablePlant',
					method: 'post',
					data: {plantId : plantId },
		            dataType : "text",
		            async:false,
		            success:function(result){
		            	if(result == "success"){
		              		 $.messager.show({ 
		                         title: '提示消息', 
		                         msg: '启用成功', 
		                         showType: 'show', 
		                         timeout: 1000, 
		                         style: { 
		                           right: '', 
		                           bottom: ''
		                         } 
		                    });
		            		$("#plantConfig_tab").datagrid("reload");
		            	}
		            }
					
				});
			}
		})
	}else{
		$.messager.alert("提示", "请选择要启用的行！", 'info');
	}
}

/*禁用电站*/
function disablePlant(){
	var selectRow = $("#plantConfig_tab").datagrid("getSelected");
	if(selectRow != null){
		$.messager.confirm("提示", "确定要禁用吗？", function (isTrue){
			if(isTrue){
				var plantId = selectRow.id;
				$.ajax({
					url: getRootPath__() + '/power_manage/plant_manage/disablePlant',
					method: 'post',
					data: {plantId : plantId },
		            dataType : "text",
		            async:false,
		            success:function(result){
		            	if(result == "success"){
		            		$.messager.show({ 
		                         title: '提示消息', 
		                         msg: '禁用成功', 
		                         showType: 'show', 
		                         timeout: 1000, 
		                         style: { 
		                           right: '', 
		                           bottom: ''
		                         } 
		                    });
		            		$("#plantConfig_tab").datagrid("reload");
		            	}
		            }
				});
			}
		})
	}else{
		$.messager.alert("提示", "请选择要禁用的行！", 'info');
	}	
}	

/*删除电站*/
function deletePlant(){
	var selectRow = $("#plantConfig_tab").datagrid("getSelected");
	if(selectRow != null){
		$.messager.confirm("提示", "确定要删除吗？", function (isTrue){
			if(isTrue){
				var plantId = selectRow.id;
				$.ajax({
					url: getRootPath__() + '/power_manage/plant_manage/deletePlant',
					method: 'post',
					data: {plantId : plantId },
		            dataType : "text",
		            async:false,
		            success:function(result){
		            	if(result == "success"){
		            		$.messager.show({ 
		                         title: '提示消息', 
		                         msg: '删除成功', 
		                         showType: 'show', 
		                         timeout: 1000, 
		                         style: { 
		                           right: '', 
		                           bottom: ''
		                         } 
		                    });
		            		$("#plantConfig_tab").datagrid("reload");
		            	}
		            }
				});
			}
		})
	}else{
		$.messager.alert("提示", "请选择要删除的行！", 'info');
	}	
	
}

/*展示第三方电站列表*/
function showStation(){
	
	$('#station_tab').datagrid({    
	    url:getRootPath__()+'/power_manage/plant_manage/getStationList' ,
	    rownumbers:true,
	    singleSelect:true,
	    pagination:true,
	    pageNumber:1,
	    pageSize:10,
	    fit:true,
	    columns:[[
	        {field:'plantId',title:'电站信息ID',hidden:true},    
	        {field:'name',title:'电站名称',halign:'center',width:160},    
	        {field:'latitude',title:'经度',halign:'center',width:70},    
	        {field:'longitude',title:'纬度',align:'center',width:70},    
	        /*{field:'description',title:'详细信息',halign:'center',width:100},*/    
	        /*{field:'capacity',title:'装机容量(KW)',align:'center',width:90}, */   
	        {field:'createDate',title:'投产发电时间',align:'center',width:110,
	        	formatter: function(value){
					if (value != null){
						return getTime(value);
					} else {
						return "时间异常";
					}
				}
	        },    
	        {field:'usestate',title:'状态',align:'center',width:50,
		        	formatter: function(value,row,index){
						if (value == '1'){
							return "未添加";
						} else if (value == '2') {
							return "已添加";
						}
					}
	        },    
	    ]],
	
		onSelect:function(index, row){
			$('#plantConfigForm').form('clear')
			var plantId = row.plantId;
		     $.ajax({
		            url: getRootPath__() + '/power_manage/plant_manage/loadStation',
		            data: {
		            	plantId: plantId
		            },
		            dataType: 'json',
		            success: function (data) {
		            	data.putproductiontime = getTime(data.putproductiontime);
		            	data.state = 1;  //新增电站时默认状态为启用
		            	data.orgId = "请选择所属公司";
		            	data.areaId = "请选择所属区域";
		            	data.userId = "请输入所属业主";
		                $('#plantConfigForm').form('load', data);
		            }
		        });
		     
	    },
	
	});
	
}

/*初始化区域列表*/
function initArea(){
	$('#areaId').combobox({    
	    url:getRootPath__() + '/power_manage/plant_manage/getAreaTypeList?id=5',    
	    valueField:'id',    
	    textField:'name'   
	});
}

/*组织机构树形选择初始化
function comboxtree(id){
	var flag=true;
	$('#orgId').combotreegrid({ 
		required:true,
        idField: 'id',
        fit : true,
        treeField: 'orgname',
        fitColumns : true,
        panelWidth:350,//面板宽度
        animate: true,
        collapsible: true,
        columns: [[
            { field: 'id', width: 180,hidden: 'true'},
            { field: 'orgname', title: '组织名', align: 'left', halign: 'center', width:200 },
            { field: 'orgTypeName', title: '组织类别', align: 'left', halign: 'center', width:80},
        ]],
        editable:false,
        onSelect:function(rec){
            $("#areaId").combobox('readonly',false);
            var url=getRootPath__() +"/sys_manage/area_manage/getAreaList?orgId="+rec.id;
            $('#areaId').combobox('reload',url);
        },
        loader : function (params, success, error) {
        	 var select=$.ajax({
        		 async: false,
                 url: getRootPath__() + '/power_manage/plant_manage/getOrgById?id='+id
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
                    url: getRootPath__() + '/power_manage/plant_manage/getOrgList',
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
*/

/**
 * 选中组织时联动项目
 */
function loadLinkedItem(row){
    $("#areaId").combobox('readonly',false);
    var url=getRootPath__() +"/sys_manage/area_manage/getAreaList?orgId="+row.id;
    $('#areaId').combobox('reload',url);
}

/*初始化用户列表*/
function initUserList(id){
	$('#userId').combobox({    
	    url:getRootPath__() + '/power_manage/plant_manage/getUserListByOrgId?orgId=' + id,    
	    valueField:'userid',    
	    textField:'username'   
	});	
}

/*编辑电站时初始化组织名称*/
function getOrgName(orgId,callback){
	$.ajax({  
		async: false,
		url: getRootPath__()+'/power_manage/plant_manage/getOrgConfigById?orgId='+orgId,
		success:function(data){
			callback(data.orgname)
		}
	});
}

/*编辑电站时初始化区域名称*/
function getAreaName(plantId,callback){
    $.ajax({
        async: false,
        url: getRootPath__()+"/power_manage/plant_manage/getOrgAreaType?plantId=" + plantId,
        success:function(data){
            callback(data)
        }
    });
}


	
	
	

