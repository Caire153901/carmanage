package com.wmt.carmanage.controller.SystemManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.entity.Role;
import com.wmt.carmanage.service.RoleService;
import com.wmt.carmanage.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;
    /**
     * 获取角色列表，用于下拉列表
     * @return
     */
    @GetMapping("/select_list")
    public List<RoleVo> getRoleSelectList(){
        return roleService.getRoleSelect();
    }

    /**
     * 角色列表
     * @param roleName 角色名
     * @param current  当前页
     * @param sort     排序条件
     * @param asc      升降序
     * @param pageSize 每页条数
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public Page<RoleVo> getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                    @RequestParam(value = "current",required = false,defaultValue = "1") Integer current,
                                    @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                    @RequestParam(value = "asc",required = false) Boolean asc,
                                    @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        return roleService.getRoleList(roleName, current, sort, asc, pageSize);
    }

    /**
     * 添加
     * @param role
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    public boolean saveRole(@Validated Role role) throws Exception{
        return roleService.saveRole(role);
    }

    /**
     * 修改
     * @param role
     * @return
     * @throws Exception
     */
    @PostMapping("/edit")
    public boolean updateRole(@Validated Role role) throws Exception{
        return roleService.editRole(role);
    }


    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    public boolean deleteRole(@RequestParam Integer id) throws Exception{
        return roleService.deleteRole(id);
    }

    /**
     * 启用/禁用
     * @param id
     * @param type
     * @return
     * @throws Exception
     */
    @PostMapping("/enable")
    public boolean enableRole(@RequestParam Integer id,@RequestParam byte type) throws Exception{
        return roleService.enableRole(id,type);
    }
    
}
