//单电站信息
function getSingleStation(platId,callback){
	var html="";
		$.ajax({
	        url: getRootPath__() + "/power_manage/single_station/toSingleStation?id="+platId,
	        success: function (data) {
	        var powerName=data.powername==null?"":data.powername;
	        var installed=data.installedcapacity==null?"":data.installedcapacity;
	        var putproductiontime=data.putproductiontime.time==null?"":data.putproductiontime.time;
	        var address=data.address==null?"":data.address;
	        var phone=data.phone==null?"":data.phone;
	        	html=html+"<ul>"
	        	    +"<li>电站名称："+powerName+"</li>"
	        	    +"<li>装机容量："+installed+"K</li>"
	        	    +"<li>投产发电时间："+putproductiontime+"</li>"
	        	    +"<li>详细地址："+address+"</li>"
	        	    +"<li>联系方式："+phone+"</li>"
	        	    +"</ul>"
	           callback(html)  
	        }
    });
}
//实时功率
function ratedPower(plantId){
    var xdata1=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
    var ydata1=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    var curpower=0;
    var RatedChart = echarts.init(document.getElementById('line_power'));
    queryRatedPower({
        platId:plantId,
        date: new Date()
    },function(responseMsg){
        if(responseMsg.status==200){
            if(null!=responseMsg.responseJSON.HourNum) {
                if (responseMsg.responseJSON.HourNum.length > 0 && responseMsg.responseJSON.HourPower.length > 0) {
                    xdata1 = responseMsg.responseJSON.HourNum;
                    ydata1 = responseMsg.responseJSON.HourPower;
                    curpower = ydata1[ydata1.length - 1];
                }
            }
        }
        $('#curpower p').remove();
        $('#curpower').append("<p>"+curpower+"KW</p>").append("<p></p>").append("<p>当前总功率</p>");
        var lineOption=lineEchart(xdata1,ydata1);
      	    RatedChart.setOption(lineOption);
    });
    var $datetimepicker = $('#linepower_datetimepicker');
		$datetimepicker.val(new Date().formate("yyyy-MM-dd"));
		$datetimepicker.datetimepicker({
			format: 'yyyy-mm-dd',
			startView: 2,
			minView: 2,
			autoclose: 1,
            todayBtn : true,
			language: 'zh-CN'
		});
		$datetimepicker.datetimepicker().on('changeDate', function (ev) {
            var xdata1=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
            var ydata1=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
            var curpower=0;
			$('#linepower_datetimepicker').datetimepicker('hide');
				queryRatedPower({
					platId:plantId,
					date:ev.date
				},function(responseMsg){
					if(responseMsg.status==200){
						if(responseMsg.responseJSON.HourNum.length>0 && responseMsg.responseJSON.HourPower.length>0) {
							xdata1 = responseMsg.responseJSON.HourNum;
							ydata1 = responseMsg.responseJSON.HourPower;
                            if(ev.date.formate("yyyy-MM-dd")==(new Date().formate("yyyy-MM-dd"))){
                                var curpowers=0;
                                curpowers = ydata1[ydata1.length - 1];
                                $('#detail_curpower p').remove();
                                $('#detail_curpower').append("<p>"+curpowers+"KW</p>").append("<p></p>").append("<p>当前总功率</p>");
                            }
						}
					}
					var lineOption=lineEchart(xdata1,ydata1);
						RatedChart.setOption(lineOption);
				});
		});

}
//实时功率
function queryRatedPower(params,callback){
    $.ajax({
        url: getRootPath__() + "/fistPages/queryRatedPower",
        method: "post",
        data: params,
        complete:function(data){
            callback(data)
        }
    });
}

//发电信息
function queryDayPlatInfo(platId,callback){
	$.ajax({
		url: getRootPath__() + "/power_manage/single_station/queryPlantInfo?platId="+platId,
		complete:function(data){
			if(data.status==200){
				callback(data.responseJSON)
			}else{
				$.messager.alert("提示", "发电信息数据请求失败！", 'warning');  
			}
			
		}
	});
}
//发电量统计（当月按天统计）
function queryDayPower(platId,callback){
	$.ajax({
		url: getRootPath__() + "/power_manage/single_station/queryPowerReport?platId="+platId,
		complete:function(data){
            callback(data)
		}
	});
}

var date=new Date;
var month=date.getMonth()+1;

function barEahart(xdata,ydata){
	var option_1 = {
		    color: ['#3398DB'],
		    title: {
		        text: '当月发电量统计',
		    },
		    tooltip : {
		        trigger: 'axis',
		        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
		            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
		        }
		    },
		    grid: {
		    	left: '3%',
		        right: '4%',
		        bottom: '10%',
		        containLabel: true
		    },
		    xAxis : [{
		            type : 'category',
		            data : xdata,
		            axisTick: {
		                alignWithLabel: true
		            }
		        }],
		    axisLabel: {  
	                interval: 0,  
	                formatter:function(value)  
	                {
	                	if(value.length>9){
                            return	value.substring(5, 10);
						}else{
                            return	month.toString()+"-"+value;
						}
	                }
	        },
		    yAxis : [{
		            type : 'value',
		            axisLabel : {
                        formatter: '{value} kwh'
                    }
		    }],
		    series : [
		        {
		            name:'电量',
		            type:'bar',
		            barWidth: '60%',
		            data:ydata
		        }
		    ]
		};
	return option_1;
}


      function lineEchart(xdata,ydata){
		    var option_2 = {
		    	   title: {
		    		        text: '当天实时功率',
		    		    },
				    grid: {
				    	top:'23%',
				    	left: '3%',
				        right: '4%',
				        bottom: '5%',
				        containLabel: true
				    },
		            tooltip : {
		                trigger: 'axis'
		            },
		            //右上角工具条
		            toolbox: {
		                show : false,
		                feature : {
		                    mark : {show: true},
		                    dataView : {show: true, readOnly: false},
		                    magicType : {show: true, type: ['line', 'bar']},
		                    restore : {show: true},
		                    saveAsImage : {show: true}
		                }
		            },
		            calculable : true,
		            xAxis : [
		                {
		                    type : 'category',
		                    boundaryGap : false,
		                    data :xdata
		                }
		            ],
				    axisLabel: {  
		                interval: 0,  
		                formatter:function(value)  
		                {  
		                    return	value.substring(10, 16);
		                      
		                }  
		        },
		            yAxis : [
		                {
		                    type : 'value',
		                    axisLabel : {
		                        formatter: '{value} kw'
		                    }
		                }
		            ],
		            series : [
		                {
		                    name:'功率',
		                    type:'line',
		                    data:ydata
		                }
		            ]
		        };
		    return option_2;
     }