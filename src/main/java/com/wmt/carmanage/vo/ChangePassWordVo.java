package com.wmt.carmanage.vo;

import lombok.Data;

@Data
public class ChangePassWordVo {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 旧密码
     */
    private String oldPwd;
    /**
     * 新密码
     */
    private String newPwd;
}
