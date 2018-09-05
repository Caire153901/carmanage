

function getDateSelect(year) {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/statistics/order/line?year='+year,
        complete:function(data){
            if(data.status=="200"){
                var orderData = data.responseJSON.data;
                var option=getOrderLineOption(orderData);
                var myEcharts = echarts.init($("#order_line").get(0));
                    myEcharts.clear();
                    myEcharts.setOption(option);
                $('#order_line_table').datagrid('loadData', orderData.list);
            }
        }
    });
}
/** 线图 设置**/
function getOrderLineOption(orderData) {
    var xAxisData = orderData.xAxisData;
    var series = orderData.seriesData;
    var legendData = orderData.legendData;
    var order_line_option = {
        title : {
            text: '汽车销售分布图',
          //  x:'center'
        },
        tooltip : {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                label: {
                    backgroundColor: '#6a7985'
                }
            }
        },
        legend: {
            data:legendData
        },
        grid: {
            top: '10%',
            left: '10%',
            right: '10%',
            bottom: '10%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: xAxisData
        },
        yAxis: {
            type: 'value'
        },
        series: series
    };
    return order_line_option;
}