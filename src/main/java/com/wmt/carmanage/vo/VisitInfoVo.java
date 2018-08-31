package com.wmt.carmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wmt.carmanage.entity.Customer;
import com.wmt.carmanage.entity.OrderInfo;
import lombok.Data;

import java.util.Date;

/**
 * 回访信息
 */
@Data
public class VisitInfoVo {

    /**
     * ID
     */
    private Integer id;
    /**
     * 客户信息
     */
    private Customer customer;
    /**
     * 订单信息
     */
    private OrderInfo orderInfo;
    /**
     * 回访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date visitDate;
    /**
     * 回访事件
     */
    private String visitEvents;
    /**
     * 回访记录
     */
    private String visitRecord;
    /**
     * 数据状态,0启用，1禁用，2删除
     */
    private Integer useStatus;
}
