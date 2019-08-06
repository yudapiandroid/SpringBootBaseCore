package com.suse.yuxin.annotation;


import com.suse.yuxin.config.CommonControllerMarker;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({CommonControllerMarker.class})
public @interface EnableCommonController {
}
