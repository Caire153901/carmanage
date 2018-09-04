package com.wmt.carmanage.util;

import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.constant.ResponseCode;
import com.wmt.carmanage.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@ControllerAdvice
public class JsonDataConverterResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        JsonData jsonData = new JsonData();
        if (o instanceof Exception) {
            Exception exception = (Exception) o;
            log.info("异常", exception);
            jsonData.setData("异常");
            String errorMsg = ResponseCode.INTERNAL_EXCEPTION.getErrorMsg();
            int errorCode = ResponseCode.INTERNAL_EXCEPTION.getErrorCode();

            if (exception instanceof BaseException) {
                BaseException baseException = (BaseException) o;
                errorMsg = baseException.getErrorMsg();
                errorCode = baseException.getErrorCode();
            } else if (exception instanceof ConstraintViolationException) {
                errorMsg = "";
                // 方法参数校验异常
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
                Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
                for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                    String message = constraintViolation.getMessage();
                    errorCode = 1;// 校验异常
                    errorMsg += message + ",";
                }
            }
            jsonData.setErrorCode(errorCode);
            jsonData.setErrorMsg(errorMsg);
        } else {
            if(o instanceof EUDataGridResult){
                EUDataGridResult eud =(EUDataGridResult)o;
                jsonData.setRows(eud.getRows());
                jsonData.setTotal(eud.getTotal());
            }else{
                jsonData.setData(o);
            }
            jsonData.setErrorCode(0);
            jsonData.setErrorMsg("success");
        }

        if (MediaType.TEXT_HTML.equals(mediaType)) {
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        }
        return jsonData;
    }
}
