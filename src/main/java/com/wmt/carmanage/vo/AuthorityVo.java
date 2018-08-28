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
     * 排序
     */
    private Integer authorityOrder;
    /**
     *
     */
    private List<AuthorityVo> childList;
}
