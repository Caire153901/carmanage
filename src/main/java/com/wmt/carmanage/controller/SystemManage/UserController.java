package com.wmt.carmanage.controller.SystemManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.service.UserInfoService;
import com.wmt.carmanage.vo.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
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
     * 用户列表
     * @param account
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public Page<UserInfoVo> getSerialNumberList(
            @RequestParam(value = "account",required = false) String account,
            @RequestParam(value = "userName",required = false) String userName,
            @RequestParam(value = "roleId",required = false) Integer roleId,
            @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
            @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
            @RequestParam(value = "asc",required = false) Boolean asc,
            @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return userInfoService.getUserInfoList(account, userName, roleId, current, sort, asc, pageSize);
    }

    /**
     * 添加
     * @param userInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveUserInfo(@Validated UserInfo userInfo) throws Exception{
        return userInfoService.saveUserInfo(userInfo);
    }

    /**
     * 修改
     * @param userInfo
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateUserInfo(@Validated UserInfo userInfo) throws Exception{
        return userInfoService.editUserInfo(userInfo);
    }


    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteUserInfo(@RequestParam Integer id) throws Exception{
        return userInfoService.deleteUserInfo(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableUserInfo(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return userInfoService.enableUserInfo(id,type);
    }
}
