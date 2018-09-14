package com.wmt.carmanage.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 *   汽车图片
 * </p>
 *
 * @author wumt
 * @since 2018-09-14
 */
@TableName("car_img")
public class CarImg implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
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
     * 汽车描述
     */
    @TableField("car_note")
    private String carNote;
    /**
     * 图片地址
     */
    @TableField("car_img")
    private String carImg;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modify")
    private Date gmtModify;

    /**
     * 数据状态，0启用，1禁用，2删除,3已选
     */
    @TableField("use_status")
    private Integer useStatus;


    public Integer getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(Integer useStatus) {
        this.useStatus = useStatus;
    }

    public String getCarNote() {
        return carNote;
    }

    public void setCarNote(String carNote) {
        this.carNote = carNote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCarImg() {
        return carImg;
    }

    public void setCarImg(String carImg) {
        this.carImg = carImg;
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

    @Override
    public String toString() {
        return "CarImg{" +
                "id=" + id +
                ", carName='" + carName + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carColor='" + carColor + '\'' +
                ", carNote='" + carNote + '\'' +
                ", carImg='" + carImg + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", useStatus=" + useStatus +
                '}';
    }
}
