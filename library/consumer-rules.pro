
#-keepnames class * extends cn.cqray.android.ioc.annotation.Module
#-keepnames class * extends cn.cqray.android.ioc.annotation.Component

# 保证LifecycleViewModel构造函数不被混淆
-keepclassmembers class * {
    public <init>(***);
    @cn.cqray.android.ioc.annotation.Provides <methods>;
    @cn.cqray.android.ioc.annotation.Inject <fields>;
}