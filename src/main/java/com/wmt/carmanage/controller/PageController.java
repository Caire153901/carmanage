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
    @RequestMapping("/")
    public String  mains() {
        return "login";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String  login() {
        return "login";
    }
    /**
     * 退出跳到登录页面
     * @return
     */
    @RequestMapping("/logout")
    public String logout(){
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
     * 首页
     * @return
     */
    @RequestMapping("/firstPage")
    public String fistPage(){
        return "firstPage";
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
     * 角色管理
     * @return
     */
    @RequestMapping(value = "/role")
    public String roleList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/system/role_list";
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
    @RequestMapping(value = "/cars_detail")
    public String carsDetailList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/sales/cars_list";
    }
    /**
     * 汽车图片管理
     * @return
     */
    @RequestMapping(value = "/car_img")
    public String carImgList(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/store/car_img";
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
        return "/store/cars_list";
    }

    /**
     * 统计的库存统计分析
     * @return
     */
    @RequestMapping(value = "/statistics/store")
    public String storeStatistics(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/statistics/statistics_store";
    }

    /**
     * 统计的销售趋势分析
     * @return
     */
    @RequestMapping(value = "/statistics/order")
    public String carOrderStatistics(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/statistics/statistics_order";
    }

    /**
     * 统计的客户分析
     * @return
     */
    @RequestMapping(value = "/statistics/customer")
    public String customerStatistics(HttpSession session) {
        UserInfo userInfo = (UserInfo) session.getAttribute(Const.USER_KEY);
        if(null==userInfo){
            return "redirect:/login";
        }
        return "/statistics/statistics_customer";
    }






}
