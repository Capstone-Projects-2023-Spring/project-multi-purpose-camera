package com.example.layout_version;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction imageView = onView(
                allOf(withId(R.id.settings),
                        withParent(allOf(withId(R.id.nav_bar_top),
                                withParent(withId(R.id.main_layout)))),
                        isDisplayed()));
        imageView.check(matches(isDisplayed()));


        ViewInteraction button = onView(
                allOf(withId(R.id.library), withText("LIBRARY"),
                        withParent(allOf(withId(R.id.nav_bar_bottom),
                                withParent(withId(R.id.main_layout)))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction viewGroup = onView(
                allOf(withId(R.id.main_layout),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(com.google.android.material.R.id.action_bar_root)))),
                        isDisplayed()));
        viewGroup.check(matches(isDisplayed()));

        ViewInteraction viewGroup2 = onView(
                allOf(withId(R.id.main_layout),
                        withParent(allOf(withId(android.R.id.content),
                                withParent(withId(com.google.android.material.R.id.action_bar_root)))),
                        isDisplayed()));
        viewGroup2.check(matches(isDisplayed()));
    }
}
