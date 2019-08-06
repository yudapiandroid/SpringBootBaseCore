package com.suse.yuxin.intercepter;

import com.suse.yuxin.base.ResponseBean;

import java.util.Map;

/**
 * @author: yuxin
 * @date: 2019/8/6
 * @description: CommonController的拦截器
 */
public interface CommonControllerIntercepter {

    /**
    * @description commoncontroller处理之前的拦截
    * @author  yuxin
    * @date   2019/8/6 9:57
    * @param
    * @return
    */
    ResponseBean beforeInvoke(String beanName, String method, Map<String,Object> data);


    /**
    * @description commoncontroller处理之后的拦截
    * @author  yuxin
    * @date   2019/8/6 9:58
    * @param
    * @return
    * @exception
    *
    */
    ResponseBean afterInvoke(String beanName, String method, Map<String,Object> data, ResponseBean b);

}
