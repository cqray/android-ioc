package cn.cqray.android.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;


import cn.cqray.android.ioc.annotation.Provides;

/**
 * 功能块生成工厂
 * @author Cqray
 */
public class ModuleFactory {

    private List<ModuleBuilder> mProviders;

    ModuleFactory(Class<?>[] components) {
        mProviders = IocUtils.getProviderFromComponents(components);
    }

    public void inject(Object target) {

        if (target != null && !mProviders.isEmpty()) {

            for (ModuleBuilder provider : mProviders) {
                IocManager.putModule(provider.mClass, provider);
            }

            List<Field> fieldList = IocUtils.getInjectFields(target.getClass());

            for (Field field : fieldList) {
                Class<?> fieldType = field.getType();
                for (ModuleBuilder provider : mProviders) {
                    List<Method> methods = provider.mProvidesMethods;
                    for (Method method : methods) {
                        Provides provides = method.getAnnotation(Provides.class);
                        if (provides != null && method.getReturnType() == fieldType) {
                            // 匹配
                            method.setAccessible(true);
                            field.setAccessible(true);
                            try {
                                field.set(target, method.invoke(provider.mObject));
                                IocManager.putProvides(method.getReturnType().getName(), method.invoke(provider.mObject));
                            } catch (Exception ignore) {}
                            break;
                        }
                    }
                }
            }
        }
    }

//    /**
//     * 初始化功能块对应的对象实例
//     */
//    private void initModuleObjects() {
//        for (ModuleBuilder provider : mProviders) {
//            IocManager.putModule(provider.mClass, provider);
//        }
//    }
}
