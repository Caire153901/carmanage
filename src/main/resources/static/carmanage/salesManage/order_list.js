
/* 启动时加载 */
$(function(){
    $("#order_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/order/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'a.order_code',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        columns: [[
            { field: 'id', title: '订单ID', hidden: 'true'},
            { field: 'orderCode', title: '订单号',sortable:true, width:150, align: 'left', halign: 'center',align: 'center'},
            { field: 'customer', title: '客户',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return value.customerName;
                }
            },
            { field: 'carInfo', title: '汽车名',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return value.carName;
                }
            },
            { field: 'carInfo.carModel', title: '汽车型号',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return row.carInfo.carModel;
                }
            },
            { field: 'carInfo.carColor', title: '汽车颜色',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return row.carInfo.carColor;
                }
            },
            { field: 'salesDate', title: '销售日期',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'orderStatus', title: '订单状态',  align: 'center', halign: 'center',sortable:true,width:60,
                formatter: function(value,row,index){
                    if (value=='0'){
                        return "未出库";
                    }
                    if (value=='1'){
                        return "已出库";
                    }
                    if(value =='2'){
                        return "在运";
                    }
                    if(value =='3'){
                        return "已送达";
                    }
                    if(value =='4'){
                        return "退货";
                    }
                }
            }
        ]]
    });
});

/** 查询数据条件 */
function checkInputQuery(){
    var orderCode = $('#orderCode').val(); //
    var orderStatus = $('#orderStatus').val();
    var customerName = $('#customerName').val();
    var carName = $('#carName').val();
    var carModel = $('#carModel').val();
    var saleStartDate = $('#saleStartDate').val();
    var saleEndDate = $('#saleEndDate').val();
    $("#order_tab").url=getRootPath__()+'/order/list';
    $('#order_tab').datagrid('reload',{
        orderCode:orderCode,
        orderStatus:orderStatus,
        customerName:customerName,
        carName:carName,
        carModel:carModel,
        saleStartDate:saleStartDate,
        saleEndDate:saleEndDate,
    });
}
/** 重置 **/
function reset(){
    $('#header input').val("");
    $('#orderStatus').combobox('setValue','5');
    checkInputQuery();
}