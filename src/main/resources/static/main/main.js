$(document).on('click', '.pf-logout', function () {
    $.messager.confirm({
        title: '提示',
        msg: '确定要退出吗？',
        draggable: false,
        fn: function (isTrue) {
            if (isTrue)
                window.location = getRootPath__() + "/logout";
        }
    });
});