package cn.cqray.android.ioc;

import cn.cqray.android.ioc.annotation.Module;
import cn.cqray.android.ioc.annotation.Provides;

@Module
public class Mo {

    @Provides
    Integer getText() {
        return 10;
    }
}
