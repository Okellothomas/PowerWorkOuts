package com.pro.powerworkouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;


public class MainActivity extends AppCompatActivity {

    private TextView powerWorkout;
    private TextView sloganText;
    private ImageView powerImage;
    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        powerWorkout = findViewById(R.id.powerWorkout);
        sloganText = findViewById(R.id.sloganText);
        powerImage = findViewById(R.id.powerImage);

        getStartedButton = (Button) findViewById(R.id.getStartedButton);
        getStartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SampleLogin.class);
                startActivity(intent);
            }
        });
    }
}