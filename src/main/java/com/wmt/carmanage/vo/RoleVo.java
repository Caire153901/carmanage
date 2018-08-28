package com.wmt.carmanage.vo;

import lombok.Data;

import java.util.List;

/**
 * 角色信息
 */
@Data
public class RoleVo {

    /**
     * 角色ID
     */
    private Integer id;
    /**
     * 角色名
     */
    private String roleName;
    /**
     * 角色描述
     */
    private String roleNote;
    /**
     * 使用状态,0启用，1禁用，2删除
     */
    private Integer useStatus;
    /**
     * 权限
     */
    private List<AuthorityVo> authorityVoList;
}
