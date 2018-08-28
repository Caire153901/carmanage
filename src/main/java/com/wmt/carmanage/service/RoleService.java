package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Role;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.vo.RoleVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface RoleService extends IService<Role> {
    /**
     * 获取角色的下拉列表
     * @return
     */
    List<RoleVo> getRoleSelect();

    /**
     * 获取角色列表
     * @param roleName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<RoleVo> getRoleList(String roleName,Integer current,String sort,Boolean asc,Integer pageSize)throws Exception;

}
