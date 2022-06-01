package cn.cqray.android.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Provides;

/**
 * 功能块生成工厂
 * @author Cqray
 */
public class ModuleFactory {

    private List<ModuleProvider> mProviders;

    ModuleFactory(Class<?>[] components) {
        mProviders = IocUtils.getProviderFromComponents(components);
    }

    public void inject(Object target) {

        if (target != null && !mProviders.isEmpty()) {

            initModuleObjects();

            // 获取并检验目标对象注入的属性字段
            Field[] fields = target.getClass().getDeclaredFields();
            List<Field> fieldList = new ArrayList<>();
            for (Field field : fields) {
                Inject inject = field.getAnnotation(Inject.class);
                if (inject != null) {
                    CheckUtils.checkInjectFiled(field);
                    fieldList.add(field);
                }
            }

            for (Field field : fieldList) {
                Class<?> fieldType = field.getType();
                for (ModuleProvider provider : mProviders) {
                    //ModuleProvider provider = ModuleContainer.get(module.getName());
                    List<Method> methods = IocUtils.getProvidesMethods(provider.mClass);
                    for (Method method : methods) {
                        Provides provides = method.getAnnotation(Provides.class);
                        if (provides != null && method.getReturnType() == fieldType) {
                            // 匹配
                            method.setAccessible(true);
                            field.setAccessible(true);
                            try {
                                field.set(target, method.invoke(provider.mObject));
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

    /**
     * 初始化功能块对应的对象实例
     */
    private void initModuleObjects() {
        for (ModuleProvider provider : mProviders) {
            String className = provider.mClass.getName();
            try {
                provider.mObject = provider.mClass.newInstance();
            } catch (Exception ignore) {}
            ModuleContainer.put(className, provider);
        }
    }
}
