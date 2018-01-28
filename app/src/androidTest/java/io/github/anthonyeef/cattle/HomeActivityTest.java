package io.github.anthonyeef.cattle;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.anthonyeef.cattle.activity.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void clickFloatingActionButton_OpenComposeActivity() {
        onView(withId(R.id.fab)).perform(click());
        onView(allOf(withId(android.R.id.content), withClassName(endsWith("EditText")))).check(matches(isDisplayed()));
    }
}
