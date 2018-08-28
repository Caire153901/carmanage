package com.wmt.carmanage.vo;

import lombok.Data;

/**
 * 用户信息
 */
@Data
public class UserInfoVo {

    /**
     * 用户ID
     */
    private Integer id;
    /**
     * 用户名
     */
    private String account;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色ID
     */
    private Integer roleId;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 使用状态,0为启用，1为禁用,2为删除
     */
    private Integer useStatus;
    /**
     * 用户真实姓名
     */
    private String userName;
    /**
     * 权限名
     */
    private String authorityNames;
}
