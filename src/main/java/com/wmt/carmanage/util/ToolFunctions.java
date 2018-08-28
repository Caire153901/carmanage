package com.wmt.carmanage.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.entity.RoleAuthority;
import com.wmt.carmanage.mapper.AuthorityMapper;
import com.wmt.carmanage.service.AuthorityService;
import com.wmt.carmanage.service.RoleAuthorityService;
import com.wmt.carmanage.vo.AuthorityVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author qiuyj
 * @date 2017/10/10
 * @description 类描述
 */
@SuppressWarnings("all")
@Component
public class ToolFunctions {

    private static AuthorityMapper authorityMapper;
    private static AuthorityService authorityService;
    private static RoleAuthorityService roleAuthorityService;

    @Autowired
    public static void setAuthorityService(AuthorityService authorityService) {
        ToolFunctions.authorityService = authorityService;
    }

    @Autowired
    public void setAuthorityMapper(AuthorityMapper authorityMapper){
        ToolFunctions.authorityMapper = authorityMapper;
    }

    public static String getPrintStack(Exception e){
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        return writer.getBuffer().toString() + e.getMessage();
    }

   /**
     * 只拥有子菜单权限不拥有父菜单权限
     * 获取菜单（包含其父菜单）树
     * @param resourceList
     * @return
     */
     public static Set<Authority> getMenuTree(List<Authority> authorityList) {
       Set<Authority> list = new LinkedHashSet<>();
        LinkedList<Authority> linkedList = new LinkedList<>(authorityList);
        while (!linkedList.isEmpty()) {
            Authority pop = linkedList.pop();
            if (pop.getParentId()==0) {
                list.remove(pop);
                list.add(pop);
            } else {
                Integer parentId = pop.getParentId();
                Authority resource = new Authority();
                resource.setId(parentId);
                int i = authorityList.indexOf(resource);
                if (i > -1) {
                    resource = authorityList.get(i);
                } else {
                    resource = authorityMapper.selectById(parentId);
                }
                resource.getChildList().add(pop);
                linkedList.add(resource);
            }
        }
        return list;
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        try{
            if(str==null || str.equals("null") || str.equals("")){
                return true;
            }
            return false;
        }catch(Exception e){
            return false;
        }
    }
    

    /**
     * json时间格式转换
     * @param dateString
     * @return
     */
    public Date dateUtil(String dateString){
        String rex = "/Date[(](.*?)[)]/";
        Pattern pattern = Pattern.compile(rex);
        Matcher m = pattern.matcher(dateString);
        if(m.find()){
            String str = m.group(1);
            String time = str.substring(0,str.length());
            try {
                return new Date(Long.parseLong(time));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            System.out.println("没有匹配到");
        }
        return null;
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 根据角色ID获取菜单
     * @param roleId
     * @return
     */
    public static List<AuthorityVo> getAuthorityListByRoleId(Integer roleId){
        // 最后的结果
        List<AuthorityVo> list = new ArrayList<>();
        Wrapper<RoleAuthority> roleAuthorityWrapper = new EntityWrapper<>();
        roleAuthorityWrapper.eq("role_id", roleId);
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.selectList(roleAuthorityWrapper);
        // 原始的数据
        List<AuthorityVo> authorityVoList = new ArrayList<>();
        roleAuthorityList.stream().forEach(roleAuthority -> {
            AuthorityVo authorityVo = new AuthorityVo();
            Authority authority = authorityMapper.selectById(roleAuthority.getAuthorityId());
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
     * 获取拼接的权限名
     * @param roleId
     * @return
     */
    public static String getAuthorityNamesByRoleId(Integer roleId){
        List<String> stringList = new ArrayList<>();
        List<AuthorityVo> list = getAuthorityListByRoleId(roleId);
        list.stream().forEach(l->{
           stringList.add(l.getAuthorityName());
        });
       return String.join("-",stringList);
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
}
