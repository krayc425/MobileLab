package cn.edu.nju.candleflame.architecturedemo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import cn.edu.nju.candleflame.architecturedemo.mvc.bean.NumberBean;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {
    @Rule
    public ActivityTestRule<Main> activityTestRule = new ActivityTestRule<>(Main.class);

    @Test
    public void exampleTest() {
        // 检查初始值
        onView(withId(R.id.mvc_number)).check(matches(withText(String.valueOf(NumberBean.DEFAULT_NUM))));
        // 点击Add按钮
        onView(withId(R.id.mvc_add)).perform(click());
        // 检查Add一次后的值
        onView(withId(R.id.mvc_number)).check(matches(withText(String.valueOf(NumberBean.DEFAULT_NUM + 1))));
        // 点击Sub按钮
        onView(withId(R.id.mvc_sub)).perform(click());
        // 检查Add一次、Sub一次后的值
        onView(withId(R.id.mvc_number)).check(matches(withText(String.valueOf(NumberBean.DEFAULT_NUM))));
    }
}
