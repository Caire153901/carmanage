package com.wmt.carmanage.util;

import com.wmt.carmanage.entity.Authority;
import com.wmt.carmanage.mapper.AuthorityMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 工具类
 */
@SuppressWarnings("all")
@Component
public class ToolFunctions {

    private static AuthorityMapper authorityMapper;

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
    public static Set<Authority> getMenuTree(List<Authority> resourceList) {
        Set<Authority> result = new LinkedHashSet<>();
        // 最后的结果
        resourceList.stream().forEach(authority -> {
            if(authority.getParentId()==0){
                result.add(authority);
            }
        });
        // 为一级菜单设置子菜单，getChild是递归调用的
        for (Authority menu : result) {
            menu.setChildList(getChild(menu.getId(), resourceList));
        }
        return result;
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
    private static Set<Authority> getChild(Integer id, List<Authority> rootMenu) {
        // 子菜单
        Set<Authority> childList = new LinkedHashSet<>();
        for (Authority menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (null!=menu.getParentId()) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (Authority menu : childList) {// 没有url子菜单还有子菜单
            if (StringUtils.isBlank(menu.getUrl())) {
                // 递归
                menu.setChildList(getChild(menu.getId(), rootMenu));
            }
        } // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
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






}
