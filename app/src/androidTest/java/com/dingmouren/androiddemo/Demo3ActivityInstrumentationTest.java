package com.dingmouren.androiddemo;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;

import com.dingmouren.androiddemo.activity.Demo3Activity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by dingmouren on 2017/4/4.
 * 1.找到id为editText的View，输入Peter，然后关闭软键盘
 * 2.点击Say hello!的View，如果没有布局文件xml中为Button设置id，可以通过搜索文字来找到它
 * 3.将TextView上的文本同预期结果对比，如果一致测试通过
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class Demo3ActivityInstrumentationTest {
    private static final String STRING_TO_BE_TYPED = "P";
    @Rule
    public ActivityTestRule<Demo3Activity> mActivityRule = new ActivityTestRule<Demo3Activity>(Demo3Activity.class);

    @Test
    public void sayHello(){
        onView(withId(R.id.editText)).perform(typeText(STRING_TO_BE_TYPED),closeSoftKeyboard());
        onView(withId(R.id.btn)).perform(click());
        String expectedText = "Hello," + STRING_TO_BE_TYPED;
        onView(withId(R.id.textView)).check(matches(withText(expectedText)));
    }
}
