//页签选中事件
function TabSelect(plantId){
    $('#tt').tabs({
        onSelect: function(title,index){//重新渲染图表
            var tabid=$("div[dataTab]").eq(index).find(".detail_report").attr("id");
            if(tabid){
                var myEcharts = echarts.getInstanceByDom(document.getElementById(tabid));
                if (!myEcharts){
                    myEcharts = echarts.init(document.getElementById(tabid));
                }
                myEcharts.resize();
            }
        }
    });
}
//电站信息
function stationTab(plantId){
	getSingleStation(plantId,function(responseMsg){
		$("#detailinfo_title").append("<div id='reuqest_info'>"+responseMsg+"</div>");
	});
}

//单电站信息
function getSingleStation(platId,callback){
    var html="";
    $.ajax({
        url: getRootPath__() + "/fistPages/toSingleStation?id="+platId,
        success: function (data) {
            var powerName=data.powername==null?"":data.powername;
            var installed=data.installedcapacity==null?"":data.installedcapacity;
            var putproductiontime=data.putproductiontime.time==null?"":data.putproductiontime.time;
            var address=data.address==null?"":data.address;
            var phone=data.phone==null?"":data.phone;
            html=html+"<ul>"
                +"<li>电站名称："+powerName+"</li>"
                +"<li>装机容量："+installed+"K</li>"
                +"<li>投产发电时间："+tranTime(putproductiontime)+"</li>"
                +"<li>详细地址："+address+"</li>"
                +"<li>联系方式："+phone+"</li>"
                +"</ul>"
            callback(html)
        }
    });
}
function tranTime(putproductiontime) {
    return new Date(parseInt(("/Date("+putproductiontime+")/").substr(6, 13))).toLocaleDateString();
}
//实时功率
function ratedPower(plantId){
	var xdata1=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
	var ydata1=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    var curpower=0;
    var RatedChart = echarts.init(document.getElementById('line_power_detail'));
        queryRatedPower({
            platId:plantId,
            date: new Date()
        },function(responseMsg){
            if(responseMsg.status==200){
                if(null!=responseMsg.responseJSON.HourNum){
                    if(responseMsg.responseJSON.HourNum.length>0 && responseMsg.responseJSON.HourPower.length>0) {
                        xdata1 = responseMsg.responseJSON.HourNum;
                        ydata1 = responseMsg.responseJSON.HourPower;
                    }
                }
            }
          /*  $('#detail_curpower p').remove();
            $('#detail_curpower').append("<p>"+curpower+"KW</p>").append("<p></p>").append("<p>当前总功率</p>");*/
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
        $('#linepower_datetimepicker').datetimepicker('hide');
            var xdata1=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
            var ydata1=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
            var RatedChart = echarts.init(document.getElementById('line_power_detail'));
            queryRatedPower({
                platId:plantId,
                date:ev.date
            },function(responseMsg){
                if(responseMsg.status==200){
                    if(responseMsg.responseJSON.HourNum.length>0 && responseMsg.responseJSON.HourPower.length>0) {
                        xdata1 = responseMsg.responseJSON.HourNum;
                        ydata1 = responseMsg.responseJSON.HourPower;
/*                        if(ev.date.formate("yyyy-MM-dd")==(new Date().formate("yyyy-MM-dd"))){
                            var curpowers=0;
                                curpowers = ydata1[ydata1.length - 1];
                            $('#detail_curpower p').remove();
                        }*/
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
function dayPlantInfo(plantId){
    var eTotal="0&nbsp;&nbsp;KWh",eDay="0&nbsp;&nbsp;kW·h",curPower="0&nbsp;&nbsp;KW",totalIncome="￥&nbsp;&nbsp;0";
 	queryDayPlatInfo(plantId,function(msg){
	    eTotal=msg.eTotal==null?"0&nbsp;&nbsp;kW·h":msg.eTotal;
	    eDay=msg.eDay==null?"0&nbsp;&nbsp;kW·h":msg.eDay;
	    curPower=msg.eCurPower==null?"0&nbsp;&nbsp;kw":msg.eCurPower;
	    totalIncome=msg.eInCome==null?" ￥&nbsp;&nbsp;0":msg.eInCome;
		$('#detail_etotal').append("<p>"+eTotal+"</p>").append("<p></p>").append("<p>总发电量</p>");
		$('#detail_eday').append("<p>"+eDay+"</p>").append("<p></p>").append("<p>当日发电量</p>");
		$('#detail_curpower').append("<p>"+curPower+"</p>").append("<p>当前总功率</p>");
		$('#detail_totalincome').append("<p>"+totalIncome+"</p>").append("<p></p>").append("<p>总收益</p>");
	}); 
}
//发电信息
function queryDayPlatInfo(platId,callback){
    $.ajax({
        url: getRootPath__() + "/fistPages/queryPlantInfo?platId="+platId,
        complete:function(data){
            if(data.status==200){
                callback(data.responseJSON)
            }else{
                $.messager.alert("提示", "发电信息数据请求失败！", 'warning');
            }

        }
    });
}
//发电量统计
function dayPower(plantId){
	var xdata2=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31];
	var ydata2=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
		queryDayPower(plantId,function(msgs){
			/*if(msgs.status==200){
			    if(msgs.responseJSON.option.length>0 && msgs.responseJSON.barData.length>0){
                    xdata2=msgs.responseJSON.xDataList;
                    ydata2=msgs.responseJSON.barData;
                }
			}
			var barOption=barEahart(xdata2,ydata2);*/
			var dayPowerChart = echarts.init(document.getElementById('ele_detailpower'));
				dayPowerChart.setOption(msgs.responseJSON.option);
		});
}
//发电量统计（当月按天统计）
function queryDayPower(platId,callback){
    $.ajax({
        url: getRootPath__() + "/fistPages/queryPowerReport?platId="+platId,
        complete:function(data){
            callback(data)
        }
    });
}
/**
 * 填充数据到大方格
 */
function fillBigRec(re,type) {
/*    if(type==="allyear"){
        $("#"+type+"_energy").html(completeNum(re.allTotalEnergy)+"&nbsp;&nbsp;kW·h").fadeIn();
        $("#"+type+"_carbonDioxide").html(completeNum(re.totalReduction.carbonDioxide)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_standardCoal").html(completeNum(re.totalReduction.standardCoal)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_carbon").html(completeNum(re.totalReduction.carbon)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_woods").html(completeNum(re.totalReduction.woods)+"&nbsp;&nbsp;m<sup>3</sup>").fadeIn();
        $("#"+type+"_income").html(completeNum(re.allTotalIncome)+"&nbsp;&nbsp;元").fadeIn();
    }else{*/
        $("#"+type+"_energy").html(completeNum(re.totalEnergy)+"&nbsp;&nbsp;kW·h").fadeIn();
        $("#"+type+"_carbonDioxide").html(completeNum(re.reduction.carbonDioxide)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_standardCoal").html(completeNum(re.reduction.standardCoal)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_carbon").html(completeNum(re.reduction.carbon)+"&nbsp;&nbsp;T").fadeIn();
        $("#"+type+"_woods").html(completeNum(re.reduction.woods)+"&nbsp;&nbsp;m<sup>3</sup>").fadeIn();
        $("#"+type+"_income").html(completeNum(re.totalIncome)+"&nbsp;&nbsp;元").fadeIn();
    //}


}
/*初始化电站列表并联动相应的逆变器*/
function inverterData(plantId,flag){
    $('#inverter_sn').combobox({
       url:getRootPath__()+'/fistPages/getInverterByPlantId?plantId=' + plantId,
       valueField:'dlId',
       textField:'deviceSn',
        //设备数据加载成功后默认选中第一个逆变器
       onLoadSuccess:function(data){
           if(flag){
               $('#inverter_sn').combobox('setValue', data[0].dlId);
           }
       },
       //选择逆变器编号时加载其对应的逆变器信息
       onSelect:function(data){
           $('#inverterSn').val(data.deviceSn);
           var stationId=data.providerstationid;
           var type=data.provider;
           var inverterSn=data.deviceSn;
           inveterInfo(stationId,type,inverterSn);
           flag=false;
       }

    });
}

/*页面加载时初始化逆变器信息*/
function inveterInfo(stationId,type,inverterSn){
    var power=0;var temperature=0;
    var todayEnergy=0; var fac=0;
    var totalEnergy=0; var time="";
    var ipv1=0; var ipv2=0; var ipv3=0;
    var iac1=0; var iac2=0; var iac3=0;
    var vpv1=0; var vpv2=0; var vpv3=0;
    var vac1=0; var vac2=0; var vac3=0;
    $.ajax({
        url: getRootPath__() + "/fistPages/selectInverterByPlantId",
        method: "post",
        data: {
            stationId:stationId,
            type:type,
            inverterSn:inverterSn
        },
        dataType : "json",
        success: function (re) {
            if(re.length>0){
                power=re[0].power;todayEnergy=re[0].currentElectric;
                totalEnergy=re[0].totalElectric;temperature=re[0].temperature;
                fac=re[0].frequency;time=getTime(re[0].datetime);
                ipv1=re[0].ipv1;ipv2=re[0].ipv2;ipv3=re[0].ipv3;
                iac1=re[0].iac1;iac2=re[0].iac2;iac3=re[0].iac3;
                vpv1=re[0].vpv1;vpv2=re[0].vpv2;vpv3=re[0].vpv3;
                vac1=re[0].vac1;vac2=re[0].vac2;vac3=re[0].vac3;
            }
            $("#invert_power").html(power).fadeIn();
            $("#invert_todayEnergy").html(todayEnergy).fadeIn();
            $("#invert_totalEnergy").html(totalEnergy).fadeIn();
            $("#invert_temperature").html(temperature).fadeIn();
            $("#invert_fac").html(fac).fadeIn();
            $("#invert_time").html(time).fadeIn();
            $("#invert_ipv1").html(ipv1).fadeIn();
            $("#invert_ipv2").html(ipv2).fadeIn();
            $("#invert_ipv3").html(ipv3).fadeIn();
            $("#invert_iac1").html(iac1).fadeIn();
            $("#invert_iac2").html(iac2).fadeIn();
            $("#invert_iac3").html(iac3).fadeIn();
            $("#invert_vpv1").html(vpv1).fadeIn();
            $("#invert_vpv2").html(vpv2).fadeIn();
            $("#invert_vpv3").html(vpv3).fadeIn();
            $("#invert_vac1").html(vac1).fadeIn();
            $("#invert_vac2").html(vac2).fadeIn();
            $("#invert_vac3").html(vac3).fadeIn();
        }
    });
}
/*逆变器报警信息*/
function inverterAlert(plantId) {
    handledAlert(plantId);
    unhandledAlert(plantId);
}
/*已处理报警信息*/
function handledAlert(plantId) {
    $('#handled_dg').datagrid('options').url=getRootPath__()+'/fistPages/handledAlert/list';
    $('#handled_dg').datagrid('reload',{
        plantId:plantId
    });
}
/*未处理报警信息*/
function unhandledAlert(plantId) {
    $('#unhandled_dg').datagrid('options').url=getRootPath__()+'/fistPages/unhandledAlert/list';
    $('#unhandled_dg').datagrid('reload',{
        plantId:plantId
    });
}

/*告警信息时间处理*/
function formatTime(val, row, index) {
    if(val){
        return new Date(val.time).formate("yyyy-MM-dd hh:mm:ss");
    }

}
/*已处理查询*/
function query() {
    var plantId=$("#query_plantId").val();
    $("#handled_msg1").hide().prev().removeClass('invalid-red');
    $("#handled_msg2").hide().prev().removeClass('invalid-red');
    var $datetimepicker = $(".datetimepicker");
    var fromDate = $datetimepicker.eq(0).val();
    if ("" === fromDate) {
        $("#handled_msg1").css("display","inline-block").prev().focus().addClass('invalid-red');
        return false;
    }
    var toDate = $datetimepicker.eq(1).val();
    if ("" === toDate) {
        $("#handled_msg2").css("display","inline-block").prev().focus().addClass('invalid-red');
        return false;
    }
    $("#handled_dg").datagrid("load", {
        fromDate: fromDate,
        toDate: toDate,
        plantId:plantId
    });
}
/*未处理查询*/
function querys() {
    var plantId=$("#query_plantId").val();
    $("#unhandled_msg1").hide().prev().removeClass('invalid-red');
    $("#unhandled_msg2").hide().prev().removeClass('invalid-red');
    var $datetimepicker = $(".datetimepicker");
    var fromDate = $datetimepicker.eq(2).val();
    if ("" === fromDate) {
        $("#unhandled_msg1").css("display","inline-block").prev().focus().addClass('invalid-red');
        return false;
    }

    var toDate = $datetimepicker.eq(3).val();
    if ("" === toDate) {
        $("#unhandled_msg2").css("display","inline-block").prev().focus().addClass('invalid-red');
        return false;
    }
    $("#unhandled_dg").datagrid("load", {
        fromDate: fromDate,
        toDate: toDate,
        plantId:plantId
    });
}
/*标记*/
function confirm() {
    var selectedRows = $("#unhandled_dg").datagrid("getSelections");
    var plantId=$("#query_plantId").val();
    if (selectedRows.length > 0) {
        $.messager.confirm("提示", "确定要标记为已处理吗？", function (isTrue) {
            if (isTrue) {
                var aids = [];
                $.each(selectedRows, function (i, v) {
                    aids.push(v.aid);
                });
                $.ajax({
                    url: getRootPath__() + "/OM/unhandledAlert/confirm",
                    method: 'post',
                    data: {
                        aids: aids
                    },
                    dataType: "html",
                    success: function (re) {
                         msg("处理成功");
                        $("#unhandled_dg").datagrid("reload", {plantId:plantId});
                        $("#handled_dg").datagrid("reload",{plantId:plantId})
                      //  window.document.location.reload();
                    }
                });

            }
        });
    }
    else {
        $.messager.alert("提示", "请选择要处理的行！", 'info');
    }
}
/*重置*/
function reset(typename) {
    var plantId=$("#query_plantId").val();
    $("#"+typename+"_msg1").hide().prev().removeClass('invalid-red');
    $("#"+typename+"_msg2").hide().prev().removeClass('invalid-red');
    $(".datetimepicker").val("");
    $("#"+typename+"_dg").datagrid("load", {plantId:plantId});
}

//日报信息
function detailReport(plantId){
	
}
//月报信息
function monthReport(plantId){	
    var myEcharts = echarts.init(document.getElementById('station_month_report'));
	    getData({
	        type: 1,
	        plantId:plantId,
	        year: new Date().getFullYear(),
	        month : new Date().getMonth() + 1,
	    }, function (re) {
	        // 渲染表格
	    	fillBigRec(re,"month");
	        $('#station_month_tab').datagrid('loadData', re.data);
	        // 渲染图形
	        myEcharts.setOption(re.option);
	        // todo 渲染表格
	    });
    var $datetimepicker = $('#month_datetimepicker');
        $datetimepicker.val(new Date().formate("yyyy-MM"));
        $datetimepicker.datetimepicker({
            format: 'yyyy-mm',
            language : "zh-CN",
            startView : "year",
            minView : "year",
        });
        $datetimepicker.datetimepicker().on('changeMonth', function (ev) {
            $('#month_datetimepicker').datetimepicker('hide');
            getData({
                type: 1,
                plantId:plantId,
                year: ev.date.getFullYear(),
                month: ev.date.getMonth() + 1
            }, function (re) {
                // 渲染表格
                $('#station_month_tab').datagrid('loadData', re.data);
                // 渲染图形
                myEcharts.setOption(re.option);
                fillBigRec(re,"month");
            });
        });
}
//年报信息
function yearReport(plantId){
     var myEcharts = echarts.init(document.getElementById('station_year_report'));
         getData({
             type: 2,
             plantId:plantId,
             year: new Date().getFullYear()
         }, function (re) {
             // 填充数据到大方块
        	 fillBigRec(re,"year");
             // 渲染图形
             myEcharts.setOption(re.option);
             // 渲染表格
             $('#station_year_tab').datagrid('loadData', re.data);
         });
     var $datetimepicker = $('#year_datetimepicker');
         $datetimepicker.val(new Date().getFullYear());
         $datetimepicker.datetimepicker({
             format: 'yyyy',
             language: "zh-CN",
             startView: "decade",
             minView: "decade",
         });
         $datetimepicker.datetimepicker().on('changeYear', function (ev) {
             $('#year_datetimepicker').datetimepicker('hide');
             getData({
                 type: 2,
                 plantId:plantId,
                 year: ev.date.getFullYear()
             }, function (re) {
                 // 填充数据到大方块
            	 fillBigRec(re,"year");
                 // 渲染图形
                 myEcharts.setOption(re.option);
                 // 渲染表格
                 $('#station_year_tab').datagrid('loadData', re.data);

             });
         });
}
//历年报信息
function yearsReport(plantId){
  var myEcharts = echarts.init(document.getElementById('station_years_report'));
        getData({
            type: 3,
            plantId:plantId
        }, function (re) {
            // 填充数据到大方块
        	fillBigRec(re,"allyear");
            $("#income").html(re.income).fadeIn();
            $("#energy").html(re.numValue).fadeIn();
            // 渲染图形
            myEcharts.setOption(re.option);
            // 渲染表格
            $('#station_years_tab').datagrid('loadData', re.data);
        });
}
//报表信息列表
function getData(params,callback) {
    $.ajax({
        url: getRootPath__() + "/fistPages/produceInfReport",
        method: "post",
        data: params,
        dataType : "json",
        success: function (re) {
            callback(re);
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
        grid: {
            top:'15%',
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