
/* 启动时加载 */
$(function(){
    $("#customer_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/customer/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectCustomer(row);
        },
        onLoadSuccess:function(data){
            buttonDisble();
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
    getCustomerProvincial();
});
/**客户省份*/
function getCustomerProvincial(){
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/customer/provincial',
        complete:function(data){
            if(data.status=="200"){
                var provincialData = data.responseJSON.data;
                $('#provincial_id').combobox({
                    data:provincialData,
                    valueField:'id',
                    textField:'name',
                });
                $('#provincialSelect').combobox({
                    data:provincialData,
                    valueField:'id',
                    textField:'name',
                });

            }
        }
    });
}
/** 获取客户编号 **/
function getCustomerCode() {
    var Code="CK";
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/customer/customer_code?config='+Code,
        complete:function(data){
            if(data.status=="200"){
                var customerCode = data.responseJSON.data;
                $("#customer_code").val(customerCode.customerCode);
            }
        }
    });
}
//选中后按钮状态
function selectCustomer(row){
    var state=row.useStatus;
    $('#customer_edit').linkbutton('enable');//修改按钮可用
    $('#customer_remove').linkbutton('enable');//删除按钮可用
    if(state==1){
        $('#customer_start').linkbutton('enable');//启用按钮可用
        $('#customer_dis').linkbutton('disable');//禁用按钮禁用
    }else if(state==0){
        $('#customer_dis').linkbutton('enable');//禁用按钮可用
        $('#customer_start').linkbutton('disable');//启用按钮禁用
    }
}
//按钮禁用初始化
function buttonDisble(){
    $('#customer_edit').linkbutton('disable');//修改按钮
    $('#customer_remove').linkbutton('disable');//删除按钮
    $('#customer_start').linkbutton('disable');//启用按钮
    $('#customer_dis').linkbutton('disable');//禁用按钮
}

/** 查询数据条件 */
function checkInputQuery(){
    var customerName = $('#customerName').val(); //客户名
    var customerCode = $('#customerCode').val(); //用户真实姓名
    var provincialId = $('#provincialSelect').val(); //用户真实姓名
    $('#customer_tab').datagrid('options').url=getRootPath__()+'/customer/list';
    $('#customer_tab').datagrid('reload',{
        customerName:customerName,
        customerCode:customerCode,
        provincialId:provincialId,
    });
}
/** 重置 **/
function reset(){
    $('#customerName').val("");
    $('#customerCode').val("");
    $('#provincialSelect').combobox('setValue', '');
    $('#customer_tab').datagrid('load',{});
}
/** 新增初始化 */
function addCustomer(){
    $('#customerForm').form('clear');
    $('#customer_state').combobox('setValue','0');
    $('#customer_state').combobox('disable');
    getCustomerProvincial();
    getCustomerCode();
    $("#customer_data").dialog("setTitle","添加客户信息").dialog("open");
}

/**编辑初始化*/
function editCustomer() {
    var selectRows = $("#customer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        var provincialId=selectRows[0].provincialId;
        if(!selectRows[0].state){
            state=0;//启用
        }
        $('#customerForm').form('load', selectRows[0]);//表单加载
        $('#customer_state').combobox('setValue',state);//状态下拉框赋值
        getCustomerProvincial();//省份
        $('#provincial_id').combobox('select',provincialId);//用户类型下拉框赋值
        $('#customer_state').combobox('enable');
        $('#customer_data').dialog('open').dialog('setTitle', '编辑客户信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#customer_state').combobox('enable');
    $("#customerForm").form("submit", {
        url: getRootPath__()+'/customer/saveOrUpdateCustomer',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#customer_tab").datagrid("load");
                $("#customer_data").dialog("close");
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
function deleteCustomer() {
    var selectRows = $("#customer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/customer/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#customer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#customer_tab').datagrid('load');
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
/**启用*/
function startUsing(){
    var selectRows = $("#customer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要启用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    async: true,
                    type: 'post',
                    url: getRootPath__()+'/customer/enable',
                    data: {
                        id: id,
                        type:0,
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("启用成功！");
                            $('#customer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("启用失败！")
                            $('#customer_tab').datagrid('load');
                            buttonDisble();
                        }
                    }
                });

            }
        });
    } else {
        $.messager.alert("提示", "请选择要启用的行！", 'info');
    }
}
/**禁用*/
function disableUsing(){
    var selectRows = $("#customer_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0)  {
        $.messager.confirm("提示", "确定要禁用吗？", function (isTrue) {
            if (isTrue) {
                var id = selectRows[0].id;
                $.ajax({
                    type: 'post',
                    async: true,
                    url: getRootPath__()+'/customer/enable',
                    data: {
                        id: id,
                        type:1
                    },
                    complete:function(data){
                        if(data.status=="200"){
                            msg("禁用成功！");
                            $('#customer_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("禁用失败！");
                            $('#customer_tab').datagrid('load');
                            buttonDisble();
                        }
                    }

                }) ;
            }
        });
    } else {
        $.messager.alert("提示", "请选择要禁用的行！", 'info');
    }
}