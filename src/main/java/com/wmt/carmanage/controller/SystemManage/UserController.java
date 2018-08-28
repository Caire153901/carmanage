package com.wmt.carmanage.controller.SystemManage;


import com.wmt.carmanage.service.UserInfoService;
import com.wmt.carmanage.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户管理控制器
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 用户列表页面跳转
     * @return
     */
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String userInfo() {
        return "/system/user_info";
    }

    @GetMapping("/list")
    public List<UserInfoVo> getUserInfoList(String userName,
                                            Integer roleId){
        return  userInfoService.getUserInfoTable(userName,roleId);
    }

}
