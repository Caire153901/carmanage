package com.wmt.carmanage.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 订单信息表
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单号
     */
    @TableField("order_code")
    private String orderCode;
    /**
     * 客户ID
     */
    @TableField("customer_id")
    private Integer customerId;
    /**
     * 汽车ID
     */
    @TableField("car_id")
    private Integer carId;
    /**
     * 成交价
     */
    @TableField("closing_cost")
    private BigDecimal closingCost;
    /**
     * 销售日期
     */
    @TableField("sales_date")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date salesDate;
    /**
     * 订单状态,0未出库，1已出库，2在运，3已送达
     */
    @TableField("order_status")
    private Integer orderStatus;
    /**
     * 订单说明
     */
    @TableField("order_note")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String orderNote;
    /**
     * 数据状态，0启用，1禁用，2删除
     */
    @TableField("use_status")
    private Integer useStatus;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modify")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtModify;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public BigDecimal getClosingCost() {
        return closingCost;
    }

    public void setClosingCost(BigDecimal closingCost) {
        this.closingCost = closingCost;
    }

    public Date getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(Date salesDate) {
        this.salesDate = salesDate;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
        "id=" + id +
        ", orderCode=" + orderCode +
        ", customerId=" + customerId +
        ", carId=" + carId +
        ", closingCost=" + closingCost +
        ", salesDate=" + salesDate +
        ", orderStatus=" + orderStatus +
        ", orderNote=" + orderNote +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModify=" + gmtModify +
        "}";
    }
}
