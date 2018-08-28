package com.wmt.carmanage.exception;

import com.wmt.carmanage.constant.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 异常处理
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements ErrorController {

    /**
     * 内部异常处理
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        log.error("", ex);
        StringBuilder errorMsg = new StringBuilder();
        int errorCode = status.value();

        // 如果是参数绑定异常
        if (ex instanceof BindException) {
            BindException bindException = (BindException) ex;
            BindingResult bindingResult = bindException.getBindingResult();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError error : fieldErrors) {
                String code = error.getCode();
                if (code.contains("typeMismatch")) {
                    // 类型不匹配异常
                    errorMsg.append("参数[" + error.getField() + "=" + error.getRejectedValue() + "],类型不正确，请检查").append(",");
                } else {
                    String defaultMessage = error.getDefaultMessage();
                    errorMsg.append(defaultMessage).append(",");
                }
            }
            errorMsg.deleteCharAt(errorMsg.length() - 1);
            errorCode = ResponseCode.ILLEGAL_ARGUMENT.getErrorCode();
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exception = (MethodArgumentTypeMismatchException) ex;
            errorCode = ResponseCode.ILLEGAL_ARGUMENT.getErrorCode();
            errorMsg.append("参数[").append(exception.getName()).append("=")
                    .append(exception.getValue()).append("],类型不正确，请检查");
        }
        if (errorMsg.length() > 0) {
            return new ResponseEntity<>(new BaseException(errorMsg.toString(), errorCode), status);
        }
        return new ResponseEntity<>(new BaseException(ex, errorCode), status);
    }

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(HttpServletRequest req, Exception e) throws Exception {
        return e;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping(value = "/error")
    public Object error(HttpServletResponse resp, HttpServletRequest req) {
        HttpStatus httpStatus = HttpStatus.valueOf(resp.getStatus());
        // 错误处理逻辑
        return new BaseException(httpStatus.getReasonPhrase(), httpStatus.value());
    }

}
