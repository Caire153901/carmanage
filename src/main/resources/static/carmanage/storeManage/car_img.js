/*提交新增or编辑电站*/
function submitPCTable(){
    //先进行上传电站图片操作
    $('#uploadPlantPic').form('submit', {
        url:getRootPath__() + '/power_manage/plant_manage/upload',
        success:function(data){
            if(data&&data!=='"'+'"'){
                $('#iconcls').val(data);  //将图片路径加载到电站表单的隐藏域iconcls中
            }
            //提交新增/编辑表单
            $("#plantConfigForm").form("submit", {
                url: getRootPath__()+'/power_manage/plant_manage/saveOrUpdatePlantConfig' ,
                success: function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '提示消息',
                            msg: '提交成功',
                            showType: 'show',
                            timeout: 1000,
                            style: {
                                right: '',
                                bottom: ''
                            }
                        });
                        $("#plantConfig_data").dialog("close");
                        $("#plantConfig_tab").datagrid("load");
                    }
                }
            });
        },
    });
}