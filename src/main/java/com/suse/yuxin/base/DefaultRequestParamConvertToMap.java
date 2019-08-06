package com.suse.yuxin.base;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: 消息转换的默认实现
 */
public class DefaultRequestParamConvertToMap implements RequestParamConvertToMap {

    public Map<String, Object> convertParam(HttpServletRequest request) throws BaseException {
        if("GET".equalsIgnoreCase(request.getMethod())) {
            return handGetRequest(request);
        } else {
            // 除了GET 其他的都解析 request body
            return handRequestBody(request);
        }
    }

    private Map<String, Object> handRequestBody(HttpServletRequest request) throws BaseException {
        int length = request.getContentLength();
        byte[] content = new byte[length];
        try {
            request.getInputStream().read(content, 0,length);
        } catch (IOException e) {
            throw new BaseException(ResponseBean.RESPONSE_CODE_CLIENT_ERROR, "parse body error " + e.getMessage());
        }
        return JSON.parseObject(content,Map.class);
    }

    private Map<String, Object> handGetRequest(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        Enumeration<String> names = request.getParameterNames();
        if(names != null){
            while (names.hasMoreElements()) {
                String key = names.nextElement();
                data.put(key, request.getParameter(key));
            }
        }
        return data;
    }

}
