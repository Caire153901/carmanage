package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
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
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    RoleAuthorityService roleAuthorityService;

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
    public Page<RoleVo> getRoleList(String roleName, Integer current, String sort, String asc, Integer pageSize) throws Exception {
        boolean orderSort = false;
        if (null == sort) {
            sort = "gmtModified";
        }
        if (null != asc && asc.equals("asc")) {
            orderSort = true;
        }
        Page page = new Page(current, pageSize, sort, orderSort);
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
             //   List<AuthorityVo> authorityVos = authorityService.getAuthorityListByRoleId(role.getId());
                String authName = authorityService.getAuthorityNamesByRoleId(role.getId());
              //  vo.setAuthorityVoList(authorityVos);
                vo.setAuthorityNames(authName);
                roleVoList.add(vo);
            });
            page = page.setRecords(roleVoList);  //查出的list调用setRecords
        }
        return page;
    }

    /**
     * 新增
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveRole(Role role) throws Exception {
        EntityWrapper<Role> wrapper = new EntityWrapper<>();
        wrapper.eq("role_name",role.getRoleName());
        List<Role> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("角色名【"+role.getRoleName()+"】已存在");
        }else {
            return super.insert(role);
        }
    }

    /**
     * 修改
     * @param role
     * @return
     * @throws Exception
     */
    @Override
    public boolean editRole(Role role) throws Exception {
        Role old = super.selectById(role.getId());
        if(!old.getRoleName().equals(role.getRoleName())){
            EntityWrapper<Role> wrapper = new EntityWrapper<>();
            wrapper.eq("role_name",role.getRoleName());
            List<Role> list = super.selectList(wrapper);
            if(list.size()>0){
                throw new BaseException("角色名【"+role.getRoleName()+"】已存在");
            }else {
                return super.updateById(role);
            }
        }
        return false;
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteRole(Integer id) throws Exception {
        Role role = super.selectById(id);
        role.setUseStatus(2);
        return super.updateById(role);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableRole(Integer id, byte type) throws Exception {
        Role role = super.selectById(id);
        if(type==0){
            role.setUseStatus(0);
        }else if(type==1){
            role.setUseStatus(1);
        }
        return super.updateById(role);
    }

    /**
     * 保存角色与权限的关系
     * @param id
     * @param resourceIds
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveRoleResource(Integer id, Integer[] resourceIds) throws Exception {
        Wrapper<RoleAuthority> wrapper = new EntityWrapper<>();
        wrapper.eq("role_id",id);
        roleAuthorityService.delete(wrapper);
        for(Integer resourceId:resourceIds){
            RoleAuthority roleAuthority = new RoleAuthority();
            roleAuthority.setRoleId(id);
            roleAuthority.setAuthorityId(resourceId);
            roleAuthorityService.insert(roleAuthority);
        }
        return true;
    }
}
