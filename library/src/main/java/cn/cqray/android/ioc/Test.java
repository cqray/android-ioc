package cn.cqray.android.ioc;


public class Test {

    public static void main(String[] args) {

        long time = System.currentTimeMillis();
        T t = new T();
//        Ioc.with(C.class).inject(t);
//        System.out.println(1 >> 1);
//        System.out.println(1 << 1);

        Ioc.with(C.class).inject(t);

        System.out.println(t.tt + "|" + IocManager.get(Mo2.class).getText() + "|" + (System.currentTimeMillis() - time));
    }
}
