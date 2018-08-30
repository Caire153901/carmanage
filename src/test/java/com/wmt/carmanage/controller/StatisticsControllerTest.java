package com.wmt.carmanage.controller;

import com.google.common.collect.Maps;
import com.wmt.carmanage.BaseTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsControllerTest extends BaseTest {


    /**
     * 仓库饼图
     */
    @Test
    public void getStatisticsStorePie() {
        String url = "/statistics/store/pie";
        Map<String,String> params = Maps.newHashMap();
        params.put("storeId", "1");
        doGet(url, params);
    }

    /**
     * 客户饼图
     */
    @Test
    public void getStatisticsCustomerPie() {
        String url = "/statistics/customer/pie";
        Map<String,String> params = Maps.newHashMap();
        doGet(url, params);
    }
    /**
     * 销售线图
     */
    @Test
    public void getStatisticsOrderLine() {
        String url = "/statistics/order/line";
        Map<String,String> params = Maps.newHashMap();
        params.put("year", "2018");
        doGet(url, params);
    }

}
