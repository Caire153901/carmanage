
$(function(){
    $.initHeight();
    getCustomerPieData();
});

/** 饼图加载 **/
function getCustomerPieData() {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/statistics/customer/pie',
        complete:function(data){
            if(data.status=="200"){
                var customerData = data.responseJSON.data;
                var option=getCustomerPieOption(customerData);
                var myEcharts = echarts.init($("#customer_pie").get(0));
                    myEcharts.setOption(option);
                $('#customer_pie_table').datagrid('loadData', customerData.seriesData);
            }
        }
    });
}
/** 饼图 设置**/
function getCustomerPieOption(customerData) {
    var legend = customerData.legendData;
    var series = customerData.seriesData;
    var customer_pie_option = {
        title : {
            text: '客户统计图',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)",
            textStyle:{
                fontSize:20,
                fontWeight:'bold',
            }
        },
        color:['#D3283F','#364d5b','#dde24d','#9fdbbf'],
        legend: {
            orient: 'vertical',
            left: 'right',
            data: legend
        },
        series : [
            {
                name: '客户数量占比',
                type: 'pie',
                radius : '55%',
                center: ['50%', '60%'],
                data:series,
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },
                label: {
                    normal: {
                        textStyle: {
                            fontSize:20
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgb(54, 77, 91)'

                        },
                        length: 20,
                        length2: 30
                    }
                },
            }
        ]
    };
    return customer_pie_option;
}

