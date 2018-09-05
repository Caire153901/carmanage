package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Role;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.mapper.UserInfoMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.RoleService;
import com.wmt.carmanage.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wmt.carmanage.util.ToolFunctions;
import com.wmt.carmanage.vo.AuthorityVo;
import com.wmt.carmanage.vo.UserInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wumt
 * @since 2018-08-24
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    RoleService roleService;
    @Autowired
    AuthorityService authorityService;



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
    @Override
    public Page<UserInfoVo> getUserInfoList(String account, String userName, Integer roleId, Integer current, String sort, String asc, Integer pageSize) throws Exception {
        Boolean orderSort = true;
        if(null==sort){
            sort ="gmtModified";
        }
        if(null!=asc && asc.equals("desc")){
                orderSort = false;
       }
        Page page = new Page(current, pageSize, sort, orderSort);
        EntityWrapper<UserInfo> wrapper = new EntityWrapper<>();
        if(null!=account && !account.equals("")){
            wrapper.like("account",account);
        }
        if (null!=userName && !userName.equals("")){
           wrapper.like("user_name",userName);
        }
        if(null!= roleId) {
            wrapper.eq("role_id", roleId);
        }
        Page<UserInfo> userInfoPage = super.selectPage(page,wrapper);
        if(null!=userInfoPage.getRecords() && userInfoPage.getRecords().size()>0){
            List<UserInfoVo> userInfoVoList = new ArrayList<>();
            userInfoPage.getRecords().stream().forEach(userInfo -> {
                UserInfoVo vo = new UserInfoVo();
                BeanUtils.copyProperties(userInfo,vo);
                Role role = roleService.selectById(userInfo.getRoleId());
                vo.setRoleName(role.getRoleName());
                vo.setAuthorityNames(authorityService.getAuthorityNamesByRoleId(role.getId()));
                userInfoVoList.add(vo);
            });
            page = page.setRecords(userInfoVoList);
        }

        return page;
    }

    /**
     * 新增
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean saveUserInfo(UserInfo userInfo) throws Exception {
        EntityWrapper<UserInfo> wrapper = new EntityWrapper<>();
        wrapper.eq("account",userInfo.getAccount());
        List<UserInfo> list = super.selectList(wrapper);
        if(list.size()>0){
           throw new BaseException("用户名【"+userInfo.getAccount()+"】已存在");
        }else{
            return super.insert(userInfo);
        }
    }

    /**
     * 修改
     * @param userInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean editUserInfo(UserInfo userInfo) throws Exception {
        return super.updateById(userInfo);
    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteUserInfo(Integer id) throws Exception {
        UserInfo old = super.selectById(id);
        old.setUseStatus(2);
        return super.updateById(old);
    }

    /**
     * 禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @Override
    public boolean enableUserInfo(Integer id, byte type) throws Exception {
        UserInfo old = super.selectById(id);
        if(type==0){
            old.setUseStatus(0);
        }else if(type==1){
            old.setUseStatus(1);
        }
        return super.updateById(old);
    }
}
