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
