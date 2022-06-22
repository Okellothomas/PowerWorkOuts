package com.pro.powerworkouts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.adapter.HomeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
  @BindView(R.id.view_pager)
  ViewPager2 viewPager;
  @BindView(R.id.tab_layout)
  TabLayout tabLayout;

  private FragmentStateAdapter viewPagerAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

    viewPagerAdapter = new HomeViewPagerAdapter(this);
    viewPager.setAdapter(viewPagerAdapter);
    initializeTabLayout();
  }

  private void initializeTabLayout(){
    new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
      switch (position){
        case 0:
          tab.setText(R.string.workouts);
          break;
        case 1:
          tab.setText(R.string.saved);
          break;
      }
    }).attach();
  }
}