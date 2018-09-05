
$(function(){
    $.initHeight();
     getStoreSelect();
});


/** 仓库列表 **/
function getStoreSelect(){
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/store/store_select',
        complete:function(data){
            if(data.status=="200"){
                var storeData = data.responseJSON.data;
                $('#storeSelect').combobox({
                    data:storeData,
                    valueField:'id',
                    textField:'storeName',
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox('getData');
                        for (var item in val[0]) {
                            if (item == 'id') {
                                $(this).combobox('select', val[0][item]);
                            }
                        }
                    },
                    onSelect:function (a) {
                        getStorePieData(a.id,a.storeName);
                    }
                });
            }
        }
    });
}
/** 饼图加载 **/
function getStorePieData(storeId,storeName) {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/statistics/store/pie?storeId='+storeId,
        complete:function(data){
            if(data.status=="200"){
                var storeData = data.responseJSON.data;
                var option=getStorePieOption(storeData,storeName);
                var myEcharts = echarts.init($("#store_pie").get(0));
                    myEcharts.setOption(option);
                     $('#store_pie_table').datagrid('loadData', storeData.seriesData);
            }
        }
    });
}
/** 饼图 设置**/
function getStorePieOption(storeData,storeName) {
    var legend = storeData.legendData;
    var series = storeData.seriesData;
    var store_pie_option = {
        title : {
            text: storeName+'统计图',
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
                name: '仓库占比',
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
    return store_pie_option;
}

