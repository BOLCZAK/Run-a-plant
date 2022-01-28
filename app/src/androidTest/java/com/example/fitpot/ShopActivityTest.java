package com.example.fitpot;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
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
public class ShopActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void shopActivity() {
        mActivityTestRule.getActivity().setSteps(1700);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.navigation_dashboard), withContentDescription("Shop"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.imageShopItem1), withContentDescription("Accumulate more water"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.imageShopItem1), withContentDescription("Accumulate more water"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.imageShopItem1), withContentDescription("Accumulate more water"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.imageShopItem1), withContentDescription("Accumulate more water"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.imageShopItem2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                10),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.imageShopItem3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                9),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(R.id.imageShopItem1), withContentDescription("Accumulate more water"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                8),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        ViewInteraction appCompatImageView8 = onView(
                allOf(withId(R.id.imageShopItem2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                10),
                        isDisplayed()));
        appCompatImageView8.perform(click());

        ViewInteraction appCompatImageView9 = onView(
                allOf(withId(R.id.imageShopItem3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment_activity_main),
                                        0),
                                9),
                        isDisplayed()));
        appCompatImageView9.perform(click());
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
