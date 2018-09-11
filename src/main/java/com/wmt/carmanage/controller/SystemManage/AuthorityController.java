package com.wmt.carmanage.controller.SystemManage;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.exception.BaseException;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.vo.AuthorityVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 权限管理控制层
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

    /**
     * 菜单的下拉列表
     * @return
     */
    @GetMapping("/authority_select")
    public List<AuthorityVo> getAuthoritySelect(Integer parentId){
        List<AuthorityVo> voList = new ArrayList<>();
        Wrapper<Authority> wrapper = new EntityWrapper<>();
        if(null!=parentId){
            wrapper.eq("parent_id",parentId);
        }
        List<Authority> list = authorityService.selectList(wrapper);
        list.stream().forEach(authority -> {
            AuthorityVo vo = new AuthorityVo();
            BeanUtils.copyProperties(authority,vo);
            voList.add(vo);
        });
        return voList;
    }

    /**
     * 权限列表父表
     * @throws Exception
     */
    @GetMapping("/list")
    public Set<AuthorityVo> getAuthorityList() throws Exception{
        return authorityService.getAuthorityList();
    }

    /**
     * 根据角色ID获取用户权限ID
     * @param roleId
     * @return
     * @throws Exception
     */
    @GetMapping("/list/by_roleId")
    public List<AuthorityVo> getAuthorityListByRoleId(@RequestParam(value = "roleId",required = true) Integer roleId) throws Exception{
       return authorityService.getAuthorityByRoleId(roleId);
    }
    /**
     * 权限表
     * @return
     */
    @GetMapping("/detail")
    public AuthorityVo getAuthorityById( @RequestParam(value = "id",required = false) Integer id)throws Exception{
        Authority authority = authorityService.selectById(id);
        AuthorityVo vo = new AuthorityVo();
        BeanUtils.copyProperties(authority,vo);
        if(authority.getParentId()!=0){
            Authority parent = authorityService.selectById(authority.getParentId());
            vo.setParentName(parent.getAuthorityName());
        }else{
            vo.setParentName("root");
        }
        return vo;
    }
    /**
     * 权限列表子表
     * @param parentId
     * @param current
     * @param sort
     * @param asc
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping("/child_list")
    public EUDataGridResult getAuthorityListByParentId(
                        @RequestParam(value = "authorityName",required = false) String authorityName,
                        @RequestParam(value = "parentId",required = false) Integer parentId,
                        @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                        @RequestParam(value = "sort",required = false,defaultValue = "authorityOrder") String sort,
                        @RequestParam(value = "order",required = false) String asc,
                        @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<AuthorityVo> page = authorityService.getAuthorityList(authorityName,parentId, current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加
     * @param authority
     * @return
     * @throws Exception
     */
    @PostMapping("/saveOrUpdateAuthority")
    public boolean saveOrUpdateAuthority(@Validated Authority authority) throws Exception{
        Integer id = authority.getId();
        Integer parentId = authority.getParentId();
        if(null!=id && id==parentId){
            throw new BaseException("不能选择自己作为父节点");
        }
        if(null == id){
            return authorityService.saveAuthority(authority);
        }else {
            return authorityService.editAuthority(authority);
        }
    }
    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteAuthority(@RequestParam Integer id) throws Exception{
        return authorityService.deleteAuthority(id);
    }
}
