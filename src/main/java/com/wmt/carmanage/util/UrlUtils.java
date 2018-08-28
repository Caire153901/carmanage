package com.wmt.carmanage.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.util.Map;
import java.util.Set;

/**
 * Test 模块需用的测试
 */
public class UrlUtils {

    public static String convertParams(Map<String, String> params) {
        if (null == params || params.size() == 0) {
            return "";
        }

        StringBuffer sb = new StringBuffer();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            sb.append(key)
                    .append("=")
                    .append(params.get(key))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    /**
     * 向url链接追加参数
     * @param url
     * @param params Map<String, String>
     * @return
     */
    public static String appendParams(String url, Map<String, String> params) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(url), "url不能为null或空字符串");
        String paramsString = convertParams(params);
        url = url.trim();
        int length = url.length();
        int index = url.indexOf("?");
        if (index > -1) {//url说明有问号
            if ((length - 1) == index) {//url最后一个符号为？，如：http://wwww.baidu.com?
                url += paramsString;
            } else {//情况为：http://wwww.baidu.com?aa=11
                url += "&" + paramsString;
            }
        } else {//url后面没有问号，如：http://wwww.baidu.com
            url += "?" + paramsString;
        }
        return url;

    }

}
