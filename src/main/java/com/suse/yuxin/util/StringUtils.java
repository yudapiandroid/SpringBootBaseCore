package com.suse.yuxin.util;

import com.github.pagehelper.PageHelper;

import java.util.Map;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: 字符串工具类
 */
public class StringUtils {

    public static boolean isEmpty(String msg){
        return msg == null || msg.length() == 0;
    }


    /**
     * map中参数强转为Integer
     * @param map
     * @param key 带转换的键值
     * @return
     */
    public static Integer mapValueCaseInt(Map<String,Object> map, String key){
        if(map == null){
            return null;
        }
        Object value = map.get(key);
        if(value == null){
            return null;
        }
        if(value instanceof String) {
            return Integer.valueOf((String) value);
        }
        if(value instanceof Integer) {
            return (Integer) value;
        }
        return null;
    }


    /**
     * 格式化page参数并赋值
     */
    public static Map<String, Object> formatPageParam(Map<String,Object> map) {

        Integer page = mapValueCaseInt(map,"page");
        Integer pageSize = mapValueCaseInt(map,"pageSize");
        if(page == null || page<1 ){
            page = 1;
        }
        if(pageSize == null || pageSize <1 ){
            pageSize = 10;
        }
        PageHelper.startPage(page ,pageSize);
        return map;
    }

}
