package com.suse.yuxin.controller;


import com.github.pagehelper.PageHelper;
import com.suse.yuxin.base.BaseException;
import com.suse.yuxin.base.RequestParamConvertToMap;
import com.suse.yuxin.base.ResponseBean;
import com.suse.yuxin.config.CommonControllerMarker;
import com.suse.yuxin.intercepter.CommonControllerIntercepter;
import com.suse.yuxin.util.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description:  通用类型的 controller
 */
@ConditionalOnBean(CommonControllerMarker.Marker.class)
@RestController
@RequestMapping(produces = "application/json")
public class CommonController implements ApplicationContextAware {

    @Autowired(required = false)
    CommonControllerIntercepter filter;

    @Autowired
    RequestParamConvertToMap msgToMap;

    ApplicationContext context;

    // beanName#methodName Method 的一个缓存
    ConcurrentHashMap<String,Method> methodCache = new ConcurrentHashMap<String, Method>();

    @RequestMapping("/{beanName}/{beanMethod}")
    public ResponseBean<?> common(
            @PathVariable("beanName") String name,
            @PathVariable("beanMethod") String method,
            HttpServletRequest request) {
        Map<String, Object> data = null;

        // 请求消息转换成map
        try {
            data = msgToMap.convertParam(request);
        } catch (BaseException e) {
            return new ResponseBean<Object>(e.getCode(), e.getMessage());
        }

        // 请求前的过滤
        if(filter != null) {
            ResponseBean b = filter.beforeInvoke(name, method, data);
            if(b != null) {
                return b;
            }
        }
        // 请求方法调用
        ResponseBean rb = null;
        try {
            rb = invokeBeanMethod(name, method, data);
        } catch (BaseException e) {
            return new ResponseBean<Object>(e.getCode(), e.getMessage());
        }

        // 请求后的过滤
        if(filter != null) {
            ResponseBean b = filter.afterInvoke(name, method, data,rb);
            if(b != null) {
                return b;
            }
        }
        return rb == null ? new ResponseBean<Object>(null) : rb;
    }


    /**
    * @description 处理 mybatis pageHelper 的分页问题
    * @author  yuxin
    * @date   2019/8/6 16:37
    * @param
    * @param m
     * @param data
     * @return
    * @exception
    *
    */
    private void handPageInfo(Method m, Map<String, Object> data) {
        Type t = m.getAnnotatedReturnType().getType();
        System.out.println(t.getTypeName());
        if(t.getTypeName().contains("pagehelper")) {
            // 处理分页
            StringUtils.formatPageParam(data);
        }else{
            //返回类型非分页数据对象，清理分页缓存
            PageHelper.clearPage();
        }
    }


    /**
    * @description 调用名字是 name 的bean的method方法
    * @author  yuxin
    * @date   2019/8/6 10:26
    * @param
    * @return
    * @exception
    *
    */
    private ResponseBean invokeBeanMethod(String name, String method, Map<String, Object> data) throws BaseException {
        Object bean = loadBeanFromContextByBeanName(name);
        Method m = checkCacheOrLoadBean(name,method,bean.getClass());
        // 处理分页返回的情况
        handPageInfo(m, data);
        // 方法调用
        try {
            Object result = m.invoke(bean, data == null ? new HashMap<String, Object>() : data);
            return new ResponseBean(result);
        } catch (Exception e) {
            throw new BaseException(ResponseBean.RESPONSE_CODE_SERVER_ERROR, "call method error " + e.getMessage());
        }
    }


    /**
    * @description 从上下文加载名字是name的bean
    * @author  yuxin
    * @date   2019/8/6 10:26
    * @param
    * @return
    * @exception
    *
    */
    private Object loadBeanFromContextByBeanName(String name) throws BaseException {
        if(context == null){
            throw new BaseException(ResponseBean.RESPONSE_CODE_SERVER_ERROR, "context is null");
        }
        try {
            Object bean = context.getBean(name);
            return bean;
        } catch (Exception e) {
            throw new BaseException(ResponseBean.RESPONSE_CODE_SERVER_ERROR, "no beanName is " + name);
        }
    }


    /**
    * @description 获取缓存的bean的方法避免每次都需要使用反射
    * @author  yuxin
    * @date   2019/8/6 10:27
    * @param
    * @return
    * @exception
    *
    */
    private Method checkCacheOrLoadBean(String name, String method, Class<?> clazz) throws BaseException {
        String cacheKey = name + "#" + method;
        Method m = methodCache.get(cacheKey);
        if(m != null){
            return m;
        }else{
            try {
                Method dm = clazz.getDeclaredMethod(method, Map.class);
                methodCache.put(cacheKey, dm);
                return dm;
            } catch (NoSuchMethodException e) {
                throw new BaseException(ResponseBean.RESPONSE_CODE_NOT_FOUND, "no method is " + method);
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
