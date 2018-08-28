package com.wmt.carmanage.controller.SystemManage;


import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.vo.AuthorityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


}
