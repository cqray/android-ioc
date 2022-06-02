package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Provides;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Module提供者
 * @author Cqray
 */
@Data
@Accessors(prefix = "m")
public class ModuleBuilder {

    /** 对应的类 **/
    Class<?> mClass;
    /** 对应的实例 **/
    Object mObject;
    /** 当前Module提供的对象 **/
    List<Method> mProvidesMethods;
    /** 当前Module要注入的属性字段 **/
    List<Field> mInjectFields;
    /** 要注入的属性字段的数量 **/
    int mInjectFieldCount;

    /** 提供的对象集合 **/
    final Map<String, Object> mProvidesObjects = new ConcurrentHashMap<>();

    public ModuleBuilder(@NonNull Class<?> cls) {
        CheckUtils.checkConstructor(cls);
        mClass = cls;
        mProvidesMethods = getProvidesMethods(cls);
        mInjectFields = getInjectFields(cls);
        mInjectFieldCount = mInjectFields.size();
    }

    public int getInjectFieldCount() {
        return mInjectFieldCount;
    }

    public synchronized void build() {
        if (mObject == null) {
            try {
                mObject = mClass.newInstance();
            } catch (Exception ignore) {}
        }
    }

    @NonNull
    private List<Method> getProvidesMethods(@NonNull Class<?> cls) {
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
    private List<Field> getInjectFields(@NonNull Class<?> cls) {
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
