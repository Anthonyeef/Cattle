package io.github.anthonyeef.cattle;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.Gravity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.anthonyeef.cattle.activity.HomeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerActions.close;
import static android.support.test.espresso.contrib.DrawerActions.open;
import static android.support.test.espresso.contrib.DrawerMatchers.isClosed;
import static android.support.test.espresso.contrib.NavigationViewActions.navigateTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NavigationDrawerTest {

    @Rule
    public ActivityTestRule<HomeActivity> mHomeActivityActivityTestRule =
            new ActivityTestRule<>(HomeActivity.class);

    @Test
    public void clickProfileNavigationItem_ShowSelfProfile() {
        onView(withId(R.id.drawer_layout))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(open());

        onView(withId(R.id.nav_view))
                .perform(navigateTo(R.id.nav_profile));

        onView(withId(R.id.drawer_layout))
                .perform(close());

        onView(withId(R.id.following_count))
                .check(matches(isDisplayed()));
    }
}
