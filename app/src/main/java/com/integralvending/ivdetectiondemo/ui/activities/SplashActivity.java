package com.integralvending.ivdetectiondemo.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.AlphaAnimation;

import com.integralvending.ivdetectiondemo.databinding.ActivitySplashBinding;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ExecutorService bgExecutor = Executors.newSingleThreadExecutor();
        Handler uiHandler = new Handler(Looper.getMainLooper());

        AlphaAnimation animation = new AlphaAnimation(0.2f, 1.0f);
        animation.setDuration(1500);
        animation.setFillAfter(true);
        binding.ivLogo.startAnimation(animation);

        bgExecutor.execute(() -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            uiHandler.post(() -> startActivity(intent));
        });

        bgExecutor.shutdown();
    }
}