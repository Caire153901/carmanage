
/* 启动时加载 */
$(function(){
    $("#visit_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/visit/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectVisit(row);
        },
        onLoadSuccess:function(data){
            buttonDisbles();
        },
        columns: [[
            { field: 'id', title: '回访ID', hidden: 'true'},
            { field: 'customer.id', title: '客户ID', hidden: 'true'},
            { field: 'orderInfo.id', title: '订单ID', hidden: 'true'},
            { field: 'customer', title: '客户名',sortable:true, width:80, align: 'left', halign: 'center',align: 'center',
                formatter: function(value,row,index){
                    return value.customerName
                }
            },
            { field: 'orderInfo', title: '订单号',sortable:true, width:50, align: 'left', halign: 'center',align: 'center',
                formatter: function(value,row,index){
                    return value.orderCode
                }
            },
            { field: 'visitDate', title: '回访时间',sortable:true, width:50, align: 'left', halign: 'center',align: 'center'},
            { field: 'visitEvents', title: '回访事件',  align: 'left', halign: 'center',sortable:true,width:80,align: 'center'},
            { field: 'visitRecord', title: '回访记录',  align: 'left', halign: 'center',width:80,align: 'center'},
            { field: 'useStatus', title: '状态',  align: 'center', halign: 'center',sortable:true,width:30,
                formatter: function(value,row,index){
                    if (value=='0'){
                        return "<span class='iconfont icon-chenggong' style='color:#1AE61A'></span>";
                    }
                    if (value=='2'){
                        return "<span class='iconfont icon-iconset0187' style='color:red'></span>";
                    }
                    if(value =='1'){
                        return "<span class='iconfont icon-dengpao' style='color:#aa00ff'></span>";
                    }
                }
            }
        ]]
    });
});


//选中后按钮状态
function selectVisit(row){
    $('#visit_edit').linkbutton('enable');//修改按钮可用
    $('#visit_remove').linkbutton('enable');//删除按钮可用
}
//按钮禁用初始化
function buttonDisbles(){
    $('#visit_edit').linkbutton('disable');//修改按钮
    $('#visit_remove').linkbutton('disable');//删除按钮
}
function chooseOrder(){
    getOrderList();
    $("#order_vo").dialog("setTitle","双击选择订单！").dialog("open");
}
/** 查询数据条件 */
function checkInputQuerys(){
    var customerName = $("#customerName").val();
    var orderCode = $("#orderCode").val();
    $('#visit_tab').datagrid('options').url=getRootPath__()+'/visit/list';
    $('#visit_tab').datagrid('reload',{
        customerName:customerName,
        orderCode:orderCode,
    });
}
/** 重置 **/
function resets(){
    $('#header input').val("");
    $('#visit_tab').datagrid('load',{});
}
/** 新增初始化 */
function addVisit(){
    $('#visitForm').form('clear');
    getOrderList();
    $("#visit_data").dialog("setTitle","添加回访信息").dialog("open");
}

/**编辑初始化*/
function editVisit() {
    var selectRows = $("#visit_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var visitDate = selectRows[0].visitDate;
        var customerId = selectRows[0].customer.id;
        var customerName = selectRows[0].customer.customerName;
        var orderId = selectRows[0].orderInfo.id;
        var orderCode = selectRows[0].orderInfo.orderCode;
        $('#visitForm').form('load', selectRows[0]);//表单加载
        $('#visit_date').datebox('setValue',visitDate);
        $('#customer_id').val(customerId);
        $('#customer_name').val(customerName);
        $('#order_id').val(orderId);
        $('#order_code').val(orderCode);
        getOrderList();
        $('#visit_data').dialog('open').dialog('setTitle', '编辑回访信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdds(){
    $("#visitForm").form("submit", {
        url: getRootPath__()+'/visit/saveOrUpdateVisit',
        onsubmit: function () {
                return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#visit_tab").datagrid("load");
                $("#visit_data").dialog("close");
                buttonDisbles();
            }else if(re.data==="异常"){
                $.messager.alert("提示信息", re.errorMsg, 'warning');
            }else{
                msg("保存数据失败！+,"+re.errorMsg);
            }
        }
    });
}

/**删除*/
function deleteVisit() {
    var selectRows = $("#visit_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/visit/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#visit_tab').datagrid('load');
                            buttonDisbles();
                        }else{
                            msg("删除失败！");
                            $('#visit_tab').datagrid('load');
                            buttonDisbles();
                        }
                    }
                });
            }
        });
    } else {
        $.messager.alert("提示", "请选择要删除的行！", 'info');
    }
}

function getOrderList(){
    $("#order_vo_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:10,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/order/list?orderStatus=0',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'sales_date',//默认传参 sort
        sortOrder : 'desc',//默认传参 order吧
        rownumbers:true,
        onDblClickRow:function(row, data) {
            setOrderInfo(data);
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
        ]]
    });
}

function setOrderInfo(data) {
    $("#customer_id").val(data.customer.id);
    $("#customer_name").val(data.customer.customerName);
    $("#order_id").val(data.id);
    $("#order_code").val(data.orderCode);
    $("#order_vo").dialog("close");
}
function queryOrder() {
    var orderCode = $('#order_code_query').val(); //客户名
    $('#order_vo_tab').datagrid('options').url=getRootPath__()+'/order/list';
    $('#order_vo_tab').datagrid('reload',{
        orderCode:orderCode,
        useStatus:0,
    });
}
/** 重置 **/
function resetOrder(){
    $('#header1 input').val("");
    $('#order_vo_tab').datagrid('load',{});
}