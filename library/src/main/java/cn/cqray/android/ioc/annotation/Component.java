package cn.cqray.android.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 部件
 * @author Cqray
 */
@Target(TYPE)
@Retention(RUNTIME)
@Documented
public @interface Component {

    /**
     * 部件所包含的功能块
     * @return 功能块列表
     */
    Class<?>[] modules() default {};
}
