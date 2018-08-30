package com.wmt.carmanage.controller;

import com.wmt.carmanage.BaseTest;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 系统管理测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemManageTest extends BaseTest {

    /**
     * 用户列表
     */
    @Test
    public void getUserPageList() {
        String url = "/user/list";
        Map<String,String> params = Maps.newHashMap();
        // params.put("account", "");
        // params.put("userName", "");
        // params.put("userInfoId", "");
        doGet(url, params);
    }
    /**
     * 角色列表
     */
    @Test
    public void getRolePageList() {
        String url = "/role/list";
        Map<String,String> params = Maps.newHashMap();
        // params.put("roleName", "");
        doGet(url, params);
    }
    /**
     * 角色下拉列表
     */
    @Test
    public void getRoleSelectList() {
        String url = "/role/select_list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }

    /**
     * 权限列表
     */
    @Test
    public void getAuthorityList() {
        String url = "/authority/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }

    /**
     * 权限子列表
     */
    @Test
    public void getAuthorityChildList() {
        String url = "/authority/child_list";
        Map<String,String> params = Maps.newHashMap();
        params.put("parentId","1");
        doGet(url, params);
    }


    /**
     * 生成流水号列表
     */
    @Test
    public void getSerialNumberList() {
        String url = "/serial_number/list";
        Map<String,String> params = Maps.newHashMap();
       // params.put("authorityName", "");
        doGet(url, params);
    }


}
