package cn.cqray.android.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 要注入的属性对象提供
 * @author Cqray
 */
@Target({ METHOD })
@Retention(RUNTIME)
@Documented
public @interface Provides {

    /**
     * 要返回的属性对象的名称
     * @return 名称
     */
    String name() default "";
}
