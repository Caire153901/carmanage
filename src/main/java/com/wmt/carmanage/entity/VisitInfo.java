package com.wmt.carmanage.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 回访信息
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("visit_info")
public class VisitInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Integer id;
    /**
     * 客户ID
     */
    @TableField("customer_id")
    private Integer customerId;
    /**
     * 订单ID
     */
    @TableField("order_id")
    private Integer orderId;
    /**
     * 回访时间
     */
    @TableField("visit_date")
    private Date visitDate;
    /**
     * 回访事件
     */
    @TableField("visit_events")
    private String visitEvents;
    /**
     * 回访记录
     */
    @TableField("visit_record")
    private String visitRecord;
    /**
     * 数据状态,0启用，1禁用，2删除
     */
    @TableField("use_status")
    private Integer useStatus;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modify")
    private Date gmtModify;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitEvents() {
        return visitEvents;
    }

    public void setVisitEvents(String visitEvents) {
        this.visitEvents = visitEvents;
    }

    public String getVisitRecord() {
        return visitRecord;
    }

    public void setVisitRecord(String visitRecord) {
        this.visitRecord = visitRecord;
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
        return "VisitInfo{" +
        "id=" + id +
        ", customerId=" + customerId +
        ", orderId=" + orderId +
        ", visitDate=" + visitDate +
        ", visitEvents=" + visitEvents +
        ", visitRecord=" + visitRecord +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModify=" + gmtModify +
        "}";
    }
}
