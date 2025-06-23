package com.example.internshipjune;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class SplashActivity extends AppCompatActivity {

    ImageView image;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        image = findViewById(R.id.splash_image);

        Glide.with(this).asGif()
                .load("https://i.pinimg.com/originals/fe/a9/2f/fea92f50aa6db93e3a5e9ae9aa27b2a7.gif")
                .placeholder(R.mipmap.ic_launcher)
                .into(image);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sp.getString(ConstantSp.name,"").equals("")){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else{
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }


            }
        }, 3000);



    }
}