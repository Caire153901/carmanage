package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.AuthorityMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.util.ToolFunctions;
import com.wmt.carmanage.vo.AuthorityVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * 权限列表
     * @param authorityName
     * @param parentId 为0 时查询主表
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public Page<AuthorityVo> getAuthorityList(String authorityName, Integer parentId,Integer current, String sort, Boolean asc, Integer pageSize) throws Exception {
        if(null==sort){
            sort = "authorityOrder";
        }
        if (null==asc){
            asc = true;
        }
        Page page = new Page(current, pageSize, sort, asc);
        EntityWrapper<Authority> wrapper = new EntityWrapper<>();
        if(null!= authorityName && !authorityName.equals("")){
            wrapper.like("authority_name",authorityName);
        }
        wrapper.eq("parent_id",parentId);
        Page<Authority> authorityPage = super.selectPage(page,wrapper);
        if(null!=authorityPage.getRecords() && authorityPage.getRecords().size()>0){
            List<AuthorityVo> authorityVoList = new ArrayList<>();
            authorityPage.getRecords().stream().forEach(authority -> {
                AuthorityVo vo = new AuthorityVo();
                BeanUtils.copyProperties(authority,vo);
                authorityVoList.add(vo);
            });
            page = page.setRecords(authorityVoList);  //查出的list调用setRecords
        }
        return page;
    }

    /**
     * 新增
     * @param authority
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveAuthority(Authority authority) throws Exception {
        EntityWrapper<Authority> wrapper = new EntityWrapper<>();
        wrapper.eq("authority_name",authority.getAuthorityName());
        List<Authority> list = super.selectList(wrapper);
        if(list.size()>0){
            throw new BaseException("权限名【"+authority.getAuthorityName()+"】已存在");
        }else {
            return super.insert(authority);
        }
    }

    /**
     * 修改
     * @param authority
     * @return
     * @throws Exception
     */
    @Override
    public boolean editAuthority(Authority authority) throws Exception {
       Authority old = super.selectById(authority.getId());
       if(!old.getAuthorityName().equals(authority.getAuthorityName())){
           EntityWrapper<Authority> wrapper = new EntityWrapper<>();
           wrapper.eq("authority_name",authority.getAuthorityName());
           List<Authority> list = super.selectList(wrapper);
           if(list.size()>0){
               throw new BaseException("权限名【"+authority.getAuthorityName()+"】已存在");
           }else {
               return super.insert(authority);
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
    public boolean deleteAuthority(Integer id) throws Exception {
        Authority old = super.selectById(id);
        old.setUseStatus(2);
        return super.updateById(old);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableAuthority(Integer id, byte type) throws Exception {
        Authority old = super.selectById(id);
        if(type==1){
            old.setUseStatus(1);
        }else if(type==0){
            old.setUseStatus(0);
        }
        return super.updateById(old);
    }

    /**
     * 根据角色ID获取菜单
     * @param roleId
     * @return
     */
    @Override
    public  List<AuthorityVo> getAuthorityListByRoleId(Integer roleId){
        // 最后的结果
        List<AuthorityVo> list = new ArrayList<>();
        EntityWrapper<RoleAuthority> roleAuthorityWrapper = new EntityWrapper<>();
        roleAuthorityWrapper.eq("role_id", roleId);
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.selectList(roleAuthorityWrapper);
        // 原始的数据
        List<AuthorityVo> authorityVoList = new ArrayList<>();
        roleAuthorityList.stream().forEach(roleAuthority -> {
            AuthorityVo authorityVo = new AuthorityVo();
            Authority authority = super.selectById(roleAuthority.getAuthorityId());
            BeanUtils.copyProperties(authority, authorityVo);
            authorityVoList.add(authorityVo);
        });
        //父级菜单
        List<AuthorityVo> parentList = authorityVoList.stream().filter(authorityVo -> authorityVo.getParentId() == 0).collect(Collectors.toList());
        parentList.stream().forEach(menu -> {
            menu.setChildList(getChild(menu.getId(), authorityVoList));
            list.add(menu);
        });
        return list;
    }

    /**
     * 递归查找子菜单
     *
     * @param id
     *            当前菜单id
     * @param rootMenu
     *            要查找的列表
     * @return
     */
    public static List<AuthorityVo> getChild(Integer id, List<AuthorityVo> rootMenu) {
        // 子菜单
        List<AuthorityVo> childList = new ArrayList<>();
        for (AuthorityVo menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (menu.getParentId()==0) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (AuthorityVo menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isBlank(menu.getUrl())) {
                // 递归
                menu.setChildList(getChild(menu.getId(),rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }
    /**
     * 获取拼接的权限名
     * @param roleId
     * @return
     */
    @Override
    public String getAuthorityNamesByRoleId(Integer roleId){
        List<String> stringList = new ArrayList<>();
        List<AuthorityVo> list = getAuthorityListByRoleId(roleId);
        list.stream().forEach(l->{
            stringList.add(l.getAuthorityName());
        });
        return String.join("-",stringList);
    }
}
