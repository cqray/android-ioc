package cn.cqray.android.ioc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 功能块实例对象容器
 * @author Cqray
 */
class ModuleContainer {

    /** 提供者集合 **/
    static final Map<String, ModuleProvider> PROVIDER_MAP = new ConcurrentHashMap<>();

    public static void put(String key, ModuleProvider provider) {
        PROVIDER_MAP.put(key, provider);
    }

    public static ModuleProvider get(String key) {
        return PROVIDER_MAP.get(key);
    }
}
