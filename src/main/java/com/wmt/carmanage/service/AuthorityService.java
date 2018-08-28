package com.wmt.carmanage.service;

import com.baomidou.mybatisplus.plugins.Page;
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
     * 权限列表父表
     * @param authorityName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    Page<AuthorityVo> getAuthorityList(String authorityName, Integer parentId,Integer current, String sort, Boolean asc, Integer pageSize) throws Exception;

}
