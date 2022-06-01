package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 检验工具集合
 * @author Cqray
 */
public class CheckUtils {

    /** 属性字段异常信息 **/
    private static final String EXCEPTION_FOR_FIELD = "Injected field %s can not be %s.";
    private static final String EXCEPTION_FOR_METHOD = "Provides method %s can not be %s.";
    private static final String EXCEPTION_FOR_CONSTRUCTOR = "%s requires a non private parameterless constructor.";


    public static void checkConstructor(@NonNull Class<?> cls) {
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        boolean hasNoParameterConstructor = false;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0
                    && constructor.getModifiers() != 1 >> 1) {
                hasNoParameterConstructor = true;
                break;
            }
        }
        if (!hasNoParameterConstructor) {
            // 需要一个非私有的无参构造函数
            throw new RuntimeException(String.format(EXCEPTION_FOR_CONSTRUCTOR, cls.getName()));
        }
    }

    /**
     * 检验注解字段
     * @param field 字段
     */
    public static void checkInjectFiled(@NonNull Field field) {
        int modifier = field.getModifiers();
        // 不能是private修饰
        if (Modifier.isPrivate(modifier)) {
            throw new RuntimeException(String.format(EXCEPTION_FOR_FIELD, field.getName(), "private"));
        }
        // 不能是final字段
        if (Modifier.isFinal(modifier)) {
            throw new RuntimeException(String.format(EXCEPTION_FOR_FIELD, field.getName(), "final"));
        }
        // 不能是static
        if (Modifier.isStatic(modifier)) {
            throw new RuntimeException(String.format(EXCEPTION_FOR_FIELD, field.getName(), "static"));
        }
    }

    /**
     * 检验注解方法
     * @param method 方法
     */
    public static void checkProvidesMethod(@NonNull Method method) {
        int modifier = method.getModifiers();
        // 不能是private方法
        if (Modifier.isPrivate(modifier)) {
            throw new RuntimeException(String.format(EXCEPTION_FOR_METHOD, method.getName(), "private"));
        }
        // 不能是static方法
        if (Modifier.isStatic(modifier)) {
            throw new RuntimeException(String.format(EXCEPTION_FOR_METHOD, method.getName(), "static"));
        }
    }
}
