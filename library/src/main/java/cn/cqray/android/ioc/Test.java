package cn.cqray.android.ioc;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;

import cn.cqray.android.ioc.annotation.Test11;

public class Test {

    public static void main(String[] args) {

//        T t = new T();
//        AndroidIoc.with(C.class).inject(t);

        List<ModuleProvider> providerList = IocUtils.getModules(new Class[] {Mo2.class, Mo.class});
        for (ModuleProvider provider : providerList) {
            System.out.println(provider.mClass.getName());
        }

        Field[] field = Test11.class.getDeclaredFields();
        for (Field field1 : field) {
            System.out.println(field1.getName() + "|" + field1.getModifiers());
        }
    }
}
