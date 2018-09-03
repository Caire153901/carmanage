package com.wmt.carmanage.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.util.ToolFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * main主页，菜单权限获取
 */
@RequestMapping("/main")
@RestController
public class MainController extends BaseController {

    @Autowired
    RoleAuthorityService roleAuthorityService;
    @Autowired
    AuthorityService authorityService;

     /**
     * 获取当前用户可用菜单
     * 返回一级菜单
     */
    @GetMapping("/queryMenuData")
    public Set<Authority> getMenuMainData(){
        Set<Authority> list;
        UserInfo userInfo = getCurrentUser();
        Wrapper<RoleAuthority> roleAuthorityWrapper = new EntityWrapper<>();
        roleAuthorityWrapper.eq("role_id",userInfo.getRoleId());
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.selectList(roleAuthorityWrapper);
        List<Authority> authorities = new ArrayList<>();
        roleAuthorityList.stream().forEach(roleAuthority -> {
            Integer auid = roleAuthority.getAuthorityId();
            Authority auth = authorityService.selectById(auid);
            authorities.add(auth);
        });
        list = ToolFunctions.getMenuTree(authorities);
        return list;
    }





}
