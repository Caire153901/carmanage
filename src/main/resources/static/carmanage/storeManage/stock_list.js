
/* 启动时加载 */
$(function(){
    getStoreSelect();
});

/** 仓库下拉 **/
function getStoreSelect() {
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
                    onSelect: function(rec){
                        getStoreInfoById(rec.id);
                        getCarStoreInfo(rec.id);
                        getCarNameSelect(rec.id);
                    },
                    onLoadSuccess:function(){ //默认选中第一条数据
                        var data= $(this).combobox("getData");
                        if (data.length > 0) {
                            $('#storeSelect').combobox('select', data[0].id);
                        }
                    }
                });
            }
        }
    });
}


/** 仓库信息 **/
function getStoreInfoById(id) {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/store/detail?id='+id,
        complete:function(data){
            if(data.status=="200"){
                var storeData =data.responseJSON.data;
                $("#store_name").val(storeData.storeName); //仓库名
                $("#max_capacity").val(storeData.maxCapacity); //仓库最大容量
                $("#capacity").val(storeData.capacity); //现有库存
                $("#margin_capacity").val(storeData.marginCapacity); //仓库余量
                $("#store_address").val(storeData.address); //仓库地址
            }
        }
    });
}
/** 汽车名下拉 **/
function getCarNameSelect(storeId) {
    $.ajax({
        type: 'get',
        async:false,
        url: getRootPath__()+'/car/car_type_select?storeId='+storeId,
        complete:function(data){
            if(data.status=="200"){
                var carData =data.responseJSON.data;
                $('#carSelect').combobox({
                    data:carData,
                    valueField:'carName',
                    textField:'carName'
                });
            }
        }
    });
}
/** 库存列表**/
function getCarStoreInfo(storeId) {
    $("#car_store_table").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/car/store/list?storeId='+storeId,
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'a.car_name',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        columns: [[
            { field: 'carId', title: '汽车ID', hidden: 'true'},
            { field: 'carName', title: '汽车名',sortable:true, width:100, halign: 'center',align: 'center'},
            { field: 'carModel', title: '汽车型号',sortable:true, width:150, halign: 'center',align: 'center'},
            { field: 'carCount', title: '数量',sortable:true,width:100,halign: 'center',align: 'center'},
        ]]
    });
}

/** 查询数据条件 */
function checkInputQuery(){
    var storeId = $('#storeSelect').val(); //
    var carName = $('#carSelect').val();
    var carModel = $('#carModel').val();
    $("#car_store_table").url=getRootPath__()+'/car/store/list';
    $('#car_store_table').datagrid('reload',{
        storeId:storeId,
        carName:carName,
        carModel:carModel
    });
}
/** 重置 **/
function reset(){
    $('#carModel').val("");
    $('#carSelect').combobox('setValue','');
    checkInputQuery();
}