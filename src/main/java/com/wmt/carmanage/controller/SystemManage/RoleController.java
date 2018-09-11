package com.wmt.carmanage.controller.SystemManage;

import com.baomidou.mybatisplus.plugins.Page;
import com.wmt.carmanage.constant.EUDataGridResult;
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
    public EUDataGridResult getRoleList(@RequestParam(value = "roleName",required = false) String roleName,
                                    @RequestParam(value = "page",required = false,defaultValue = "1") Integer current,
                                    @RequestParam(value = "sort",required = false,defaultValue = "gmtModified") String sort,
                                    @RequestParam(value = "order",required = false) String asc,
                                    @Max(value = 100,message = "每页条数不超过100") @RequestParam(value = "rows",required = false,defaultValue = "10") Integer pageSize)
            throws Exception{
        Page<RoleVo> page = roleService.getRoleList(roleName, current, sort, asc, pageSize);
        EUDataGridResult all = new EUDataGridResult();
        all.setRows(page.getRecords());
        all.setTotal(page.getTotal());
        return all;
    }

    /**
     * 添加
     * @param role
     * @return
     * @throws Exception
     */
    @PostMapping("/addOrUpdateRole")
    public boolean addOrUpdateRole(@Validated Role role) throws Exception{
        if(null==role.getId()){
            return roleService.saveRole(role);
        }else{
            return roleService.editRole(role);
        }
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

    /**
     * 保存用户与权限的关系
     * @param id
     * @param resourceIds
     * @return
     * @throws Exception
     */
    @PostMapping("/saveRoleResource")
    public boolean saveRoleResource(@RequestParam Integer id,@RequestParam("resourcesIds[]") Integer[] resourceIds) throws Exception {
        return roleService.saveRoleResource(id,resourceIds);
    }
}
