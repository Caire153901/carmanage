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
    var ids = [];
    // 删除行
    $.each(rows, function (i, v) {

        ids.push(v.id);
    });
    $.ajax({
        method : "post",
        url: getRootPath__() + '/sys_manage/resource_manage/removeResource',
        data: {
            ids: ids
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
    $('#fid').form('load', getRootPath__() + '/sys_manage/resource_manage/getById?id=' + id);
    var code = $iconSpan.find('input').val();
    $iconSpan.attr('class', '').addClass('iconfont').addClass('icon-' + code);
    $("#selectId").combotree("setValue", node.parentId);
    $('#dlg').dialog('open').dialog('setTitle', '编辑资源');
}

