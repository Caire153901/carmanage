/**
 * Created by Administrator on 2017/10/23.
 */
/**
 * 获取生产信息报后回调方法
 */
function getData(params, callback) {
    var url = getRootPath__() + "/produce_inf_report";
    if(params.type === 1){
        url += "/month_produce_inf_report/produceInfReport";
    }else if(params.type === 2){
        url += "/year_produce_inf_report/produceInfReport";
    }else if(params.type === 3){
        url += "/allyear_produce_inf_report/produceInfReport";
    }
    $.ajax({
        url: url,
        method: "post",
        data: params,
        dataType: "json",
        success: function (re) {
            callback(re);
        }
    });
}

/**
 * 填充数据到大方格
 */
function fillBigRec(re) {
    $("#income").html(completeNum(re.totalIncome) + "&nbsp;&nbsp;元").fadeIn();
    $("#energy").html(completeNum(re.totalEnergy)+ "&nbsp;&nbsp;kW·h").fadeIn();
    $("#carbonDioxide").html(completeNum(re.reduction.carbonDioxide) + "&nbsp;&nbsp;T").fadeIn();
    $("#standardCoal").html(completeNum(re.reduction.standardCoal) + "&nbsp;&nbsp;T").fadeIn();
//                $("#rawCoal").html(re.reduction.rawCoal).fadeIn();
    $("#carbon").html(completeNum(re.reduction.carbon) + "&nbsp;&nbsp;T").fadeIn();
    $("#woods").html(completeNum(re.reduction.woods) + "&nbsp;&nbsp;m<sup>3</sup>").fadeIn();
}