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


    public static List<ModuleProvider> getProviderFromComponents(Class<?>[] components) {
        List<ModuleProvider> providers = new ArrayList<>();
        for (Class<?> component : components) {
            Component com = component.getAnnotation(Component.class);
            if (com != null) {
                providers.addAll(getProviderFromModules(com.modules()));
            }
        }
        return providers;
    }

    @NonNull
    static List<ModuleProvider> getProviderFromModules(Class<?>[] classes) {
        List<ModuleProvider> list = new ArrayList<>();
        if (classes != null) {
            for (Class<?> cls : classes) {
                Module module = cls.getAnnotation(Module.class);
                if (module != null) {
                    ModuleProvider provider = new ModuleProvider(cls);
                    list.add(provider);
                }
            }
            Collections.sort(list, new Comparator<ModuleProvider>() {
                @Override
                public int compare(ModuleProvider o1, ModuleProvider o2) {
                    return o1.getInjectFieldCount() - o2.getInjectFieldCount();
                }
            });
        }
        return list;
    }

    public static List<Field> getInjectFields(Object target) {
        List<Field> list = new ArrayList<>();
        if (target != null) {
            return null;
        }
        return list;
    }

    @NonNull
    public static List<Method> getProvidesMethods(Class<?> module) {
        List<Method> list = new ArrayList<>();
        Method[] methods = module.getDeclaredMethods();
        for (Method method : methods) {
            Provides provides = method.getAnnotation(Provides.class);
            if (provides != null) {
                CheckUtils.checkProvidesMethod(method);
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
    public static List<Field> getInjectFields(Class<?> cls) {
        List<Field> list = new ArrayList<>();
        if (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    CheckUtils.checkInjectFiled(field);
                    list.add(field);
                }
            }
            return list;
        }
        return list;
    }
}
