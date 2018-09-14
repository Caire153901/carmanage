
/* 启动时加载 */
$(function(){
    $("#car_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/car/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'a.car_code',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectCar();
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '汽车ID', hidden: 'true'},
            { field: 'carCode', title: '汽车编号',sortable:true, width:150, align: 'left', halign: 'center',align: 'center'},
            { field: 'carName', title: '汽车名称',sortable:true, width:150, halign: 'center',align: 'center'},
            { field: 'carModel', title: '汽车型号',  align: 'left', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'carColor', title: '汽车颜色',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'engineNumber', title: '发动机编号',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'yieldly', title: '产地',  align: 'left', halign: 'center',width:100,align: 'center'},
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
            { field: 'storageDate', title: '入库日期',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'carNote', title: '描述',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'flow', title: '流向',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'useStatus', title: '状态',  align: 'center', halign: 'center',sortable:true,width:60,
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
            },
            { field: 'imgUrl', title: '查看',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    var image = row.carName+"_"+row.carModel+"_"+row.carColor;
                    return  "<a href='#' onclick=\"openWin('"+image+"','"+image+"')\"><span class='iconfont icon-tupian'></span></a>";
                }
            }
        ]]
    });
    getStoreSelect();
    getManufacturerSelect();
});
/*展示图片*/
function openWin(title,image){
    $('#win').window({
        width:650,
        height:500,
        title:title,
        closed:true,
        modal:true
    });
    if(image==undefined || image==""){
        $.messager.alert("提示","未上传图片","warning");
    }else{
        var imgPath=getRootPath__()+"/main/show/img?imgName="+image;
        $("#stationImg").attr('src',imgPath);
        $("#win").window("open");
    }
}
//选中后按钮状态
function selectCar(){
    $('#car_edit').linkbutton('enable');//修改按钮可用
    $('#car_remove').linkbutton('enable');//删除按钮可用
}
//按钮禁用初始化
function buttonDisble(){
    $('#car_edit').linkbutton('disable');//修改按钮
    $('#car_remove').linkbutton('disable');//删除按钮
}
/** 查询数据条件 */
function checkInputQuery(){
    var carCode = $('#carCode').val(); //汽车编号
    var carName = $('#carName').val(); //汽车名称
    var carModel = $('#carModel').val(); //汽车型号
    var manufacturerId = $('#manufacturerSelect').val(); //厂商
    var storeInfoId = $('#storeInfoSelect').val(); //仓库
    var productionStartDate = $('#productionStartDate').val(); //生产日期查询开始日期
    var productionEndDate = $('#productionEndDate').val(); //生产日期查询结束日期
    var storageStartDate = $('#storageStartDate').val(); //入库日期查询开始日期
    var storageEndDate = $('#storageEndDate').val(); //入库日期查询结束日期
    $('#car_tab').datagrid('options').url=getRootPath__()+'/car/list';
    $('#car_tab').datagrid('reload',{
        carCode:carCode,
        carName:carName,
        carModel:carModel,
        manufacturerId:manufacturerId,
        storeInfoId:storeInfoId,
        productionStartDate:productionStartDate,
        productionEndDate:productionEndDate,
        storageStartDate:storageStartDate,
        storageEndDate:storageEndDate
    });
}
/** 仓库下拉 **/
function getStoreSelect() {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/store/store_select',
        complete:function(data){
            if(data.status=="200"){
                var storeData = data.responseJSON.data;
                $('#store_id').combobox({
                    data:storeData,
                    valueField:'id',
                    textField:'storeName',
                });
                $('#manufacturerSelect').combobox({
                    data:storeData,
                    valueField:'id',
                    textField:'storeName',
                });

            }
        }
    });
}
/** 厂商下拉列表 **/
function getManufacturerSelect() {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/manufacturer/select_list',
        complete:function(data){
            if(data.status=="200"){
                var manufacturerData = data.responseJSON.data;
                $('#manufacturer_id').combobox({
                    data:manufacturerData,
                    valueField:'id',
                    textField:'manufacturerName',
                });
                $('#manufacturerSelect').combobox({
                    data:manufacturerData,
                    valueField:'id',
                    textField:'manufacturerName',
                });

            }
        }
    });
}

/** 获取汽车编号 **/
function getCarCode() {
    var Code="QC";
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/car/car_code?config='+Code,
        complete:function(data){
            if(data.status=="200"){
                var carCode = data.responseJSON.data;
                $("#car_code").val(carCode.carCode);
            }
        }
    });
}
/** 重置 **/
function reset(){
    $('#header input').val("");
    $('#header select').combobox('setValue', '');
    $('#car_tab').datagrid('load',{});
}
/** 新增初始化 */
function addCar(){
    $('#carForm').form('clear');
    getStoreSelect();
    getManufacturerSelect();
    getCarCode();
    var vs = show();
    $('#production_date').datebox('setValue',vs);
    $('#car_state').combobox('disable');
    $("#car_data").dialog("setTitle","添加汽车信息").dialog("open");
}
function show(){
    var mydate = new Date();
    var str = mydate.getFullYear() + "-";
    str += (mydate.getMonth()+1) + "-";
    str += mydate.getDate();
    return str;
}

function getDate(strDate) {
    var date = eval('new Date(' + strDate.replace(/\d+(?=-[^-]+$)/,
        function (a) { return parseInt(a, 10) - 1; }).match(/\d+/g) + ')');
    return date;
}
/**编辑初始化*/
function editCar() {
    var selectRows = $("#car_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        var storeId=selectRows[0].storeInfo.id;
        var manufacturerId =selectRows[0].manufacturer.id;
        var productionDate = selectRows[0].productionDate;
        if(!selectRows[0].state){
            state=0;//启用
        }
        $("#use_status").val(state);
        $('#carForm').form('load', selectRows[0]);//表单加载
        getStoreSelect();
        getManufacturerSelect();
        $('#store_id').combobox('select',storeId);//仓库
        $("#store_id").combobox("disable").css("background-color","#EEEEEE;");
        $("#production_date").datebox('setValue',productionDate);
        $('#manufacturer_id').combobox('select',manufacturerId);//厂商
        $('#car_data').dialog('open').dialog('setTitle', '编辑汽车信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    $('#car_state').combobox('enable');
    $("#carForm").form("submit", {
        url: getRootPath__()+'/car/saveOrUpdateCar',
        onsubmit: function () {
            return $(this).form("validate");
        },
        success: function (result) {
            var re = JSON.parse(result);
            if (re.errorCode==0) {
                msg("提交成功！");
                $("#car_tab").datagrid("load");
                $("#car_data").dialog("close");
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
function removeCar() {
    var selectRows = $("#car_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/car/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#car_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#car_tab').datagrid('load');
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