package com.wmt.carmanage.constant;

import com.baomidou.mybatisplus.enums.IEnum;

import java.io.Serializable;

/**
 * @author : Qiuyj
 * @date : 2018/5/24 8:45
 * @desc :
 */
public enum LargeScreenConfigEnum implements IEnum {
    /**
     * 简介信息
     */
    INTRODUCTION("introduction"),
    /**
     * 水质
     */
    WATER_QUALITY("water_quality"),
    /**
     * 设备设施
     */
    DEVICE("device"),
    /**
     * 料塔
     */
    FEED_TOWER("feed_tower"),
    /**
     * logo
     */
    LOGO("logo"),
    /**
     * 标题
     */
    TITLE("title"),
    /**
     * 经纬度
     */
    LAT_AND_LON("lat_and_lng"),
    /**
     * 视频
     */
    VIDEO("video"),
    /**
     * 显示图片
     */
    SHOW_IMG("show_img"),
    /**
     * 简介图片
     */
    INTRODUCTION_IMG("introduction_img")
    ;

    private final String value;

    LargeScreenConfigEnum(String value) {
        this.value = value;
    }

    @Override
    public Serializable getValue() {
        return value;
    }

}
