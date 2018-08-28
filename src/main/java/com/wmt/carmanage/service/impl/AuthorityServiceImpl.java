package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.mapper.AuthorityMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.vo.AuthorityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority> implements AuthorityService {

    @Autowired
    RoleAuthorityService roleAuthorityService;
    /**
     * 根据角色获取权限列表
     * @param roleId
     * @return
     */
    @Override
    public List<AuthorityVo> getAuthorityListByRoleId(Integer roleId) {
        List<AuthorityVo> list = new ArrayList<>();
        Wrapper<RoleAuthority> roleAuthorityWrapper = new EntityWrapper<>();
        roleAuthorityWrapper.eq("role_id",roleId);
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.selectList(roleAuthorityWrapper);
        roleAuthorityList.stream().forEach(roleAuthority -> {
            AuthorityVo authorityVo = new AuthorityVo();
            Authority authority = super.selectById(roleAuthority.getAuthorityId());
            BeanUtils.copyProperties(authority,authorityVo);
            list.add(authorityVo);
        });
        return list;
    }
}
