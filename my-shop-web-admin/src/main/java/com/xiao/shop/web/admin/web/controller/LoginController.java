package com.xiao.shop.web.admin.web.controller;

import com.xiao.shop.commons.constant.ConstantUtils;
import com.xiao.shop.commons.constant.UrlConstant;
import com.xiao.shop.commons.utils.CookieUtils;
import com.xiao.shop.domain.User;
import com.xiao.shop.web.admin.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;

    @RequestMapping(value = {UrlConstant.URL_LOGIN}, method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        String userInfo = CookieUtils.getCookieValue(request, ConstantUtils.COOKIE_NAME_USER_INFO);
        //判断cookie是否为空，不为空就将cookie中保存的邮箱和密码传给前端通过el表达式回填进表单
        if(StringUtils.isNotBlank(userInfo)){
            String[] userInfoArray = userInfo.split(":");
            String email = userInfoArray[0];
            String password = userInfoArray[1];
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.setAttribute("isRemember", true);
        }
        return UrlConstant.JSP_LOGIN;
    }

    /**
     * 实现方式一
     * 问题：
     * @param httpServletRequest
     * @param email
     * @param password
     * @param model
     * @return
     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(HttpServletRequest httpServletRequest, String email, String password, ModelMap model) {
//        User user = userService.login(email, password);
//
//        if (user == null) {
//            model.addAttribute("message", "用户名或密码错误");
//            return "login";////此方式跳转，页面刷新会重复提交表单
//            //return "redirect:/login";//使用重定向的方式无法传递message数据
//        }
//
//        httpServletRequest.getSession().setAttribute(ConstantUtils.SESSION_USER, user);
//
//        return "redirect:/main";
//    }

    /**
     * 实现方式二，参考解决方案:https://www.cnblogs.com/kanyun/p/8092933.html
     *
     * @param request
     * @param email
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = UrlConstant.URL_LOGIN, method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, String email, String password, String isRemember, RedirectAttributes model) {
        logger.info("isRemember:{}", isRemember);
        boolean remmenber = isRemember != null;
        User user = userService.login(email, password);

        if(!remmenber){
            //如果设置为不记住，则清除cookie
            CookieUtils.deleteCookie(request, response, ConstantUtils.COOKIE_NAME_USER_INFO);
        }

        if (user == null) {//登陆失败
            //这个方法原理是放到session中，session在跳到页面后马上移除对象。所以你刷新一下后这个值就会丢失
            model.addFlashAttribute("message", "用户名或密码错误");
            return "redirect:" + UrlConstant.URL_LOGIN;
        }else {
            //登录成功
            if(remmenber) {
                //设置cookie，用于记住密码功能
                CookieUtils.setCookie(request, response, ConstantUtils.COOKIE_NAME_USER_INFO, String.format("%s:%s", email, password), 7 * 24 * 60 * 60);
            }
            //设置session用于自动登录
            request.getSession().setAttribute(ConstantUtils.SESSION_USER, user);

            return "redirect:" + UrlConstant.URL_SHOP_MAIN;
        }
    }
}
