package com.pro.powerworkouts.ui;

import static org.junit.Assert.*;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.pro.powerworkouts.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
public class HomeActivityTest {
  private HomeActivity activity;
  private TabLayout tabLayout;

  @Before
  public void setUp()  {
    activity = Robolectric.buildActivity(HomeActivity.class)
            .create()
            .start()
            .resume()
            .get();
    tabLayout = activity.findViewById(R.id.tab_layout);
  }

  @Test
  public void validateTabs() {
    TabLayout.Tab workouts = tabLayout.getTabAt(0);
    TabLayout.Tab done = tabLayout.getTabAt(1);

    assertEquals(activity.getString(R.string.workouts), Objects.requireNonNull(workouts).getText());
    assertEquals(activity.getString(R.string.done), Objects.requireNonNull(done).getText());
  }

  @Test
  public void validateTabCount() {
    assertEquals(2, tabLayout.getTabCount());
  }
}