package cn.cqray.android.ioc;

import cn.cqray.android.ioc.annotation.Inject;
import cn.cqray.android.ioc.annotation.Module;
import cn.cqray.android.ioc.annotation.Provides;

@Module
public class Mo2 {

    @Inject Integer c;

//    public Mo2(int s) {
//
//    }

    @Provides
    String getText() {
        return "10";
    }
}
