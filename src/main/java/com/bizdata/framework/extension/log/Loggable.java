package com.bizdata.framework.extension.log;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志初始化注解,省去每次在需要用到日志的地方进行初始化,直接使用该注解完成初始化
 *
 * @version 1.0
 *
 * @author sdevil507
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Loggable {

}
