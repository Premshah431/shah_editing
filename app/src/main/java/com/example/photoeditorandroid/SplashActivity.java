package com.example.photoeditorandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    ImageView img;
    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img = findViewById(R.id.imageView);
        tv = findViewById(R.id.textView);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand_in);
        img.startAnimation(animation);
        tv.startAnimation(animation);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(), Select.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        };thread.start();
    }
    }
