$(function () {
    var pathName = window.document.location.pathname;
    window.mybase = {};
    var baseurl = window.mybase.BASE_URL = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    if (baseurl === "/largeScreen") {
        baseurl = "";
    }
    baseurl += "/largeScreen";
    var powerName;
    //设置请求为同步
    $.ajaxSetup({async: false});
    //获取数据
    var daydata;//日电量
    var daytime;//日时间
    var monthdata;//月电量
    var monthtime;//月时间
    var maploction;//地图地址
    var mappower;//地图电量
    var powernum;//饼图数据
    var powerstatu;//饼图状态
    var today_ele;// 今日发电量
    var date = new Date();
    var loaddata = function () { //请求数据方法
        $.post(baseurl + "/queryLocationPower",
            {
                powerName: powerName
            },
            function (data, status) {
                maploction = data.maploction;
                mappower = data.mappower;
            });
        $.post(baseurl + "/queryYear2MPower",
            {
                powerName: powerName
            },
            function (data, status) {
                monthdata = data.power;
                monthtime = data.time;
            });
        $.post(baseurl + "/queryPowerStatu",
            {
                powerName: powerName
            },
            function (data, status) {
                powernum = data.num;
                powerstatu = data.statu;
            });
        $.post(baseurl + "/queryMonth2DayPower",
            {
                powerName: powerName
            },
            function (data, status) {
                daydata = data.power;
                daytime = data.time;
            });

        $.post(baseurl + "/getCountum",
            {
                powerName: powerName
            },
            function (data, status) {
                // console.log(data);
                today_ele = data.electric;
                $(".installedCapacity").fadeOut('slow').text((data.installedCapacity * 0.001).toFixed(3)).fadeIn('slow');//总电站容量
                $(".queryCount").fadeOut('slow').text(data.count).fadeIn('slow');//电站数量
                $(".cumulativeOnline").fadeOut('slow').text(completeNum(data.total_electric)).fadeIn('slow');//累计上网电量
                $(".reduceDeforestation").fadeOut('slow').text(completeNum(data.eleTotal_woods)).fadeIn('slow');//减少森林砍伐
                $(".CO2Reduction").fadeOut('slow').text(completeNum(data.eleTotal_carbonDioxide)).fadeIn('slow');//二氧化碳
                $(".saveCoal").fadeOut('slow').text(completeNum(data.eleTotal_standardCoal)).fadeIn('slow');//节约标准煤
                $(".today_ele").fadeOut('slow').text(completeNum(data.electric)).fadeIn('slow');//当日发电量

                daydata[date.getDate() - 1] = today_ele;
                monthdata[date.getMonth()] = (monthdata[date.getMonth()] * 1) + (today_ele * 1) + "";
        });
    };

    //请求数据
    // console.log();
    $(document).ready(function () {
        var daypower = echarts.init(document.getElementById('daypower'));
        //当月上网量
        var monthpower = echarts.init(document.getElementById('monthpower'));
        //运维统计
        var operational = echarts.init(document.getElementById('operational'));
        var maper = echarts.init(document.getElementById('maper'));
        //单位换算kw.h>万Kw.h
        var tomonthdatazu = function(monthdata){
            if(monthdata.length>0){
                var monthdatazu = [];
                for (var i = 0; i < monthdata.length; i++) {
                    monthdatazu.push((monthdata[i]*0.0001).toFixed(3));
                }
                return monthdatazu;
            }
        }
        var convertData = function (data) {
            var geoCoordMap = maploction;
            if (data) {
                var res = [];
                for (var i = 0; i < data.length; i++) {
                    var geoCoord = geoCoordMap[data[i].name];
                    if (geoCoord) {
                        res.push({
                            name: data[i].name,
                            value: geoCoord.concat(data[i].value)
                        });
                    }
                }
                return res;
            }
        };
        var pieData = function (powernum, powerstatu) {
            if (powernum.length == powerstatu.length && powerstatu.length > 0) {
                var res = [];
                for (var i = 0; i < powerstatu.length; i++) {
                    res.push({
                        name: powerstatu[i],
                        value: powernum[i],
                    });
                }
                return res;
            }
        };
        var daytimezu = [];
        var monthtimezu = [];
        var inner = function () {
            loaddata();
            daytimezu = [];
            monthtimezu = [];
            //数据格式转换
            if (daytime.length > 0) {
                for (var i = 0; i < daytime.length; i++) {
                    daytimezu.push(daytime[i] + "日")
                }
            }
            if (monthtime.length > 0) {
                for (var i = 0; i < monthtime.length; i++) {
                    monthtimezu.push(monthtime[i] + "月")
                }
            }
            //单位换算kw.h>万Kw.h

            //当日上网量

            // maper.on('click', function (params) {
            //     var city = params.name;
            //     // var arr = new Array();
            //     // arr=city.split("：");
            //     // powerName=arr[1];
            //     if (params.value != null && params.value != undefined) {
            //         powerName = city;
            //         $("#inpt").val(powerName);
            //     } else {
            //         $("#inpt").val("");
            //     }
            //     $("#Button").click();
            // });




            var pieDatas = pieData(powernum, powerstatu);

            var pieOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: function (params) {
                        return params.name + '<br>' + '电量' + ' : ' + params.value[2];
                    }
                },
                geo: {
                    map: 'china',
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    roam: true,
                    itemStyle: {
                        normal: {
                            areaColor: '#4CB9D6',
                            borderColor: '#2891B1'
                        },
                        emphasis: {
                            areaColor: '#087995'
                        }
                    },
                    regions: [{
                        name: '四川',
                        itemStyle: {
                            normal: {
                                areaColor: '#71C8E6',
                                color: '#71C8E6'
                            }
                        }
                    }]
                },
                series: [
                    {
                        name: '电量',
                        type: 'scatter',
                        coordinateSystem: 'geo',
                        data: convertData(mappower),
                        symbolSize: 8,
                        label: {
                            normal: {
                                formatter: '{b}',
                                position: 'right',
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#91F525'
                            }
                        },
                    },
                    {
                        name: 'Top 5',
                        type: 'effectScatter',
                        coordinateSystem: 'geo',
                        data: convertData(mappower.sort(function (a, b) {
                            return b.value - a.value;
                        }).slice(0, 6)),
                        symbolSize: 10,
                        showEffectOn: 'render',
                        rippleEffect: {
                            brushType: 'stroke'
                        },
                        hoverAnimation: true,
                        label: {
                            normal: {
                                show: false
                            }
                        },
                        itemStyle: {
                            normal: {
                                color: '#91F525',
                                shadowBlur: 20,
                                shadowColor: '#333'
                            }
                        },
                        zlevel: 1
                    }
                ]
            };
            maper.setOption(pieOption);
            // setInterval(function () {
            //     loaddata();
            //
            // },5000);
            var operationalOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                calculable: true,
                legend: {
                    x: 'center',
                    y: 'bottom',
                    data: powerstatu,
                    textStyle: {
                        color: "#fff",
                    }
                },
                series: [
                    {
                        name: '运维统计',
                        type: 'pie',
                        selectedMode: 'single',
                        radius: ['20%', '70%'],
                        //roseType : 'radius',
                        label: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        labelLine: {
                            normal: {
                                show: true
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: pieDatas,
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    var colorList = ['#E15D4B', '#28AB3B'];
                                    return colorList[params.dataIndex];
                                },
                                label: {
                                    formatter: function (params) {
                                        return (params.percent - 0).toFixed(1) + '%'
                                    }
                                },
                            }
                        }
                    }
                ]
            };

            var dayPowerOption;
            if (daytimezu.length >= 3) {
                dayPowerOption = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        left: 'center',
                        textStyle: {
                            color: '#fff',
                            fontFamily: "宋体"
                        }
                    },
                    grid: {
                        x: 5,
                        y: 35,
                        x2: 5,
                        y2: 5,
                        containLabel: true
                    },
                    xAxis: [
                        {
                            type: 'category',
                            boundaryGap: false,
                            data: daytimezu,
                            axisLabel: {
                                show: true,
                                textStyle: {
                                    color: '#fff'
                                },
                            },
                            axisLine: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            },
                            axisTick: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: "kw",
                            nameTextStyle: {
                                color: '#fff'
                            },
                            axisLabel: {
                                formatter: '{value}',
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }
                            },

                            splitLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }

                            },
                        }
                    ],
                    series: [
                        {
                            name: "电量",
                            type: "line",
                            data: daydata,
                            smooth: true,
                            showSymbol: false,
                            itemStyle: {
                                normal: {
                                    color: "#008EFC",
                                    areaStyle: {
                                        color: '#0794A8'
                                    },
                                    lineStyle: {
                                        color: '#0794A8',
                                        width: 0,
                                    }
                                }
                            }
                        }
                    ]
                };

            } else {
                dayPowerOption = {
                    tooltip: {
                        show: true
                    },
                    grid: {
                        x: 5,
                        y: 35,
                        x2: 5,
                        y2: 5,
                        containLabel: true
                    },

                    xAxis: [
                        {
                            type: 'category',
                            data: daytimezu,
                            axisLabel: {
                                show: true,
                                interval: 0,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            },
                            axisTick: {
                                //length: 5,
                                lineStyle: {
                                    color: "#2D4174",

                                }
                            }
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value',
                            name: "kw.h",
                            nameTextStyle: {
                                color: '#fff'
                            },
                            axisLabel: {
                                formatter: '{value}',
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }
                            },
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }

                            },
                        }
                    ],
                    series: [
                        {
                            name: "电量",
                            type: "bar",
                            barWidth: "20",
                            data: daydata,
                            itemStyle: {
                                normal: {
                                    color: "#0794A8"
                                }
                            }
                        }
                    ]
                };
            }
            daypower.setOption(dayPowerOption);

            var monthpowerOption;
            if (monthtimezu.length >= 3) {
                monthpowerOption= {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        }
                    },
                    title: {
                        left: 'center',
                        textStyle: {
                            color: '#fff',
                            fontFamily: "宋体"
                        }
                    },
                    grid: {
                        x: 5,
                        y: 35,
                        x2: 13,
                        y2: 5,
                        containLabel: true
                    },
                    xAxis: [
                        {
                            data: monthtimezu,
                            boundaryGap: false,
                            axisLabel: {
                                show: true,
                                interval: 0,
                                textStyle: {
                                    color: '#fff'
                                },
                            },
                            axisLine: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            },
                            axisTick: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            }
                        }
                    ],

                    yAxis: [
                        {
                            type: 'value',
                            name: "万kw.h",
                            nameTextStyle: {
                                color: '#fff'
                            },
                            axisLabel: {
                                formatter: '{value}',
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }
                            },

                            splitLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }

                            },
                        }
                    ],
                    series: [
                        {
                            name: "电量",
                            type: "line",
                            data: tomonthdatazu(monthdata),
                            smooth: true,
                            showSymbol: false,
                            itemStyle: {
                                normal: {
                                    color: "#008EFC",
                                    areaStyle: {
                                        color: '#0794A8'
                                    },
                                    lineStyle: {
                                        color: '#0794A8',
                                        width: 0
                                    }
                                }
                            }
                        }
                    ]
                };
            } else {
                monthpowerOption = {
                    tooltip: {
                        show: true
                    },
                    grid: {
                        x: 5,
                        y: 35,
                        x2: 5,
                        y2: 5,
                        containLabel: true
                    },

                    xAxis: [
                        {
                            type: 'category',
                            data: monthtimezu,

                            axisLabel: {
                                show: true,
                                interval: 0,
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                lineStyle: {
                                    color: "#2D4174"
                                }
                            },
                            axisTick: {
                                //length: 5,
                                lineStyle: {
                                    color: "#2D4174",

                                }
                            }
                        }
                    ],

                    yAxis: [
                        {
                            type: 'value',
                            name: "kw.h",
                            nameTextStyle: {
                                color: '#fff'
                            },
                            axisLabel: {
                                formatter: '{value}',
                                textStyle: {
                                    color: '#fff'
                                }
                            },
                            axisLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }
                            },
                            splitLine: {
                                show: true,
                                lineStyle: {
                                    // 使用深浅的间隔色
                                    color: "#2D4174"
                                }

                            },
                        }
                    ],
                    series: [
                        {
                            name: "电量",
                            type: "bar",
                            barWidth: "20",
                            data: tomonthdatazu(monthdata),
                            itemStyle: {
                                normal: {
                                    color: "#0794A8"
                                }
                            }
                        }
                    ]
                };
            }
            monthpower.setOption(monthpowerOption);

            operational.setOption(operationalOption);
        };
        inner();
        setInterval(function () {
            inner();
        },10*60*1000);


    });
    //支持echarts响应式的方法
    $(window).resize(function () {
        windowchange();
        inner();
    });
});