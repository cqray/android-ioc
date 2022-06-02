package cn.cqray.demo.ioc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import cn.cqray.android.ioc.Ioc;
import cn.cqray.android.ioc.C;
import cn.cqray.android.ioc.T;
import cn.cqray.android.ioc.annotation.Test11;
import dalvik.system.DexFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        T t = new T();
        Ioc.with(C.class).inject(t);

        System.out.println(t.tt);
        Log.e("数据", "TT:" + t.tt);


        Test11 t1 =new Test11();
        t1.text = "";

        Field[] field = Test11.class.getDeclaredFields();
        Log.e("数据", "changfdu:" + field.length);
        for (Field field1 : field) {
            System.out.println(field1.getName() + "|" + field1.getModifiers());
            Log.e("数据", field1.getName() + "|" + field1.getModifiers());
        }

    }

    public void test() {
        long time = System.currentTimeMillis();
        List<String> list = getClassName("");
        for (String s : list) {
            Log.e("数据", "类型：" + s);
        }

        Log.e("数据", "勇士：" + (System.currentTimeMillis() - time));
    }

    public List<String > getClassName(String packageName){
        List<String > classNameList=new ArrayList<String >();
        try {

            DexFile df = new DexFile(this.getPackageCodePath());//通过DexFile查找当前的APK中可执行文件
            Enumeration<String> enumeration = df.entries();//获取df中的元素  这里包含了所有可执行的类名 该类名包含了包名+类名的方式
            while (enumeration.hasMoreElements()) {//遍历
                String className = (String) enumeration.nextElement();

                if (className.contains(packageName)) {//在当前所有可执行的类里面查找包含有该包名的所有类
                    classNameList.add(className);
                    try {
                        Class<?> cls = getClassLoader().loadClass(className);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  classNameList;
    }
}