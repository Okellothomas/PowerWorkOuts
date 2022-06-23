package com.pro.powerworkouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.pro.powerworkouts.ui.SignUp;

import com.airbnb.lottie.LottieAnimationView;
import com.pro.powerworkouts.ui.HomeActivity;

public class SplashScreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView powerWorkout;
    TextView sloganText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        sloganText = findViewById(R.id.sloganText);
        powerWorkout = findViewById(R.id.powerWorkout);
        lottie = findViewById(R.id.lottie);


        sloganText.animate().translationX(-1400).setDuration(2000).setStartDelay(3000);
        powerWorkout.animate().translationX(-1400).setDuration(2000).setStartDelay(3000);
        lottie.animate().translationX(2000).setDuration(2500).setStartDelay(3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),SignUp.class);
                startActivity(i);
            }
        },5500);
    }
}