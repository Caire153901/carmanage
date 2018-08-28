package com.wmt.carmanage.service;

import com.wmt.carmanage.entity.UserInfo;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.UserInfoVo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface UserInfoService extends IService<UserInfo> {

    List<UserInfoVo> getUserInfoTable(String userName, Integer roleId);

}
