package cn.leancloud.leanstoragegettingstarted;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVAnalytics;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

/**
 * Created by BinaryHB on 16/9/13.
 */
public class GettingStartedApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this, "LC_APP_ID", "LC_CLIENT_KEY");
        AVOSCloud.setDebugLogEnabled(true);
        AVAnalytics.enableCrashReport(this, true);

        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "UMENG_APP_KEY", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "UMENG_MESSAGE_SECRET");

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                System.out.println("Device Token : " + deviceToken);

                //返回：AoiVyKQENUthY4n7x0X8F_rdDqb44Vkiwy_HgdSDX9bO
            }

            @Override
            public void onFailure(String s, String s1) {
                System.out.println("Failed " + s + " , " + s1);
            }
        });
    }
}
