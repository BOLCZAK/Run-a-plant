package com.example.fitpot;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class HomeActivityTest2 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void homeActivityTest2() {
        mActivityTestRule.getActivity().setSteps(2500);
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_home), withContentDescription("Home"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                0),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.button_water_plant), withText("Water the plant"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                3),
                        isDisplayed()));
        materialButton6.perform(click());
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
