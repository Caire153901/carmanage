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
     * @param userName
     * @param roleId
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<UserInfoVo> getUserInfoList(String account,String userName,Integer roleId, Integer current, String sort, Boolean asc, Integer pageSize)throws Exception;

    /**
     * 新增
     * @param userInfo
     * @return
     * @throws Exception
     */
    boolean saveUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 修改
     * @param userInfo
     * @return
     * @throws Exception
     */
    boolean editUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteUserInfo(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableUserInfo(Integer id,byte type) throws Exception;

}
