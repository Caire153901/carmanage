package com.wmt.carmanage.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p>
 * 汽车信息
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@TableName("car_info")
public class CarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 汽车编号
     */
    @TableField("car_code")
    private String carCode;
    /**
     * 汽车名称
     */
    @TableField("car_name")
    private String carName;
    /**
     * 汽车型号
     */
    @TableField("car_model")
    private String carModel;
    /**
     * 汽车颜色
     */
    @TableField("car_color")
    private String carColor;
    /**
     * 发动机编号，不重复
     */
    @TableField("engine_number")
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
    @TableField("store_id")
    private Integer storeId;
    /**
     * 所属厂商
     */
    @TableField("manufacturer_id")
    private Integer manufacturerId;
    /**
     * 生产日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField("production_date")
    private Date productionDate;

    @TableField(exist = false)
    private String productionDates;
    /**
     * 入库日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @TableField("storage_date")
    private Date storageDate;
    /**
     * 汽车描述
     */
    @TableField("car_note")
    private String carNote;
    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 数据状态，0启用，1禁用，2删除,3已选
     */
    @TableField("use_status")
    private Integer useStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("gmt_modify")
    private Date gmtModify;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarCode() {
        return carCode;
    }

    public void setCarCode(String carCode) {
        this.carCode = carCode;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getYieldly() {
        return yieldly;
    }

    public void setYieldly(String yieldly) {
        this.yieldly = yieldly;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(Integer manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public Date getStorageDate() {
        return storageDate;
    }

    public void setStorageDate(Date storageDate) {
        this.storageDate = storageDate;
    }

    public String getCarNote() {
        return carNote;
    }

    public void setCarNote(String carNote) {
        this.carNote = carNote;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getProductionDates() {
        return productionDates;
    }

    public void setProductionDates(String productionDates) {
        this.productionDates = productionDates;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return object ->     seen.putIfAbsent(keyExtractor.apply(object), true) == null;
    }
    @Override
    public String toString() {
        return "CarInfo{" +
        "id=" + id +
        ", carCode=" + carCode +
        ", carName=" + carName +
        ", carModel=" + carModel +
        ", carColor=" + carColor +
        ", engineNumber=" + engineNumber +
        ", yieldly=" + yieldly +
        ", flow=" + flow +
        ", storeId=" + storeId +
        ", manufacturerId=" + manufacturerId +
        ", productionDate=" + productionDate +
        ", storageDate=" + storageDate +
        ", carNote=" + carNote +
        ", imgUrl=" + imgUrl +
        ", useStatus=" + useStatus +
        ", gmtCreate=" + gmtCreate +
        ", gmtModify=" + gmtModify +
        "}";
    }
}
