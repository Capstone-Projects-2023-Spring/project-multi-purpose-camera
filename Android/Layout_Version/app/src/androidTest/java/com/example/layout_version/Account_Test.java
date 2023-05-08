package com.example.layout_version;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.datatransport.Priority;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class Account_Test {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void LoginMatch_Test() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.login), withText("Log In"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Log In")));
    }

    @Test
    public void SignUpMatch_Test() {
        ViewInteraction textView = onView(
                allOf(withId(R.id.signup), withText("Sign Up"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.RelativeLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Sign Up")));
    }

    @Test
    public void LoginClick_Test() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.login), withText("Log In"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.RelativeLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    @Test
    public void ViewAndLibrary_Test() throws InterruptedException {
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.username))
                .perform(typeText("John Smith"), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText("Password"), closeSoftKeyboard());
        onView(withId(R.id.login))
                .perform(click());
        Thread.sleep(500);
        onView(withId(R.id.library))
                .perform(click());
        onView(withId(R.id.view))
                .perform(click());
        Thread.sleep(500);
        onView(withId(R.id.settings))
                .perform(click());
        Thread.sleep(500);
        onView(withId(R.id.back_home_text_setting))
                .perform(click());
        Thread.sleep(500);
//        onView(withId(R.id.account))
//                .perform(click());
//        Thread.sleep(500);
//        onView(withId(R.id.library))
//                .perform(click());
//        Thread.sleep(500);
//        onView(withId(R.id.videoThumbnailImageView))
//                .perform(click());
//        Thread.sleep(500);
//        onView(withId(R.id.playButton))
//                .perform(click());
//        Thread.sleep(500);
    }



    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
