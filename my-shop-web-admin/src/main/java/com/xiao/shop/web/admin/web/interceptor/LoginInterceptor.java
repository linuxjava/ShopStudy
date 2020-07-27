package com.xiao.shop.web.admin.web.interceptor;

import com.xiao.shop.commons.constant.ConstantUtils;
import com.xiao.shop.commons.constant.UrlConstant;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //不包含userInfo信息说明未登录，那么重定向到登录页面
        if (httpServletRequest.getSession().getAttribute(ConstantUtils.SESSION_USER) == null) {
            httpServletResponse.sendRedirect(UrlConstant.JSP_LOGIN);
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
