package com.wmt.carmanage.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public class Authority implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 权限名
     */
    @TableField("authority_name")
    private String authorityName;
    /**
     * 使用状态，0启用，1禁用，2删除
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
    @TableField("gmt_modified")
    private Date gmtModified;
    /**
     * 父ID
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 排序
     */
    @TableField("authority_order")
    private Integer authorityOrder;
    /**
     * 菜单地址
     */
    @TableField("url")
    private String url;
    /**
     * 子菜单
     */
    private List<Authority> childList;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Authority> getChildList() {
        return childList;
    }

    public void setChildList(List<Authority> childList) {
        this.childList = childList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getAuthorityOrder() {
        return authorityOrder;
    }

    public void setAuthorityOrder(Integer authorityOrder) {
        this.authorityOrder = authorityOrder;
    }

    @Override
    public String toString() {
        return "Authority{" +
        "id=" + id +
        ", authorityName=" + authorityName +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        ", parentId=" + parentId +
        ", authorityOrder=" + authorityOrder +
        "}";
    }
}
