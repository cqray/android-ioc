package cn.cqray.android.ioc;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Module提供者
 * @author Cqray
 */
public class ModuleProvider {

    /** 对应的类 **/
    Class<?> mClass;
    /** 对应的实例 **/
    Object mObject;

    List<Field> mInjectFields;
    List<Method> mProvidesMethods;
    int mInjectFieldCount;

    public ModuleProvider(@NonNull Class<?> cls) {
        CheckUtils.checkConstructor(cls);
        mClass = cls;
        mInjectFields = IocUtils.getInjectFields(cls);
        mInjectFieldCount = mInjectFields.size();
    }

    public int getInjectFieldCount() {
        return mInjectFieldCount;
    }
}
