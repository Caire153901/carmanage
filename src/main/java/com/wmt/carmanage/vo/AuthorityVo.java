package com.wmt.carmanage.vo;

import lombok.Data;

import java.util.List;

/**
 * 权限信息
 */
@Data
public class AuthorityVo {

    /**
     * 权限ID
     */
    private Integer id;
    /**
     * 权限名
     */
    private String authorityName;
    /**
     * 使用状态，0启用，1禁用，2删除
     */
    private Integer useStatus;
    /**
     * 父ID
     */
    private Integer parentId;
    /**
     * 父名称
     */
    private String parentName;
    /**
     * 排序
     */
    private Integer authorityOrder;
    /**
     * 菜单地址
     */
    private String url;
    /**
     * 子菜单
     */
    private List<AuthorityVo> childList;
    /**
     * 图标
     */
    private String icon;
}
