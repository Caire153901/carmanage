package com.wmt.carmanage.controller;

import com.google.common.collect.Maps;
import com.wmt.carmanage.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 仓库管理测试
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StoreManageTest extends BaseTest{

    /**
     * 汽车列表
     */
    @Test
    public void getCarList() {
        String url = "/car_info/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
     /**
     * 汽车类型下拉列表
     */
    @Test
    public void getCarSelectList() {
        String url = "/car_info/car_type_select";
        Map<String,String> params = Maps.newHashMap();
        params.put("storeId","1");
        doGet(url, params);
    }

    /**
     * 仓库列表
     */
    @Test
    public void getStoreList() {
        String url = "/store/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
     /**
     * 仓库下拉列表
     */
    @Test
    public void getStoreSelectList() {
        String url = "/store/store_select";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }

}
