package com.suse.yuxin.base;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: 将请求的消息转化成 Map
 */
public interface RequestParamConvertToMap {

    Map<String, Object> convertParam(HttpServletRequest request) throws BaseException;

}
