package com.wmt.carmanage.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 角色-权限中间表
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("role_authority")
public class RoleAuthority implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableField("role_id")
    private Integer roleId;
    /**
     * 权限ID
     */
    @TableField("authority_id")
    private Integer authorityId;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    @Override
    public String toString() {
        return "RoleAuthority{" +
        "roleId=" + roleId +
        ", authorityId=" + authorityId +
        "}";
    }
}
