
function getRootPath__() {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName !== 'distribute-station') {
        webName = '';
    }
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}


/**
 * 拓展treegrid
 * 树形表格
 */
$.extend($.fn.treegrid.methods, {
    /**
     * 添加取消全部checknode
     */
    uncheckAll: function (jq) {
        var nodes = jq.treegrid("getCheckedNodes");
        $.each(nodes, function (i, v) {
            jq.treegrid("uncheckNode", v.id);
        });
    }
});

/**
 * 添加选项卡
 * @param title
 * @param url
 */
function addTab(title, url) {
    var $tabs = $('#tabs1'),
        existTab = $tabs.tabs('getTab', title);
    if (existTab) {
        $tabs.tabs('select', title);
    } else {
        $tabs.tabs('add', {
            title: title,
            closable: true,
            content: '<div class="iframe-container"><iframe id="iframe" class="page-iframe" src="' + getRootPath__() + url + '" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe></div>',
        });
    }
}

/**
 * 递归修改或者添加自身和children属性中的属性，
 * @param data 数据
 * @param oldPro 原属性的名称
 * @param newPro 新属性的名称
 * @param callback 把原属性的的传入该方法去返回值作为新属性
 */
function recursiveModify(data, oldPro, newPro, callback) {
    $.each(data, function (i, v) {
        var oldProperty = v[oldPro],
            newProperty = oldProperty;
        if (callback)
            newProperty = callback(oldProperty);// 有回则交由回调函数处理
        v[newPro] = newProperty;
        if (v.children)
            recursiveModify(v.children,oldPro, newPro, callback);// 如果有子集则递归修改子集
    });
    return data;
}
/**
 * 时间格式化，前端input的class为easyui-date
 * @param arguments 时间数据
 * @returns
 */
function getTime(arguments) {
	var tss = arguments.time || 0;
	var ts=tss.toString().substring(0,10);
    var t, y, m, d, h, i, s;
    t = ts ? new Date(ts * 1000) : new Date();
    y = t.getFullYear();
    m = t.getMonth() + 1;
    d = t.getDate();
    h = t.getHours();
    i = t.getMinutes();
    s = t.getSeconds();
    // 可根据需要在这里定义时间格式  
    return y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d) + ' ' + (h < 10 ? '0' + h : h) + ':' + (i < 10 ? '0' + i : i) + ':' + (s < 10 ? '0' + s : s);
}
/**
 * 前台对比日期大小
 * @param checkStartDate 开始时间
 * @param checkEndDate 结束时间
 * @returns 开始时间>结束时间为false,否则为true
 */
function compareDate(checkStartDate, checkEndDate) {      
	var arys1= new Array();      
    var arys2= new Array();      
	if(checkStartDate != null && checkEndDate != null) {      
	    arys1=checkStartDate.split('-');      
	      var sdate=new Date(arys1[0],parseInt(arys1[1]-1),arys1[2]);      
	    arys2=checkEndDate.split('-');      
	    var edate=new Date(arys2[0],parseInt(arys2[1]-1),arys2[2]);      
		if(sdate > edate) {      
		    return false;         
		}  else {   
		    return true;      
		}   
   }      
}

Date.prototype.formate = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function msg(msg) {
    $.messager.show({
        title:'提示',
        msg:msg,
        showType:'show'
    });
}
function completeNum(x) {
    if(x>10000){
        return (x/10000).toFixed(3)+"万";
    }else{
        return x.toFixed(3);
    }
}