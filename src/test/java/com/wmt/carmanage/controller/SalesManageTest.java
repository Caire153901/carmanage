package com.wmt.carmanage.controller;

import com.google.common.collect.Maps;
import com.wmt.carmanage.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 销售管理测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SalesManageTest extends BaseTest{

    /**
     * 订单列表
     */
    @Test
    public void getOrderList() {
        String url = "/order/list";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
}
