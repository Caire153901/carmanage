package com.wmt.carmanage.controller;

import com.google.common.collect.Maps;
import com.wmt.carmanage.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 客户管理测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerManageTest extends BaseTest {


    /**
     * 客户列表
     */
    @Test
    public void getCustomerList() {
        String url = "/customer/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
    /**
     * 厂商列表
     */
    @Test
    public void getManufacturerList() {
        String url = "/manufacturer/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
    /**
     * 回访信息列表
     */
    @Test
    public void getVisitList() {
        String url = "/visit/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }

}
