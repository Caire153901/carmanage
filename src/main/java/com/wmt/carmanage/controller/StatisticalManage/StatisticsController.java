package com.wmt.carmanage.controller.StatisticalManage;

import com.wmt.carmanage.entity.OrderInfo;
import com.wmt.carmanage.service.CarInfoService;
import com.wmt.carmanage.service.CustomerService;
import com.wmt.carmanage.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 统计报表管理
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    CarInfoService carInfoService;
    @Autowired
    CustomerService customerService;
    @Autowired
    OrderInfoService orderInfoService;
    /**
     * 统计的库存统计分析
     * @return
     */
    @GetMapping ("/store/pie")
    public Map storeStatistics(Integer storeId) {
        return carInfoService.getStorePieByStoreId(storeId);
    }

    /**
     * 统计的客户统计分析
     * @return
     */
    @GetMapping ("/customer/pie")
    public Map customerStatistics() {
        return customerService.getCustomerPie();
    }
    /**
     * 统计的订单统计分析
     * @return
     */
    @GetMapping ("/order/line")
    public Map orderStatistics(Integer year) {
        return orderInfoService.getOrderLineByYear(year);
    }


}
