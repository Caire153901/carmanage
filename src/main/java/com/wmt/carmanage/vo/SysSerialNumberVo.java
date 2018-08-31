package com.wmt.carmanage.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 流水号
 */
@Data
public class SysSerialNumberVo {

    /**
     * 权限ID
     */
    private Integer authorityId;
    /**
     * 菜单名
     */
    private String authorityName;
    /**
     * 序列号模板
     */
    private String configTemplet;
    /**
     * 当前序列号
     */
    private Integer currutSerial;
    /**
     * 最大位数
     */
    private Integer maxSerial;
    /**
     * 数据状态，0启用，1禁用，2删除
     */
    private Integer useStatus;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date gmtModified;


}
