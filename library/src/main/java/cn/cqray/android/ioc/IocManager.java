package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.cqray.android.ioc.annotation.Module;

public class IocManager {

    /** 提供者集合 **/
    static final Map<Class<?>, ModuleBuilder> MODULE_MAP = new ConcurrentHashMap<>();
    /** 所有提供的对象的集合 **/
    static final Map<String, Object> PROVIDES_MAP = new ConcurrentHashMap<>();

    public static void putModule(Class<?> cls, ModuleBuilder mb) {
        ModuleBuilder val = MODULE_MAP.get(cls);
        if (val == null) {
            mb.build();
            MODULE_MAP.put(cls, mb);
        }
    }

    @NonNull
    public static synchronized ModuleBuilder getModule(@NonNull Class<?> cls) {
        Module module = cls.getAnnotation(Module.class);
        if (module == null) {
            throw new RuntimeException();
        }
        ModuleBuilder mb = MODULE_MAP.get(cls);
        if (mb == null) {
            mb = new ModuleBuilder(cls);
            putModule(cls, mb);
        }
        return mb;
    }

    public static void putProvides(String key, Object obj) {
        PROVIDES_MAP.put(key, obj);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(@NonNull Class<T> cls) {
        if (cls.getAnnotation(Module.class) != null) {
            return (T) getModule(cls).mObject;
        } else {
            return (T) PROVIDES_MAP.get(cls.getName());
        }
    }
}
