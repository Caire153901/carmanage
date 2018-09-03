//菜单js
var mainPlatform = {
    init: function () {
        //初始化顶部菜单
        var url = getRootPath__() + '/main/queryMenuData';

        $.ajax({
            url: url,
            method: 'get',
            dataType: 'json',
            success: function (re) {
                var $container = $('#top-level-container');
                $.each(re.data, function (i, v) {
                    var $li = $('<li class="pf-nav-item home"></li>');
                    var $a = $('<a href="javascript:;"></a>');
                    var $span1 = $('<span class="iconfont icon-' + v.icon + '"></span>');
                    var $span = $('<span class="pf-nav-title"></span>').append(v.authorityName);
                    $li.data("data", v);
                    $container.append($li.append($a.append($span1).append($span)));

                });
                ///-----------------------顶部翻页
                var page = 0,
                    pages = ($('#top-level-container').height() / 70) - 1;
                if (pages === 0) {
                    $('.pf-nav-prev,.pf-nav-next').hide();
                }
                $(document).on('click', '.pf-nav-prev,.pf-nav-next', function () {
                    if ($(this).hasClass('disabled')) return;
                    if ($(this).hasClass('pf-nav-next')) {
                        page++;
                        $('.pf-nav').stop().animate({'margin-top': -70 * page}, 200);
                        if (page == pages) {
                            $(this).addClass('disabled');
                            $('.pf-nav-prev').removeClass('disabled');
                        } else {
                            $('.pf-nav-prev').removeClass('disabled');
                        }
                    } else {
                        page--;
                        $('.pf-nav').stop().animate({'margin-top': -70 * page}, 200);
                        if (page == 0) {
                            $(this).addClass('disabled');
                            $('.pf-nav-next').removeClass('disabled');
                        } else {
                            $('.pf-nav-next').removeClass('disabled');
                        }
                    }
                });
            $('#pf-hd .pf-nav li').first().click();
            }
        });

        this.bindEvent();
    },
    bindEvent: function () {
        // 顶部大菜单单击事件
        $(document).on('click', '.pf-nav-item', function () {
            // 切换选中状态
            if ($(this).hasClass('current'))
                return false;
            $('.pf-nav-item').removeClass('current');
            $(this).addClass('current');
            // 判断是否有子菜单
            var data = $(this).data('data');
            if (null==data.childList) {
                // 没有子菜单
                console.log("没有子菜单");
                var $container = $("#pf-sider").html('');
            } else {
                // 加载左侧菜单
                // 清空左侧菜单
                var $container = $("#pf-sider").html('');
                $container.append('<h2 class="pf-model-name"><span class="iconfont icon-' + data.icon + '"></span><span class="pf-name">' + data.authorityName + '</span> <span class="toggle-icon"></span></h2>')
                var $ul = $('<ul class="sider-nav"></ul>').html('');
                $.each(data.childList, function (i, v) {
                    // 判断是否有子菜单
                    if (null==v.childList) {
                        // 没有子菜单
                        var $a = $('<a href="javascript:;"></a>').on('click', function () {
                            addTab(v.authorityName, v.url);
                        });
                        $ul.append($('<li></li>').append($a.append('<span class="iconfont icon-' + v.icon + ' sider-nav-icon"></span><span class="sider-nav-title">' + v.authorityName + '')));
                    } else {
                        // 有子菜单
                        var $li = $('<li></li>');
                        var $a = $('<a href="javascript:;"><span class="iconfont icon-' + v.icon + ' sider-nav-icon"></span><span class="sider-nav-title">' + v.authorityName + '</span><i class="iconfont">&#xe642;</i></a>');
                        var $ul1 = $('<ul class="sider-nav-s" ></ul>');
                        $.each(v.childList, function (i1, v1) {
                            $ul1.append($('<li></li>').append(
                                $("<a href='javascript:;'>" + v1.authorityName + "</a>").on('click', function () {
                                    addTab(v1.authorityName, v1.url);
                                })
                            ));
                        });
                        $ul.append($li.append($a).append($ul1));
                    }
                });
                $container.append($ul);
            }
        });

        $(document).on('click', '.sider-nav li', function () {
            $('.sider-nav li').removeClass('current');
            $(this).addClass('current');
        });

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

        //左侧菜单收起
        $(document).on('click', '.toggle-icon', function () {
            $(this).closest("#pf-bd").toggleClass("toggle");
            setTimeout(function () {
                $(window).resize();
            }, 300)
        });

        //修改密码
        $(document).on('click', '.pf-modify-pwd', function () {
            $("#cg_password").dialog("setTitle", "修改密码").dialog("open").dialog("center");
        });

        $(document).on('click', '.pf-notice-item', function () {
            $('#pf-page').find('iframe').eq(0).attr('src', 'backend/notice.html')
        });
    },
};

function changePassword() {
    var userId = $('#userId').val();
    var oldpass = $('#useroldpwd').val();
    var password = $('#userpwd').val();
    var repassword = $('#userrepwd').val();
    if (oldpass == "" || password == "" || repassword == "") {
        $.messager.alert("提示信息", "所有内容不得为空！", 'warning');
    } else if (oldpass == password) {
        $.messager.alert("提示信息", "新密码不能和旧密码相同！", 'warning');
    } else {
        $("#passwordForm").form("submit", {
            url: getRootPath__() + '/sys_manage/user_manage/changepassword',
            onsubmit: function () {
                return $(this).form("validate");
            },
            success: function (result) {
                if (result == "success") {
                    $("#cg_password").dialog("close");
                    $.messager.alert("提示信息", "密码修改成功！请重新登录！", 'info');
                    setTimeout(function () {
                        window.location.href = getRootPath__() + "/logout";
                    }, 1500);
                } else if (result == "nomatch") {
                    $.messager.alert("提示信息", "旧密码与原密码不同！", 'warning');
                } else {
                    $.messager.alert("提示信息", "修改密码失败！", 'warning');
                }
            }
        });

    }

};
mainPlatform.init();

