package com.wmt.carmanage.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.RoleAuthority;
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
            sort = "authority_order";
        }
        if (null==asc){
            asc = true;
        }
        Page page = new Page(current, pageSize, sort, asc);
        EntityWrapper<Authority> wrapper = new EntityWrapper<>();
        if(null!= authorityName && !authorityName.equals("")){
            wrapper.like("",authorityName);
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
}
