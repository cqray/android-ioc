package cn.cqray.android.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 功能块
 * @author Cqray
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Module {}
