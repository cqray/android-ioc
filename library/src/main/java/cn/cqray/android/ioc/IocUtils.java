package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Module;

public class IocUtils {

    @NonNull
    public static List<ModuleProvider> getModules(Class<?>[] classes) {
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
                    return o1.getParamCount() - o2.getParamCount();
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
    public static List<Field> getInjectFields(Class<?> cls) {
        List<Field> list = new ArrayList<>();
        if (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    list.add(field);
                }
            }
            return list;
        }
        return list;
    }


    public static int getConstructorCount(Class<?> cls) {
        Constructor<?>[] constructor = cls.getDeclaredConstructors();
        return constructor.length;
    }
}
