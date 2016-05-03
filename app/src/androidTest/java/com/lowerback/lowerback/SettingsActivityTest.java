package com.lowerback.lowerback;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public final ActivityTestRule<SettingsActivity> main = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void shouldDefineInterval(){
        onView(withId(R.id.alarm_interval_EditText)).perform(clearText()).perform(typeText("30")).check(matches(withText("30")));
    }

    @Test
    public void shouldSaveIntervalDefined(){
        onView(withId(R.id.save_id)).perform(click());
    }
}
