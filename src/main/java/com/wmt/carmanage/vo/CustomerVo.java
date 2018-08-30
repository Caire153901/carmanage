package com.wmt.carmanage.vo;

import lombok.Data;

@Data
public class CustomerVo {

    /**
     * ID
     */
    private Integer id;
    /**
     * 客户编号
     */
    private String customerCode;
    /**
     * 客户名
     */
    private String customerName;
    /**
     * 性别，0男，1女，2其他
     */
    private Integer sex;
    /**
     * 联系电话
     */
    private String telphone;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 地址
     */
    private String address;
    /**
     * 省
     */
    private String provincial;
    /**
     * 数据状态,0启用，1禁用，2删除
     */
    private Integer useStatus;

}
