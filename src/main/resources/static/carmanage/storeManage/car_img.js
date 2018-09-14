/* 启动时加载 */
$(function(){
    $("#carImg_tab").datagrid({
        checkOnSelect: true,
        pagination:true,
        pageSize:20,//默认传参 rows
        pageNumber:1,//默认传参 page
        url: getRootPath__()+'/car_img/list',
        fitColumns:true,
        singleSelect:true,
        fit:true,
        method:'get',
        pageList : [10,20,30],
        sortName : 'gmtModify',//默认传参 sort
        sortOrder : 'asc',//默认传参 order吧
        rownumbers:true,
        onSelect:function(index, row){
            selectCarImg();
        },
        onLoadSuccess:function(data){
            buttonDisble();
        },
        columns: [[
            { field: 'id', title: '汽车图片ID', hidden: 'true'},
            { field: 'carName', title: '汽车名称',sortable:true, width:150, halign: 'center',align: 'center'},
            { field: 'carModel', title: '汽车型号',  align: 'left', halign: 'center',sortable:true,width:100,align: 'center'},
            { field: 'carColor', title: '汽车颜色',  align: 'left', halign: 'center',width:100,align: 'center'},
            { field: 'carNote', title: '描述',  align: 'left', halign: 'center',width:100,align: 'center'},
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
            { field: 'carImg', title: '查看',  align: 'left', halign: 'center',width:100,align: 'center',
                formatter: function(value,row,index){
                    var image = value;
                    console.log(value)
                    var path =image.replace('\/','//');
                     console.log(path)
                    var imgName = row.carName+"_"+row.carModel+"_"+row.carColor;
                    return  "<a href='#' onclick=\"openWin('"+imgName+"','"+imgName+"')\"><span class='iconfont icon-tupian'></span></a>";
                }
            }
        ]]
    });
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
        $("#carImg").attr('src',imgPath);
        $("#win").window("open");
    }
}
//选中后按钮状态
function selectCarImg(){
    $('#carImg_edit').linkbutton('enable');//修改按钮可用
    $('#carImg_remove').linkbutton('enable');//删除按钮可用
}
//按钮禁用初始化
function buttonDisble(){
    $('#carImg_edit').linkbutton('disable');//修改按钮
    $('#carImg_remove').linkbutton('disable');//删除按钮
}
/** 查询数据条件 */
function checkInputQuery(){
    var carName = $('#car_name').val(); //汽车名称
    var carModel = $('#car_model').val(); //汽车型号
    var carColor = $('#car_color').val(); //汽车颜色
    $('#carImg_tab').datagrid('options').url=getRootPath__()+'/car_img/list';
    $('#carImg_tab').datagrid('reload',{
        carName:carName,
        carModel:carModel,
        carColor:carColor,
    });
}

/** 重置 **/
function reset(){
    $('#header input').val("");
    $('#carImg_tab').datagrid('load',{});
}
/** 新增初始化 */
function addCarImg(){
    $('#carImgForm').form('clear');
    $("#carImg_data").dialog("setTitle","添加汽车信息").dialog("open");
}

/**编辑初始化*/
function editCarImg() {
    var selectRows = $("#carImg_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0){
        var id =selectRows[0].id;//获取选中行的用户ID
        var state=0;//状态参数初始化，默认为禁用
        if(!selectRows[0].useStatus){
            state=0;//启用
        }
        $("#use_status").val(state);
        $('#carImgForm').form('load', selectRows[0]);//表单加载
        $('#carImg_data').dialog('open').dialog('setTitle', '编辑汽车信息');
    }else{
        $.messager.alert("提示", "请选择要修改的行！", 'info');
    }
}
/**
 * 提交
 * @returns
 */
function checkInputAdd(){
    var imgName="";
    var carName = $("#car_name").val();
    var carModel = $("#car_model").val();
    var carColor = $("#car_color").val();
    imgName = carName+"_"+carModel+"_"+carColor;
    //先进行上传图片操作
    $('#uploadCarPic').form('submit', {
        url:getRootPath__() + '/main/upload/img?fileName='+imgName,
        success:function(data){
            var re = JSON.parse(data);
            $('#car_img').val(re.data.imgUrl);  //将图片路径加载到电站表单的隐藏域iconcls中
            $("#carImgForm").form("submit", {
                url: getRootPath__()+'/car_img/saveOrUpdateCarImg',
                onsubmit: function () {
                    return $(this).form("validate");
                },
                success: function (result) {
                    var re = JSON.parse(result);
                    if (re.errorCode==0) {
                        msg("提交成功！");
                        $("#carImg_tab").datagrid("load");
                        $("#carImg_data").dialog("close");
                        buttonDisble();
                    }else if(re.data==="异常"){
                        $.messager.alert("提示信息", re.errorMsg, 'warning');
                    }else{
                        msg("保存数据失败！+,"+re.errorMsg);
                    }
                }
            });
        },
    });
}

/**删除*/
function removeCarImg() {
    var selectRows = $("#carImg_tab").datagrid("getSelections");
    if(selectRows.length > 1){
        $.messager.alert("提示", "只能选择一行！", 'info');
    }else if(selectRows.length >0) {
        $.messager.confirm("提示", "确定要删除吗？", function (isTrue) {
            if (isTrue) {
                var id =selectRows[0].id
                $.ajax({
                    type: 'post',
                    url: getRootPath__()+'/car_img/delete',
                    data: {id: id},
                    complete:function(data){
                        if(data.status=="200"){
                            msg("删除成功！");
                            $('#carImg_tab').datagrid('load');
                            buttonDisble();
                        }else{
                            msg("删除失败！");
                            $('#carImg_tab').datagrid('load');
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