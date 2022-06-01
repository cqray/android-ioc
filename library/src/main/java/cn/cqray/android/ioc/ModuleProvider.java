package cn.cqray.android.ioc;

//import java.lang.reflect.Constructor;
//

import androidx.annotation.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;

public class ModuleProvider {
    
    /** 构造函数参数个数 **/
    int mParamCount;
    /** 参数类型 **/
    Class<?>[] mParamTypes;
    /** 对应的类 **/
    Class<?> mClass;
    /** 对应的实例 **/
    Object mObject;

    public ModuleProvider(@NonNull Class<?> cls) {
        mClass = cls;
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
//        if (constructors.length != 1) {
//            throw new RuntimeException(cls.getName() + " must have only one public constructor.");
//        }
        mParamTypes = constructors[0].getParameterTypes();

        checkConstructor();

        mParamCount = mParamTypes.length;

//        List<Field> injectFields = IocUtils.getInjectFields(cls);
//        if (injectFields.size() < mParamTypes.length) {
//            throw new RuntimeException("Constructor parameters not found in injection parameters.");
//        }
//
//        for (Class<?> type : mParamTypes) {
//            boolean found = false;
//            for (Field field : injectFields) {
//                if (field.getType() == type) {
//                    injectFields.remove(field);
//                    found = true;
//                    break;
//                }
//            }
//            if (!found) {
//                throw new RuntimeException("Constructor parameters not found in injection parameters.");
//            }
//        }
    }

    /**
     * 检查构造函数是否符合规范
     */
    private void checkConstructor() {
        Constructor<?>[] constructors = mClass.getDeclaredConstructors();
        boolean hasNoParameterConstructor = false;
        for (Constructor<?> constructor : constructors) {
            if (constructor.getParameterTypes().length == 0
                    && constructor.getModifiers() != 1 >> 1) {
                hasNoParameterConstructor = true;
                break;
            }
        }
        if (!hasNoParameterConstructor) {
            // 需要一个非私有的无参构造函数
            throw new RuntimeException(mClass.getName() + " requires a non private parameterless constructor.");
        }
    }

    public int getParamCount() {
        return mParamCount;
    }
}
