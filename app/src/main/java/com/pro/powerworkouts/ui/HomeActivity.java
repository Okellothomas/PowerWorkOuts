package com.pro.powerworkouts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.pro.powerworkouts.R;
import com.pro.powerworkouts.SplashScreen;
import com.pro.powerworkouts.adapter.HomeViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {
  @BindView(R.id.view_pager)
  ViewPager2 viewPager;
  @BindView(R.id.tab_layout)
  TabLayout tabLayout;

  private FragmentStateAdapter viewPagerAdapter;
  private FirebaseAuth auth;
  private FirebaseAuth.AuthStateListener authListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);

    ButterKnife.bind(this);

    viewPagerAdapter = new HomeViewPagerAdapter(this);
    viewPager.setAdapter(viewPagerAdapter);
    auth = FirebaseAuth.getInstance();

    initializeTabLayout();
    initializeAuthListener();
  }

  private void initializeAuthListener(){
    authListener = firebaseAuth -> {
      if(firebaseAuth.getCurrentUser() == null){
        redirectToSplashScreen();
      }
    };
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

  private void redirectToSplashScreen(){
    Intent intent = new Intent(this, SplashScreen.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
  }

  @Override
  protected void onStart() {
    super.onStart();
    auth.addAuthStateListener(authListener);
  }

  @Override
  protected void onStop() {
    super.onStop();
    if(authListener != null){
      auth.removeAuthStateListener(authListener);
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.app_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    if (item.getItemId() == R.id.action_logout){
      FirebaseAuth.getInstance().signOut();
    }
    return super.onOptionsItemSelected(item);
  }
}