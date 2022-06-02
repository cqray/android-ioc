package cn.cqray.android.ioc;

import androidx.annotation.NonNull;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.cqray.android.ioc.annotation.Component;
import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Module;
import cn.cqray.android.ioc.annotation.Provides;

public class IocUtils {


    public static List<ModuleBuilder> getProviderFromComponents(Class<?>[] components) {
        List<ModuleBuilder> providers = new ArrayList<>();
        for (Class<?> component : components) {
            Component com = component.getAnnotation(Component.class);
            if (com != null) {
                providers.addAll(getProviderFromModules(com.modules()));
            }
        }
        return providers;
    }

    @NonNull
    static List<ModuleBuilder> getProviderFromModules(Class<?>[] classes) {
        List<ModuleBuilder> list = new ArrayList<>();
        if (classes != null) {
            for (Class<?> cls : classes) {
                Module module = cls.getAnnotation(Module.class);
                if (module != null) {
                    ModuleBuilder provider = new ModuleBuilder(cls);
                    list.add(provider);
                }
            }
            Collections.sort(list, new Comparator<ModuleBuilder>() {
                @Override
                public int compare(ModuleBuilder o1, ModuleBuilder o2) {
                    return o1.getInjectFieldCount() - o2.getInjectFieldCount();
                }
            });
        }
        return list;
    }

    @NonNull
    public static List<Method> getProvidesMethods(@NonNull Class<?> cls) {
        List<Method> list = new ArrayList<>();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            Provides provides = method.getAnnotation(Provides.class);
            if (provides != null) {
                // 检查是否是符合规范的注解
                CheckUtils.checkProvidesMethod(method);
                // 加入列表
                list.add(method);
            }
        }
        return list;
    }

    /**
     * 从类中获取注入的属性字段列表
     * @param cls 指定的类
     * @return 属性字段列表
     */
    @NonNull
    public static List<Field> getInjectFields(@NonNull Class<?> cls) {
        List<Field> list = new ArrayList<>();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Inject inject = field.getAnnotation(Inject.class);
            if (inject != null) {
                // 检查是否是符合规范的注解
                CheckUtils.checkInjectFiled(field);
                // 加入列表
                list.add(field);
            }
        }
        return list;
    }
}
