package com.wmt.carmanage.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.constant.Const;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.service.UserInfoService;
import com.wmt.carmanage.util.ToolFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * 用户登录控制器
 */
@SuppressWarnings("all")
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录
     * @param account
     * @param password
     * @param model
     * @param session
     * @return
     */
    @PostMapping("/login")
    public String login(String account, String password, Model model,HttpSession session){
        if(ToolFunctions.isEmpty(account) ){
            // 账号为空
            model.addAttribute("msg", "对不起，账号不能为空");
            model.addAttribute("account", account);
            model.addAttribute("password", password);
            return "login";
        }
        if(ToolFunctions.isEmpty(password)){
            // 密码为空
            model.addAttribute("msg", "对不起，密码不能为空");
            model.addAttribute("account", account);
            model.addAttribute("password", password);
            return "login";
        }
        try {
            Wrapper<UserInfo> userInfoWrapper = new EntityWrapper<>();
            userInfoWrapper.eq("account",account);
            UserInfo userInfo = userInfoService.selectOne(userInfoWrapper);
            if(null==userInfo){
                // 账号不存在
                model.addAttribute("msg", "对不起，账号不存在");
                model.addAttribute("account", account);
                model.addAttribute("password", password);
                return "login";
            }else {
                if(!password.equals(userInfo.getPassword())){
                    // 密码不正确
                    model.addAttribute("msg", "对不起，密码不正确");
                    model.addAttribute("account", account);
                    model.addAttribute("password", password);
                    return "login";
                }
                if(userInfo.getUseStatus()==1){
                    // 密码不正确
                    model.addAttribute("msg", "对不起，用户已禁用");
                    model.addAttribute("account", account);
                    model.addAttribute("password", password);
                    return "login";
                }
            }
            session.setAttribute(Const.USER_KEY, userInfo);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("msg", "对不起，用户名或密码错误！");
            model.addAttribute("account", account);
            model.addAttribute("password", password);
            return "login";
        }

        return "redirect:/main";
    }

}
