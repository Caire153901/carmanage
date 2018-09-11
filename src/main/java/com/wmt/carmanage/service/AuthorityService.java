package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.vo.AuthorityVo;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
public interface AuthorityService extends IService<Authority> {

    /**
     * 权限列表父表
     * @param authorityName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<AuthorityVo> getAuthorityList(String authorityName, Integer parentId,Integer current, String sort, String asc, Integer pageSize) throws Exception;

    /**
     * 获取权限
     * @throws Exception
     */
    Set<AuthorityVo> getAuthorityList() throws Exception;

    /**
     * 根据角色ID获取权限
     * @param roleId
     * @return
     * @throws Exception
     */
    List<AuthorityVo> getAuthorityByRoleId(Integer roleId) throws Exception;

    /**
     * 新增
     * @param authority
     * @return
     * @throws Exception
     */
    boolean saveAuthority(Authority authority) throws Exception;

    /**
     * 修改
     * @param authority
     * @return
     * @throws Exception
     */
    boolean editAuthority(Authority authority) throws Exception;

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    boolean deleteAuthority(Integer id) throws Exception;

    /**
     * 禁用/启用
     * @param id
     * @return
     * @throws Exception
     */
    boolean enableAuthority(Integer id,byte type) throws Exception;

    /**
     * 根据角色ID获取权限
     * @param roleId
     * @return
     */
    List<AuthorityVo> getAuthorityListByRoleId(Integer roleId);

    /**
     * 根据角色ID获取权限拼接
     * @param roleId
     * @return
     */
    String getAuthorityNamesByRoleId(Integer roleId);

}
