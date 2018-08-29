package com.wmt.carmanage.controller.SystemManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.vo.AuthorityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;

/**
 * 权限管理控制层
 */
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    AuthorityService authorityService;

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
    @GetMapping("/list")
    public Page<AuthorityVo> getAuthorityList(
                                         @RequestParam(value = "authorityName",required = false) String authorityName,
                                         @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                                         @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                         @RequestParam(value = "asc",required = false) Boolean asc,
                                         @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return authorityService.getAuthorityList(authorityName,0, current, sort, asc, pageSize);
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
    public Page<AuthorityVo> getAuthorityListByParentId(
                        @RequestParam(value = "authorityName",required = false) String authorityName,
                        @RequestParam(value = "parentId",required = false) Integer parentId,
                        @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                        @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                        @RequestParam(value = "asc",required = false) Boolean asc,
                        @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return authorityService.getAuthorityList(authorityName,parentId, current, sort, asc, pageSize);
    }


    /**
     * 添加
     * @param authority
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveAuthority(@Validated Authority authority) throws Exception{
        return authorityService.saveAuthority(authority);
    }

    /**
     * 修改
     * @param authority
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateAuthority(@Validated Authority authority) throws Exception{
        return authorityService.editAuthority(authority);
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

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableAuthority(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return authorityService.enableAuthority(id,type);
    }

}
