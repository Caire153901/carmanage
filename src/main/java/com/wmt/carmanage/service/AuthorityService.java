package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.service.IService;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.vo.AuthorityVo;

import java.util.List;

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
     * 根据角色获取权限列表
     * @param roleId
     * @return
     */
    List<AuthorityVo> getAuthorityListByRoleId(Integer roleId);
}
