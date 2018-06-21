package cn.edu.nju.candleflame.architecturedemo;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;

import org.junit.Test;
import org.junit.runner.RunWith;

import cn.edu.nju.candleflame.architecturedemo.mvc.bean.NumberBean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class UiAutomatorTest {
    @Test
    public void exampleTest() throws Exception {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        UiDevice uiDevice = UiDevice.getInstance(instrumentation);
        Context context = instrumentation.getContext();

        Intent intent = context.getPackageManager().getLaunchIntentForPackage("cn.edu.nju.candleflame.architecturedemo");
        assertNotNull(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Thread.sleep(5000);

        UiObject number = uiDevice.findObject(new UiSelector().resourceId("cn.edu.nju.candleflame.architecturedemo:id/mvc_number"));
        UiObject add = uiDevice.findObject(new UiSelector().resourceId("cn.edu.nju.candleflame.architecturedemo:id/mvc_add"));
        UiObject sub = uiDevice.findObject(new UiSelector().resourceId("cn.edu.nju.candleflame.architecturedemo:id/mvc_sub"));
        assertEquals(number.getText(), String.valueOf(NumberBean.DEFAULT_NUM));
        add.click();
        assertEquals(number.getText(), String.valueOf(NumberBean.DEFAULT_NUM + 1));
        sub.click();
        assertEquals(number.getText(), String.valueOf(NumberBean.DEFAULT_NUM));

        uiDevice.pressHome();
        intent = context.getPackageManager().getLaunchIntentForPackage("cn.leancloud.leanstoragegettingstarted");
        assertNotNull(intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Thread.sleep(5000);
        // more test for another app
    }
}
