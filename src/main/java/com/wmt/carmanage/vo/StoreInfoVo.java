package com.wmt.carmanage.vo;

import lombok.Data;

@Data
public class StoreInfoVo {

    /**
     * 仓库ID
     */
    private Integer id;
    /**
     * 仓库名
     */
    private String storeName;
    /**
     * 仓库地址
     */
    private String address;
    /**
     * 仓库最大容量
     */
    private Integer maxCapacity;
    /**
     * 仓库现有容量
     */
    private Integer capacity;

    /**
     * 0启用，1禁用，2删除
     */
    private Integer useStatus;
}
