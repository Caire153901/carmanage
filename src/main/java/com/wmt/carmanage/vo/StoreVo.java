package com.wmt.carmanage.vo;

import lombok.Data;

/**
 * 库存信息
 */
@Data
public class StoreVo {
    /**
     * 汽车ID
     */
    private Integer carId;
    /**
     * 汽车名称
     */
    private String carName;
    /**
     * 汽车型号
     */
    private String carModel;
    /**
     * 数量
     */
    private Integer carCount;

}
