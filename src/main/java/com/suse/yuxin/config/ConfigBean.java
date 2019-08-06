package com.suse.yuxin.config;

import com.suse.yuxin.base.DefaultRequestParamConvertToMap;
import com.suse.yuxin.base.RequestParamConvertToMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnBean(CommonControllerMarker.Marker.class)
@Configuration
public class ConfigBean {

    @Bean
    public RequestParamConvertToMap convertToMap() {
        return new DefaultRequestParamConvertToMap();
    }

}
