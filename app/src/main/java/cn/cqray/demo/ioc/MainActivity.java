package cn.cqray.demo.ioc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Field;

import cn.cqray.android.ioc.AndroidIoc;
import cn.cqray.android.ioc.C;
import cn.cqray.android.ioc.T;
import cn.cqray.android.ioc.Test;
import cn.cqray.android.ioc.annotation.Test11;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        T t = new T();
        AndroidIoc.with(C.class).inject(t);

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
}