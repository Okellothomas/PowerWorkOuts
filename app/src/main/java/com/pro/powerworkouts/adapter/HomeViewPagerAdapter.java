package com.pro.powerworkouts.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.pro.powerworkouts.fragment.DoneWorkoutFragment;
import com.pro.powerworkouts.fragment.WorkoutCategoryFragment;

public class HomeViewPagerAdapter extends FragmentStateAdapter {
  private static final int NUM_PAGES = 2;

  public HomeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
    super(fragmentActivity);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Fragment fragment = new Fragment();
    switch (position){
      case 0:
        fragment = new WorkoutCategoryFragment();
        break;
      case 1:
        fragment = new DoneWorkoutFragment();
        break;
    }
    return fragment;
  }

  @Override
  public int getItemCount() {
    return NUM_PAGES;
  }
}
