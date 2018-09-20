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
import com.wmt.carmanage.service.UserInfoService;
import com.wmt.carmanage.util.FileUtils;
import com.wmt.carmanage.util.ToolFunctions;
import com.wmt.carmanage.vo.ChangePassWordVo;
import com.wmt.carmanage.vo.UserInfoVo;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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
    @Autowired
    UserInfoService userInfoService;

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
    public Map upLoadCarImg(MultipartFile file, String fileName) throws IOException {
        Map map = new HashMap();
        String filePath =FileUtils.saveUploadFile(file,fileName);
        map.put("imgUrl",filePath);
        return map;
    }

    /**
     * 获取图片
     * @return
     */
    @GetMapping("/show/img")
    public String logo(
            @RequestParam(value = "imgUrl",required = false) String imgUrl,
            @RequestParam(value = "imgName",required = false) String imgName,
            HttpServletRequest request,HttpServletResponse response)throws Exception{
        return FileUtils.IoReadImage(imgName,imgUrl,request,response);
    }

    /**
     * 修改密码
     * @param changePassWordVo
     * @return
     */
    @PostMapping("/change/pwd")
    public Map changePwd(@Validated ChangePassWordVo changePassWordVo){
        Map map = new HashMap();
        Integer userId = changePassWordVo.getUserId();
        UserInfo userInfo = userInfoService.selectById(userId);
        String oldPwd = changePassWordVo.getOldPwd();
        if(null==userInfo){
            map.put("msg","当前用户不存在！");
        }else{
            if(!oldPwd.equals(userInfo.getPassword())){
                map.put("msg","nomatch");
            }else{
                userInfo.setPassword(changePassWordVo.getNewPwd());
                userInfoService.updateById(userInfo);
                map.put("msg","success");
            }
        }
        return map;
    }

}
