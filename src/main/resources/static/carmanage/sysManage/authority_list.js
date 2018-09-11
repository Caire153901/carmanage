
    $(function () {
        // 初始化下拉树
        $("#selectId").combotree({
            url:'/authority/list',
            required:true,
            method:'get',
            loadFilter:function(data){
                data = recursiveModify(data.data,'authorityName','text');
                data.push({
                    id : '0',
                    text:'0',
                });
                return data;
            },
            onChange: function (e) {
                $("#parentId").val(e);
                $("#parentName").val($("#selectId").combotree("getText"));
            }
        });

//        初始化弹出层
        $("#dlg").dialog({
            title: 'My Dialog',
            width: 450,
            height: 340,
            closed: true,
            cache: false,
            modal: true,
            buttons: [{ //设置下方按钮数组
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#fid').form('submit', {
                        success: function () {
                            $("#dlg").dialog('close');
                            window.location.reload();
                        }
                    }); // 提交表单

                }
            }, {
                text: '取消',
                handler: function () {
                    $("#dlg").dialog('close');
                }
            }]
        });
        // 居中
        $('#dlg').dialog('center');

        // iframe绑定点击事件，点击icon后隐藏iframe，赋值给隐藏域
        var $iframe = $("#iconsFrame");
        $iframe.on("load", function () {
            $iframe.contents().find('li').on('click', function (e) {
                var code = $(this).find(".fontclass").html().replace("\.", '');
                $iframe.hide();
                var $iconSpan = $('#iconSpan');
                $iconSpan.find('input').val(code);
                $iconSpan.attr('class', '').addClass('iconfont').addClass('icon-' + code);
            });
        });

        // 初始化树形表格
        $('#tt').treegrid({
            url: getRootPath__() + '/authority/list',
            idField: 'id',
            fit : true,
            treeField: 'authorityName',
            iconCls: 'icon-ok',
            fitColumns : true,
            animate: true,
            collapsible: true,
            checkbox: true,
            method:'get',
            columns: [[
                {title: 'id', field: 'id', width: 180,  hidden: 'true'},
                {title: '资源名称', field: 'authorityName', width: 180 },
                {title: '资源路径', field: 'url', width: 180 },
                {title: '父节点', field: 'parentName', width: 180 },
                {title: '图标', field: 'icon', width: 180},
            ]],
            loadFilter: function (data) {
                return recursiveModify(data.data,"icon","icon",function (oldProperty) {
                    return "<span class='iconfont icon-" + oldProperty + "'></span>";
                });
            }
        });
    });
/**
 * 删除资源
 */
function removeResource() {
    // 获取选中的行
    var rows = $('#tt').treegrid('getCheckedNodes');
    if (!rows || rows.length === 0){
        alert("没有check节点");
        return false;
    }
    // 删除行
    var id ="";
    $.each(rows, function (i, v) {
         id =v.id;
    });
    $.ajax({
        method : "post",
        url: getRootPath__() + '/authority/delete',
        data: {
            id: id
        },
        dataType : "text",
        success: function () {
            window.location.reload();
        }
    });
}

/**
 * 新建资源
 */
function createResource() {
    var $iconSpan = $('#iconSpan');
    $iconSpan.attr('class', '').addClass('iconfont');
    $('#fid').form('reset');
    $('#dlg').dialog('open').dialog('setTitle', '新建资源');
}
/**
 * 编辑资源
 */
function editResource() {
    var node = $('#tt').treegrid('getSelected');
    if (!node) {
        alert("没有选中节点");
        return false;
    }
    var id = node.id;
    var $iconSpan = $('#iconSpan');
    $iconSpan.find('input').val('');
    $.ajax({
        method : "get",
        url: getRootPath__() + '/authority/detail?id=' + id,
        dataType : "text",
        success: function (data) {
            var re = JSON.parse(data);
            $('#fid').form('load', re.data);
            $iconSpan.attr('class', '').addClass('iconfont').addClass('iconfont icon-' + re.data.icon);
        }
    });
    $("#selectId").combotree("setValue", node.parentId);
    $('#dlg').dialog('open').dialog('setTitle', '编辑资源');
}

