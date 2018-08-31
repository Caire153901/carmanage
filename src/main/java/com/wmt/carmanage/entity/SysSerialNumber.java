package com.wmt.carmanage.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 流水号表
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("sys_serial_number")
public class SysSerialNumber implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限ID
     */
    @TableField("authority_id")
    private Integer authorityId;
    /**
     * 序列号模板
     */
    @TableField("config_templet")
    private String configTemplet;
    /**
     * 当前序列号
     */
    @TableField("currut_serial")
    private Integer currutSerial;
    /**
     * 最大位数
     */
    @TableField("max_serial")
    private Integer maxSerial;
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
    @TableField("gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtModified;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getConfigTemplet() {
        return configTemplet;
    }

    public void setConfigTemplet(String configTemplet) {
        this.configTemplet = configTemplet;
    }

    public Integer getCurrutSerial() {
        return currutSerial;
    }

    public void setCurrutSerial(Integer currutSerial) {
        this.currutSerial = currutSerial;
    }

    public Integer getMaxSerial() {
        return maxSerial;
    }

    public void setMaxSerial(Integer maxSerial) {
        this.maxSerial = maxSerial;
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

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public String toString() {
        return "SysSerialNumber{" +
        "id=" + id +
        ", authorityId=" + authorityId +
        ", configTemplet=" + configTemplet +
        ", currutSerial=" + currutSerial +
        ", maxSerial=" + maxSerial +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
