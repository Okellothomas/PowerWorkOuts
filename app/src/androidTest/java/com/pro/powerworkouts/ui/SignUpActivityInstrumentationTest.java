package com.pro.powerworkouts.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.pro.powerworkouts.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignUpActivityInstrumentationTest {
  @Rule
  public ActivityScenarioRule<SignUpActivity> signUpActivityScenario = new ActivityScenarioRule<>(SignUpActivity.class);

  // Test only passes if user is not currently logged in
  @Test
  public void validateEditText() {
    onView(withId(R.id.nameEdit)).perform(typeText("John")).check(matches(withText("John"))).perform(closeSoftKeyboard());
    sleep(500);
    onView(withId(R.id.emailEdit)).perform(typeText("John@gmail.com")).check(matches(withText("John@gmail.com"))).perform(closeSoftKeyboard());
    sleep(500);
    onView(withId(R.id.passWord)).perform(typeText("John123")).check(matches(withText("John123"))).perform(closeSoftKeyboard());
    sleep(500);
    onView(withId(R.id.confirmPassWord)).perform(typeText("John123")).check(matches(withText("John123"))).perform(closeSoftKeyboard());
  }

  // TO BE CHANGED: USE ESPRESSO IDLING RESOURCES
  private void sleep(int milliseconds){
    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e){
      System.out.println("Got interrupted!");
    }
  }
}