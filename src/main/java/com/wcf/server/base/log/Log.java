package com.wcf.server.base.log;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default "";

    /**
     * 是否记录请求参数，默认 true，涉及隐私的请求请设置false，如含用户密码的
     */
    boolean saveParams() default true;

    /**
     * 是否保存请求结果，默认 true，涉及隐私的请求请设置false，如含用户密码的
     */
    boolean saveResult() default true;
}