
/* 启动时加载 */
$(function(){
    $("#sales_tab").datagrid({
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
        onSelect:function(index, row){
            selectSales(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
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
            { field: 'closingCost', title: '成交价(元)',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'salesDate', title: '销售日期',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'orderNote', title: '描述',  align: 'left', halign: 'center',width:100,align: 'center'},
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
/** 获取订单编号 **/
function getOrderCode() {
    var Code="DD";
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/order/order_code?config='+Code,
        complete:function(data){
            if(data.status=="200"){
                var orderCode = data.responseJSON.data;
                $("#order_code").val(orderCode.orderCode);
            }
        }
    });
}
//选中后按钮状态
function selectSales(row){
    var state=row.useStatus;
    $('#sales_edit').linkbutton('enable');//修改按钮可用
    $('#sales_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#sales_start').linkbutton('enable');//启用按钮可用
        $('#sales_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#sales_dis').linkbutton('enable');//禁用按钮可用
        $('#sales_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#sales_edit').linkbutton('disable');//修改按钮
    $('#sales_remove').linkbutton('disable');//删除按钮
    $('#sales_start').linkbutton('disable');//启用按钮
    $('#sales_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var salesCode = $('#salesCode').val(); //客户名
    var productionStartDate = $('#productionStartDate').val(); //用户真实姓名
    var productionEndDate = $('#productionEndDate').val(); //用户真实姓名
    $('#sales_tab').datagrid('options').url=getRootPath__()+'/order/list';
    $('#sales_tab').datagrid('reload',{
        salesCode:salesCode,
        productionStartDate:productionStartDate,
        productionEndDate:productionEndDate,
    });
}
/** 重置 **/
function reset(){
    $('#header input').val("");
    $('#sales_tab').datagrid('load',{});
}
/** 新增初始化 */
function addSales(){
    $('#salesForm').form('clear');
    $('#order_status').combobox('setValue', '0');
    getOrderCode();
    $("#sales_data").dialog("setTitle","添加客户信息").dialog("open");
}

/**编辑初始化*/
function editSales() {
    var selectRows = $("#sales_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        var provincialId=selectRows[0].provincialId;
        if(!selectRows[0].useStatus){
            state=0;//启用
        }
        var orderStatus =selectRows[0].orderStatus;
        var saleDate = selectRows[0].salesDate;
        $('#salesForm').form('load', selectRows[0]);//表单加载
        $('#sales_state').val(state);//状态下拉框赋值
        $('#order_status').combobox('setValue',orderStatus);//状态下拉框赋值
        $('#provincial_id').combobox('select',provincialId);//用户类型下拉框赋值
        $('#order_status').combobox('enable');
        $('#sales_date').datebox('setValue',saleDate);
        $('#sales_data').dialog('open').dialog('setTitle', '编辑客户信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $("#salesForm").form("submit", {
        url: getRootPath__()+'/order/saveOrUpdateOrderInfo',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#sales_tab").datagrid("load");
                $("#sales_data").dialog("close");
                buttonDisble();
            }else if(re.data==="异常"){
                $.messager.alert("提示信息", re.errorMsg, 'warning');
            }else{
                msg("保存数据失败！+,"+re.errorMsg);
            }
        }
    });
}

/**删除*/
function deleteSales() {
    var selectRows = $("#sales_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/sales/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#sales_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#sales_tab').datagrid('load');
                            buttonDisble();
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的行！", 'info');
    }
}

function getCustomerList(){
    $("#customer_vo_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/customer/list?useStatus=0',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onDblClickRow:function(row, data) {
            setCustomerInfo(data);
        },
        columns: [[
            { field: 'id', title: '客户ID', hidden: 'true'},
            { field: 'provincialId', title: '省份ID', hidden: 'true'},
            { field: 'customerCode', title: '客户编号',sortable:true, width:80, align: 'left', halign: 'center',align: 'center'},
            { field: 'customerName', title: '客户名',sortable:true, width:50, align: 'left', halign: 'center',align: 'center'},
            { field: 'sex', title: '性别',sortable:true, width:30, halign: 'center',align: 'center',
                formatter: function(value,row,index){
                    if (value=='0'){
                        return "男";
                    }
                    if (value=='2'){
                        return "其他>";
                    }
                    if(value =='1'){
                        return "女";
                    }
                }
            },
            { field: 'telphone', title: '联系电话',  align: 'left', halign: 'center',sortable:true,width:80,align: 'center'},
            { field: 'identityCard', title: '身份证号',  align: 'left', halign: 'center',width:80,align: 'center'},
            { field: 'provincialName', title: '省份',  align: 'left', halign: 'center',width:50,align: 'center'},
            { field: 'address', title: '地址',  align: 'left', halign: 'center',width:120,align: 'center'},
        ]]
    });
}

function getCarList() {
    $("#car_vo_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/car/choose/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'a.car_code',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onDblClickRow:function(row, data) {
            setCarInfo(data);
        },
        columns: [[
            { field: 'id', title: '汽车ID', hidden: 'true'},
            { field: 'carCode', title: '汽车编号',sortable:true, width:150, align: 'left', halign: 'center',align: 'center'},
            { field: 'carName', title: '汽车名称',sortable:true, width:150, halign: 'center',align: 'center'},
            { field: 'carModel', title: '汽车型号',  align: 'left', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'carColor', title: '汽车颜色',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'engineNumber', title: '发动机编号',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'storeInfo', title: '所属仓库',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return value.storeName;
                }
            },
            { field: 'manufacturer', title: '厂商',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    return value.manufacturerName;
                }
            },
            { field: 'productionDate', title: '生产日期',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'carNote', title: '描述',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'flow', title: '流向',  align: 'left', halign: 'center',width:100,align: 'center'},
        ]]
    });
}
function chooseCustomer() {
    getCustomerList();
    $("#customer_vo").dialog("setTitle","双击选择客户！").dialog("open");
}
function chooseCar() {
    getCarList();
    $("#car_vo").dialog("setTitle","双击选择汽车！").dialog("open");
}
function setCarInfo(data) {
    $("#car_id").val(data.id);
    $("#car_name").val(data.carName);
    $("#car_model").val(data.carModel);
    $("#car_color").val(data.carColor);
    $("#car_vo").dialog("close");
}
function setCustomerInfo(data) {
    $("#customer_id").val(data.id);
    $("#customer_name").val(data.customerName);
    $("#customer_vo").dialog("close");
}
function queryCustomer() {
    var customerName = $('#customerName').val(); //客户名
    var customerCode = $('#customerCode').val(); //用户真实姓名
    $('#customer_vo_tab').datagrid('options').url=getRootPath__()+'/customer/choose/list';
    $('#customer_vo_tab').datagrid('reload',{
        customerName:customerName,
        customerCode:customerCode,
        useStatus:0,
    });
}
/** 重置 **/
function resetCustomer(){
    $('#header2 input').val("");
    $('#customer_vo_tab').datagrid('load',{});
}
function queryCar() {
    var carCode = $('#carCode').val(); //客户名
    var carName = $('#carName').val(); //用户真实姓名
    var carModel = $('#carModel').val(); //用户真实姓名
    $('#car_vo_tab').datagrid('options').url=getRootPath__()+'/car/choose/list';
    $('#car_vo_tab').datagrid('reload',{
        carCode:carCode,
        carName:carName,
        carModel:carModel,
    });
}
/** 重置 **/
function resetCar(){
    $('#header1 input').val("");
    $('#car_vo_tab').datagrid('load',{});
}