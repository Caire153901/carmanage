package com.wmt.carmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wmt.carmanage.entity.Manufacturer;
import com.wmt.carmanage.entity.StoreInfo;
import lombok.Data;

import java.util.Date;

/**
 * 汽车信息
 */
@Data
public class CarInfoVo {
    /**
     * ID
     */
    private Integer id;
    /**
     * 汽车编号
     */
    private String carCode;
    /**
     * 汽车名称
     */
    private String carName;
    /**
     * 汽车型号
     */
    private String carModel;
    /**
     * 汽车颜色
     */
    private String carColor;
    /**
     * 发动机编号，不重复
     */
    private String engineNumber;
    /**
     * 生产地
     */
    private String yieldly;
    /**
     * 车辆流向
     */
    private String flow;
    /**
     * 所属仓库
     */
    private StoreInfo storeInfo;
    /**
     * 所属厂商
     */
    private Manufacturer manufacturer;
    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date productionDate;
    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date storageDate;
    /**
     * 汽车描述
     */
    private String carNote;
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 数据状态，0启用，1禁用，2删除，3已选
     */
    private Integer useStatus;
}
