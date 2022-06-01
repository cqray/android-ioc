package cn.cqray.android.ioc;


public class Test {

    public static void main(String[] args) {

        T t = new T();
//        AndroidIoc.with(C.class).inject(t);
//        System.out.println(1 >> 1);
//        System.out.println(1 << 1);

        AndroidIoc.with(C.class).inject(t);

        System.out.println(t.tt);
    }
}
