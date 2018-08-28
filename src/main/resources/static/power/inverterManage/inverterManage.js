/*初始化电站列表并联动相应的逆变器*/
function initPlantList(userId){
	var userId = userId;
	$('#id').combobox({    
	    url:getRootPath__()+'/power_manage/inverter_manage/getPlantListByUserId?userId=' + userId,   
	    valueField:'id',    
	    textField:'powername',
	    
	    //电站数据加载成功后默认选择用户的第一个电站
	    onLoadSuccess:function(data){
	    	$('#id').combobox('setValue', data[0].id);
	    },
	    
	    //选择电站时联动选择电站对应的设备	/逆变器列表
	    onSelect:function(data){
	    	var provider = data.provider;
	    	var providerstationid = data.providerstationid;
	    	$('#sn').combobox({
	    		url:getRootPath__()+'/power_manage/inverter_manage/getDeviceSnList?provider=' + provider + '&providerstationid=' + providerstationid,
	    		valueField:'deviceSn',
	    		textField:'deviceSn',
	    		
	    		//设备数据加载成功后默认选中第一个逆变器
	    		onLoadSuccess:function(data){
	    	    	$('#sn').combobox('setValue', data[0].deviceSn);
	    	    },
	    		
	    		//选择逆变器编号时加载其对应的逆变器信息
	    		onSelect:function(data2){
	    			var deviceSn = data2.deviceSn;
	    			loadInverterData(deviceSn);  //加载表格
	    			loadInverterConfig(deviceSn);  //加载表格
	    		}
	    		
	    	});	
	    },
	
	});
	
}

/*加载逆变器数据表格*/
function loadInverterData(deviceSn){
	var deviceSn = deviceSn;
	$('#inverterData_tab').datagrid({
		rownumbers:true,
		url:getRootPath__()+'/power_manage/inverter_manage/getInverterData?deviceSn=' + deviceSn,
		columns:[[    
	        {field:'inverterSn',title:'test',hidden:true,width:100},
	        {field:'ipv1',title:'ipv1',width:75,align:'center'},    
	        {field:'ipv2',title:'ipv2',width:75,align:'center'},
	        {field:'ipv3',title:'ipv3',width:75,align:'center'},
	        
	        {field:'vpv1',title:'vpv1',width:75,align:'center'},
	        {field:'vpv2',title:'vpv2',width:75,align:'center'},
	        {field:'vpv3',title:'vpv3',width:75,align:'center'},
	        
	        {field:'iac1',title:'iac1',width:75,align:'center'},
	        {field:'iac2',title:'iac2',width:75,align:'center'},
	        {field:'iac3',title:'iac3',width:75,align:'center'},
	        
	        {field:'vac1',title:'vac1',width:75,align:'center'},
	        {field:'vac2',title:'vac2',width:75,align:'center'},
	        {field:'vac3',title:'vac3',width:75,align:'center'},
	        
	        {field:'datetime',title:'数据时间',width:200,align:'center',
	        	formatter: function(value,row,index){
					return getTime(value)
				}
	        },
	    ]], 
	    
	});
	
}

/*加载逆变器信息表格*/
function loadInverterConfig(deviceSn){
	var deviceSn = deviceSn;
	$('#inverterConfig_tab').datagrid({
		rownumbers:true,
		url:getRootPath__()+'/power_manage/inverter_manage/getInverterData?deviceSn=' + deviceSn,
		columns:[[    
	        {field:'power',title:'输出功率（W）',width:200,align:'center'},    
	        {field:'currentElectric',title:'当天发电量（kWh）',width:200,align:'center'},
	        {field:'totalElectric',title:'累计发电量（kWh）',width:200,align:'center'},
	        {field:'temperature',title:'温度',width:85,align:'center'},
	        
	        {field:'frequency',title:'频率（Hz）',width:200,align:'center'},
	        {field:'datetime',title:'数据时间',width:200,align:'center',
	        	formatter: function(value,row,index){
					return getTime(value)
				}
	        },
	    ]], 
	    
	});
	
}




