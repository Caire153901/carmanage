package com.wmt.carmanage.util;

import lombok.Data;

import java.util.List;

/**
 * Description: 统一结果返回对象
 */
@Data
public class JsonData {

    /**
     * 返回结果
     */
    private Object data;

    private List<?> rows;
    private long total;

    /**
     * 状态码
     */
    private Integer errorCode;

    /**
     * 状态信息
     */
    private String errorMsg = "";
}
