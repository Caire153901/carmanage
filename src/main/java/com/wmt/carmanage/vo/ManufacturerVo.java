package com.wmt.carmanage.vo;


import lombok.Data;

/**
 * 厂商信息
 */
@Data
public class ManufacturerVo {

    /**
     * ID
     */
    private Integer id;
    /**
     * 厂商编号
     */
    private String manufacturerCode;
    /**
     * 厂商名
     */
    private String manufacturerName;
    /**
     * 联系人
     */
    private String linkman;
    /**
     * 联系电话
     */
    private String telphone;
    /**
     * 邮编
     */
    private String postcode;
    /**
     * 地址
     */
    private String address;
    /**
     * 数据状态，0启用，1禁用，2删除
     */
    private Integer useStatus;
}
