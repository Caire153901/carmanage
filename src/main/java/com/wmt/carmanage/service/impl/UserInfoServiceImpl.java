package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.Role;
import com.wmt.carmanage.entity.UserInfo;
import com.wmt.carmanage.mapper.UserInfoMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.RoleService;
import com.wmt.carmanage.service.UserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
     * @param userName
     * @param roleId
     * @return
     */
    @Override
    public List<UserInfoVo> getUserInfoTable(String userName, Integer roleId) {
        List<UserInfoVo> list = new ArrayList<>();
        Wrapper<UserInfo> userInfoWrapper = new EntityWrapper<>();
        if(null!=userName && !userName.equals("")){
            userInfoWrapper.like("account",userName);
        }
        List<UserInfo> userInfoList = super.selectList(userInfoWrapper);
        List<UserInfo> collect = new ArrayList<>();
        if (null!=roleId){
            collect=userInfoList.stream()
                    .filter(userInfo -> userInfo.getRoleId()
                            .equals(roleId)).collect(Collectors.toList());
        }else {
            collect = userInfoList;
        }
        collect.stream().forEach(userInfo -> {
            UserInfoVo vo = new UserInfoVo();
            Integer role_id = userInfo.getRoleId();
            Role role = roleService.selectById(role_id);
            BeanUtils.copyProperties(userInfo,vo);
            vo.setRoleName(role.getRoleName());
            String authList ="";
            authList=authorityService.getAuthorityListByRoleId(role_id)
                    .stream().map(AuthorityVo::getAuthorityName).collect(Collectors.joining("-"));
            vo.setAuthorityNames(authList);
            list.add(vo);
        });
        return list;
    }
}
