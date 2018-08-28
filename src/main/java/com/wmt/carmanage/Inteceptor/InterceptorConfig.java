package com.wmt.carmanage.Inteceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器基础
 */
@Slf4j
public class InterceptorConfig implements HandlerInterceptor {

    // controller 执行之前调用
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

/*        //正式的
        if(customerId.equals("deeb85b4-db32-4b6e-9e96-3f6d4a4edb95")){
      //if(customerId.equals("d705c09f-d815-4d03-ae63-6f7cb555bc96")){
            log.info("权限验证成功,customerId={}", customerId);
            return true;
        }else {
            throw new BaseException(ResponseCode.AUTHENTICATION_FAILURE);
        }*/
        return true;
    }

    // controller 执行之后，且页面渲染之前调用
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    // 页面渲染之后调用，一般用于资源清理操作
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion");
    }

}
