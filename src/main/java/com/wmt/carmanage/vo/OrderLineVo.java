package com.wmt.carmanage.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderLineVo {


    /**
     * 客户信息
     */
    private String customerName;
    /**
     * 汽车信息
     */
    private String carInfoName;
    /**
     * 成交价
     */
    private BigDecimal closingCost;
    /**
     * 销售日期
     */
    private Date salesDate;

}
