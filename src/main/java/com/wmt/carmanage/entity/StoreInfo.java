package com.wmt.carmanage.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 仓库信息
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("store_info")
public class StoreInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 仓库ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 仓库名
     */
    @TableField("store_name")
    private String storeName;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 仓库最大容量
     */
    @TableField("max_capacity")
    private Integer maxCapacity;
    /**
     * 仓库现有容量
     */
    private Integer capacity;
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
    /**
     * 0启用，1禁用，2删除
     */
    @TableField("use_status")
    private Integer useStatus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    @Override
    public String toString() {
        return "StoreInfo{" +
        "id=" + id +
        ", storeName=" + storeName +
        ", address=" + address +
        ", maxCapacity=" + maxCapacity +
        ", capacity=" + capacity +
        ", gmtCreate=" + gmtCreate +
        ", gmtModify=" + gmtModify +
        ", useStatus=" + useStatus +
        "}";
    }
}
