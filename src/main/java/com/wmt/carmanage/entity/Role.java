package com.wmt.carmanage.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名
     */
    @TableField("role_name")
    private String roleName;
    /**
     * 角色描述
     */
    @TableField("role_note")
    private String roleNote;
    /**
     * 使用状态,0启用，1禁用，2删除
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleNote() {
        return roleNote;
    }

    public void setRoleNote(String roleNote) {
        this.roleNote = roleNote;
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
        return "Role{" +
        "id=" + id +
        ", roleName=" + roleName +
        ", roleNote=" + roleNote +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModified=" + gmtModified +
        "}";
    }
}
