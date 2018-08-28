package com.wmt.carmanage.exception;


import com.wmt.carmanage.constant.ResponseCode;
import lombok.Data;

/**
 * 异常处理基础模块
 */
@Data
public class BaseException extends RuntimeException {

    private int errorCode;
    private String errorMsg;

    public BaseException(String errorMsg, int errorCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public BaseException(ResponseCode responseCode) {
        super(responseCode.getErrorMsg());
        this.errorMsg = responseCode.getErrorMsg();
        this.errorCode = responseCode.getErrorCode();
    }

    public BaseException(ResponseCode responseCode, String errMsg) {
        super(responseCode.getErrorMsg());
        this.errorMsg = responseCode.getErrorMsg();
        this.errorMsg += ",\n" + errMsg;
        this.errorCode = responseCode.getErrorCode();
    }

    public BaseException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = -1;
    }

    public BaseException(Throwable cause, int errorCode) {
        super(cause);
        this.errorMsg = cause.getMessage();
        this.errorCode = errorCode;
    }
}
