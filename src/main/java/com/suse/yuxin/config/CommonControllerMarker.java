package com.suse.yuxin.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
* @description  CommonController的标记类
* @author  yuxin
* @date   2019/8/6 9:26
* @param
* @return
* @exception
*/
@Configuration
public class CommonControllerMarker  {

    @Bean
    public Marker marker() {
        return new Marker();
    }

    public static class Marker {}
}
