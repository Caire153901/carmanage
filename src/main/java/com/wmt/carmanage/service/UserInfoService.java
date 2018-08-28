package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
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

    /**
     * 用户列表
     * @param account
     * @param roled
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<UserInfoVo> getUserInfoList(String account,String userName,Integer roleId, Integer current, String sort, Boolean asc, Integer pageSize)throws Exception;

}
