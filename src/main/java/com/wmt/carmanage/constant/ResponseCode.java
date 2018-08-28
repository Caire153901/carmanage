package com.wmt.carmanage.constant;

/**
 *  错误码及错误描述
 */
public enum ResponseCode {

    NEED_LOGIN(5001, "用户未登录，需要登录"),
    ILLEGAL_ARGUMENT(5002, "非法参数"),
    HAVE_NO_PERMISSION(5003, "您无权访问"),
    INTERNAL_EXCEPTION(5004, "内部异常，请联系管理员"),
    NEED_UPLOAD_FILE(5005, "未上传图片"),

    ;

    private final int errorCode;
    private final String errorMsg;

    ResponseCode(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
