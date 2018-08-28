package com.wmt.carmanage.controller;

import com.wmt.carmanage.constant.Const;
import com.wmt.carmanage.entity.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 页面控制器
 */
@Controller
public class PageController {
    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String  login() {
        return "login";
    }

    /**
     * 主页面
     * @return
     */
    @RequestMapping("/main")
    public String main(Model model, HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        model.addAttribute("userInfo",userInfo);
        return "main";
    }


}
