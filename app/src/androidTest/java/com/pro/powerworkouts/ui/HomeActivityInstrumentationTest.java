package com.pro.powerworkouts.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.pro.powerworkouts.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class HomeActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<HomeActivity> homeActivityScenario = new ActivityScenarioRule<>(HomeActivity.class);
  View categoryListDecorView;
  
  @Before
  public void setUp()  {
    homeActivityScenario.getScenario().onActivity(activity -> categoryListDecorView = activity.getWindow().getDecorView());
  }

  @Test
  public void openHomeScreen_displaysTabs() {
    onView(withId(R.id.tab_layout)).check(matches(isDisplayed()));
  }

  @Test
  public void clickWorkoutTab_displaysWorkoutCategories() {
    sleep();
    onView(allOf(withText(getInstrumentation().getTargetContext().getString(R.string.workouts)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());

    // Sleep while workout categories are fetched from the API
    sleep();
    onView(withId(R.id.screen_title)).check(matches(isDisplayed()));
  }

  @Test
  public void clickDoneTab_displaysDoneWorkoutList() {
    sleep();
    onView(allOf(withText(getInstrumentation().getTargetContext().getString(R.string.done)), isDescendantOfA(withId(R.id.tab_layout)))).perform(click());

    // Sleep while done workouts are fetched from Firebase
    sleep();
    onView(withId(R.id.done_workout_list)).check(matches(isDisplayed()));
  }

  // TO BE CHANGED: USE ESPRESSO IDLING RESOURCES
  private void sleep(){
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}