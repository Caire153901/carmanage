package com.wmt.carmanage.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderExcelOut implements Serializable{

    /**
     * Id
     */
    @Excel(name = "序号", width = 10)
    private Integer rowNumber;
    /**
     * 订单号
     */
    @Excel(name = "订单号", width = 30)
    private String orderCode;
    /**
     * 客户名
     */
    @Excel(name = "客户名", width = 30)
    private String customerName;
    /**
     * 客户号
     */
    @Excel(name = "客户号", width = 30)
    private String customerCode;
    /**
     * 性别
     */
    @Excel(name = "性别", width = 30)
    private String sex;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话", width = 30)
    private String telPhone;
    /**
     * 汽车号
     */
    @Excel(name = "汽车号", width = 30)
    private String carCode;
    /**
     * 汽车名
     */
    @Excel(name = "汽车名", width = 30)
    private String carName;
    /**
     * 汽车型号
     */
    @Excel(name = "汽车型号", width = 30)
    private String carModel;
    /**
     * 汽车颜色
     */
    @Excel(name = "汽车颜色", width = 30)
    private String carColor;
    /**
     * 发动机编号
     */
    @Excel(name = "发动机编号", width = 30)
    private String engineNumber;
    /**
     * 生产日期
     */
    @Excel(name = "生产日期", format = "yyyy-MM-dd",width = 20)
    private Date productionDate;
    /**
     * 入库日期
     */
    @Excel(name = "入库日期", format = "yyyy-MM-dd",width = 20)
    private Date storageDate;

    /**
     * 成交价
     */
    @Excel(name = "成交价", width = 30)
    private BigDecimal closingCost;
    /**
     * 销售日期
     */
    @Excel(name = "销售日期", format = "yyyy-MM-dd",width = 20)
    private Date salesDate;
    /**
     * 订单状态,0未出库，1已出库，2在运，3已送达
     */
    @Excel(name = "订单状态", width = 30)
    private String orderStatus;
    /**
     * 订单说明
     */
    @Excel(name = "订单说明", width = 100)
    private String orderNote;

}