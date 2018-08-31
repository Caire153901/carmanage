package com.wmt.carmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wmt.carmanage.entity.CarInfo;
import com.wmt.carmanage.entity.Customer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息
 */
@Data
public class OrderInfoVo {

    /**
     * ID
     */
    private Integer id;
    /**
     * 订单号
     */
    private String orderCode;
    /**
     * 客户信息
     */
    private Customer customer;
    /**
     * 汽车信息
     */
    private CarInfo carInfo;
    /**
     * 成交价
     */
    private BigDecimal closingCost;
    /**
     * 销售日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date salesDate;
    /**
     * 订单状态,0未出库，1已出库，2在运，3已送达
     */
    private Integer orderStatus;
    /**
     * 订单说明
     */
    private String orderNote;
    /**
     * 数据状态，0启用，1禁用，2删除
     */
    private Integer useStatus;
}
