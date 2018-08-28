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
        if(null==userInfo){
            return "redirect:/login";
        }
        model.addAttribute("userInfo",userInfo);
        return "main";
    }
    /**
     *  流水号页面跳转
     * @return
     */
    @RequestMapping(value = "/serial_number")
    public String serial_number(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/system/serial_number";
    }

    /**
     * 用户列表页面跳转
     * @return
     */
    @RequestMapping(value = "/user")
    public String userList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/system/user_list";
    }

    /**
     * 权限管理
     * @return
     */
    @RequestMapping(value = "/authority")
    public String authorityList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/system/authority_list";
    }
    /**
     * 客户管理
     * @return
     */
    @RequestMapping(value = "/customer")
    public String customerList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/customer/customer_list";
    }

    /**
     * 厂商管理
     * @return
     */
    @RequestMapping(value = "/manufacturer")
    public String manufacturerList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/customer/manufacturer_list";
    }

    /**
     * 回访信息管理
     * @return
     */
    @RequestMapping(value = "/visit")
    public String callbackList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/customer/visit_list";
    }

    /**
     * 订单列表管理
     * @return
     */
    @RequestMapping(value = "/order")
    public String orderList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/sales/order_list";
    }
    /**
     * 销售列表管理
     * @return
     */
    @RequestMapping(value = "/sales")
    public String salesList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/sales/sales_list";
    }
    /**
     * 汽车列表管理
     * @return
     */
    @RequestMapping(value = "/cars")
    public String carsList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/sales/cars_list";
    }
    /**
     * 仓库列表管理
     * @return
     */
    @RequestMapping(value = "/store")
    public String storeList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/store/store_list";
    }
    /**
     * 库存管理
     * @return
     */
    @RequestMapping(value = "/stock")
    public String stockList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/store/stock_list";
    }






}
