/**
 * 电站信息列表
 */
function showPlantList(){
    $("#plantInfo_tab").datagrid({
        url:getRootPath__()+'/power_manage/plant_info/getPlantList' ,
        checkOnSelect: true,
        pagination:true,
        pageSize:20,
        pageNumber:1,
        fitColumns:true,
        singleSelect:true,
        fit:true,
        rownumbers:true,
        onDblClickCell:function(i,f,v){
            var row = $("#plantInfo_tab").datagrid('getSelected');
            var titles=row.powername;
            var plantId=row.id;
            var providerType=row.providerType;
            var urls= '/power_manage/single_station/detailStation?plantId='+plantId+'&providerType='+providerType;
            top.addTab(titles,urls);
        },
        columns:[[
            {field:'id',title:'电站信息ID',hidden:true, width:100},
            {field:'userId',title:'业主ID',hidden:true, width:100},
            {field:'orgid',title:'公司ID',hidden:true, width:100},
            {field:'powername',title:'电站名称',halign:'center',sortable:true,width:100},
            {field:'sn',title:'逆变器编号',halign:'center',sortable:true,width:100},
            {field:'installedcapacity',title:'装机容量(KW)',sortable:true,align:'center',width:100},
            {field:'electric',title:'今日发电量(KW.h)',sortable:true,align:'center',width:100},
            {field:'total_electric',title:'累计发电量(KW.h)',sortable:true,align:'center',width:100},
            // {field:'currentPower',title:'当前功率(Kw)',sortable:true,align:'center',width:100},
            {field:'putproductiontime',title:'投产发电时间',sortable:true,align:'center',width:100,
                formatter: function(value,row,index){
                    if(value!=null){
                        var time=getTime(value).toString().substring(0,10);
                    }else{
                        var time="";
                    }
                    return time;
                }
            },
            {field:'iconcls',title:'电站图片',align:'center',width:100,
                formatter: function(value,row,index){
                    var imgName=row.powername;
                    if(value!=null && value!=""){
                        var image=value.substring(1,value.length - 1);
                    }else{
                        image="";
                    }
                    return  "<a href='#' onclick=\"openWin('"+imgName+"','"+image+"')\"><span class='iconfont icon-tupian'></span></a>";
                }
            }
        ]]
    });
}

/**
 * 电站信息图片查看
 * @param title
 * @param image
 */
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
        var path =image.split("\/");
        var imgName=path[path.length-1];
        var imgPath=getRootPath__()+"/fistPages/img?imgName="+imgName;
        $("#stationImg").attr('src',imgPath);
        $("#win").window("open");
    }
}

//下拉框初始化
function comboxSelect(id){
    $('#select_org').combobox({
        url:getRootPath__() +"/sys_manage/org_manage/getOrgList?pid="+id,
        valueField:'id',
        textField:'orgname',
        editable:false,
        onLoadSuccess:function (req) {
              $('#select_org').combobox('setValue','请选择子公司');
              $('#select_area').combobox('setValue','请选择区域');
        },
        onSelect:function(rec){
            $("#select_area").combobox('readonly',false);
            if(rec.orgname!='请选择子公司'){
                    var url=getRootPath__() +"/sys_manage/area_manage/getAreaList?orgId="+rec.id;
                    $('#select_area').combobox('reload',url);
            }else{
                $('#select_area').combobox('setValue','请选择区域');
            }
        }
    });
}


//条件查询
function searchPlantList(){
    var powername = $('#search_plant').val(); //公司名称
    var orgId=$('#select_org').combobox('getValue');//公司ID
    var areaId=$('#select_area').combobox('getValue');//区域ID
    var upElectric=$('#upElectric').val();//今日发电量起始值
    var downElectric=$('#downElectric').val();//今日发电量结束值
    var snCode=$('#search_sn').val();//逆变器编号
    var queryDate=$('#search_date').val();//逆变器编号
    if(!queryDate || queryDate == '') {
        queryDate = new Date().formate("yyyy-MM-dd");
    }
    if(upElectric!=""){
        isNum(upElectric,'今日发电量起始值');
    }
    if(downElectric!=""){
        isNum(downElectric,'今日发电量结束值');
    }
    if(upElectric!="" && downElectric!=""){
        if(parseFloat(downElectric)<parseFloat(upElectric)){
            msg('今日发电量结束值不能小于起始值');
            return;
        }
    }
    if(orgId==='请选择子公司'){
        orgId=null;
    }
    if(areaId=='请选择区域'){
        areaId=null;
    }
    $('#plantInfo_tab').datagrid('options').url=getRootPath__()+'/power_manage/plant_info/getPlantList';
    $('#plantInfo_tab').datagrid('reload',{
        sn:snCode,
        powername:powername,
        orgId:orgId,
        areaId:areaId,
        upElectric:upElectric,
        downElectric:downElectric,
        el_Date:queryDate
    });
}

/**
 * 数字验证
 * @param t
 * @param text
 */
function isNum(t,text) {
    s =  $.trim(t);
    var p2=/^[0-9]+\.?[0-9]*$/;
    //var p =/^[1-9](\d+(\.\d{1,2})?)?$/;
    // var p1=/^[0-9](\.\d{1,2})?$/;
    if(!p2.test(s)){
        msg(text+'必须为数字！');
        return;
    }
}
//重置查询
function reset(id){
    $("#search_date").val(new Date().formate("yyyy-MM-dd"));
    $('#upElectric').val("");
    $('#downElectric').val("");
    $('#search_plant').val(""); //公司名称
    $('#search_sn').val("");//逆变器编号
    comboxSelect(id);
    $("#select_area").combobox('clear');
    $("#select_area").combobox('readonly',true);
    $("#plantInfo_tab").datagrid("load", {});
}
function showOutWin() {
    $('#outWin').window({
        width:250,
        height:200,
        title:"导出日期选择",
        collapsible:false,
        minimizable:false,
        maximizable:false,
        closed:true,
        modal:true
    });
    $('#plantExcelOut_datetimepicker').datebox({
        onSelect: function(date){
            var dates=date;
        }
    });
    $("#outWin").window("open");
}
function downLoadPlantInfoExcel() {
    var date=$("#search_date").val();
    var snCode=$('#search_sn').val();//逆变器编号
    var powername = $('#search_plant').val(); //公司名称
    var orgId=$('#select_org').combobox('getValue');//公司ID
    var areaId=$('#select_area').combobox('getValue');//区域ID
    var upElectric=$('#upElectric').val();//今日发电量起始值
    var downElectric=$('#downElectric').val();//今日发电量结束值
    if (orgId == '请选择子公司'){
        orgId = '';
    }
    if (areaId == '请选择区域'){
        areaId = '';
    }
    var url="powername="+powername+"&orgId="+orgId+"&areaId="+areaId+"&sn="+snCode+"&el_Date="+date;

    if(upElectric!=""){
        isNum(upElectric,'今日发电量起始值');
    }
    if(downElectric!=""){
        isNum(downElectric,'今日发电量结束值');
    }
    if(upElectric!="" && downElectric!=""){
        if(parseFloat(downElectric)<parseFloat(upElectric)){
            msg('今日发电量结束值不能小于起始值');
            return;
        }
    }
    $("#outWin").window("close");
    url=url+"&upElectric="+upElectric+"&downElectric="+downElectric;
    window.location.href = getRootPath__() + "/power_manage/plant_info/downLoadPlantInfoExcel?"+url;
}
