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
            buttonDisble();
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
    var state=row.useStatus;
    $('#visit_edit').linkbutton('enable');//修改按钮可用
    $('#visit_remove').linkbutton('enable');//删除按钮可用
}
//按钮禁用初始化
function buttonDisble(){
    $('#visit_edit').linkbutton('disable');//修改按钮
    $('#visit_remove').linkbutton('disable');//删除按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var customerName = $('#customerName').val(); //客户名
    var orderCode = $('#orderCode').val(); //用户真实姓名
    $('#visit_tab').datagrid('options').url=getRootPath__()+'/visit/list';
    $('#visit_tab').datagrid('reload',{
        customerName:customerName,
        orderCode:orderCode,
    });
}
/** 重置 **/
function reset(){
    $('#customerName').val("");
    $('#orderCode').val("");
    $('#visit_tab').datagrid('load',{});
}
function getCustomerSelect() {
    
}
function getOrderSelect(){

}
/** 新增初始化 */
function addVisit(){
    $('#visitForm').form('clear');
    $('#visit_state').combobox('setValue','0');
    $('#visit_state').combobox('disable');
    $("#visit_data").dialog("setTitle","添加客户信息").dialog("open");
}

/**编辑初始化*/
function editVisit() {
    var selectRows = $("#visit_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        if(!selectRows[0].state){
            state=0;//启用
        }
        $('#visitForm').form('load', selectRows[0]);//表单加载
        $('#visit_state').combobox('setValue',state);//状态下拉框赋值
        $('#visit_state').combobox('enable');
        $('#visit_data').dialog('open').dialog('setTitle', '编辑客户信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#visit_state').combobox('enable');
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
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#visit_tab').datagrid('load');
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
