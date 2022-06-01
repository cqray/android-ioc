package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.cqray.android.ioc.annotation.Component;
import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Provides;

public class AndroidIoc {

    private final Map<Class<?>, Object> MODULE_MAP = new ConcurrentHashMap<>();

    private Class<?> [] mModules;

    private AndroidIoc(Class<?>[] modules) {
        mModules = modules;
        if (modules != null) {
            for (Class<?> module : modules) {
                try {
                    MODULE_MAP.put(module, module.newInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                //module.getMethods()[0].getReturnType();
            }
        }
    }

    @NonNull
    public static ModuleFactory with(Class<?>... classes) {
//        Class<?> [] modules = null;
//        if (cls != null) {
//            Component component = cls.getAnnotation(Component.class);
//            if (component != null) {
//                System.out.println(component.modules());
//                modules = component.modules();
//            }
//        }
        return new ModuleFactory(classes);
    }



    public void inject(Object target) {
        if (target != null) {
            Field[] fields = target.getClass().getDeclaredFields();
            List<Field> fieldList = new ArrayList<>();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    fieldList.add(field);
                }
            }

            if (mModules != null) {
                for (Field field : fieldList) {
                    Class<?> fieldType = field.getType();
                    for (Class<?> module : mModules) {
                        Object obj = MODULE_MAP.get(module);
                        Method[] methods = module.getDeclaredMethods();
                        for (Method method : methods) {
                            Provides provides = method.getAnnotation(Provides.class);
                            if (provides != null && method.getReturnType() == fieldType) {
                                // 匹配
                                method.setAccessible(true);
                                field.setAccessible(true);
                                try {
                                    field.set(target, method.invoke(obj));
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}
