package com.wmt.carmanage.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.util.FileUtils;
import com.wmt.carmanage.util.ToolFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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
    @Autowired
    CarInfoService carInfoService;

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

    /**
     * 上传图片
     */
    @PostMapping("/upload/img")
    public String upLoadCarImg(MultipartFile file, String fileName) throws IOException {
        return FileUtils.saveUploadFile(file,fileName);
    }

    /**
     * logo图片
     * @return
     */
    @GetMapping("/show/img")
    public String logo(@RequestParam String imgUrl)throws Exception{
        return FileUtils.IoReadImage(imgUrl);
    }
}
