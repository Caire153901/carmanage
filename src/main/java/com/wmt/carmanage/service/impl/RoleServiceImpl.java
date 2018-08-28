package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.Role;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.RoleMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.service.RoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.util.ToolFunctions;
import com.wmt.carmanage.vo.AuthorityVo;
import com.wmt.carmanage.vo.RoleVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    AuthorityService authorityService;

    /**
     * 获取角色的下拉列表
     * @return
     */
    @Override
    public List<RoleVo> getRoleSelect() {
        List<RoleVo> list = new ArrayList<>();
        Wrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.eq("use_status",0);
        List<Role> roleList = super.selectList(wrapper);
        roleList.stream().forEach(role -> {
            RoleVo vo = new RoleVo();
            BeanUtils.copyProperties(role,vo);
            list.add(vo);
        });
        return list;
    }

    /**
     * 角色列表
     * @param roleName
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<RoleVo> getRoleList(String roleName, Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if (null == sort) {
            sort = "gmtModified";
        }
        if (null == asc) {
            asc = false;
        }
        Page page = new Page(current, pageSize, sort, asc);
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        if (null != roleName && !roleName.equals("")) {
            wrapper.like("role_name", roleName);
        }
        Page<Role> rolePage = super.selectPage(page, wrapper);
        // 3.判断并组装返回结果
        if (null != rolePage.getRecords() || rolePage.getRecords().size() > 0) {

            List<RoleVo> roleVoList = Lists.newArrayList();
            rolePage.getRecords().stream().forEach(role -> {
                RoleVo vo = new RoleVo();
                BeanUtils.copyProperties(role, vo);
                List<AuthorityVo> authorityVos = ToolFunctions.getAuthorityListByRoleId(role.getId());
                vo.setAuthorityVoList(authorityVos);
                roleVoList.add(vo);
            });
            page = page.setRecords(roleVoList);  //查出的list调用setRecords
        }
        return page;
    }

}
