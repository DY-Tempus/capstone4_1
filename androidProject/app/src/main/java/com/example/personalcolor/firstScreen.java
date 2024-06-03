package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class firstScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.first_screen);

        // 3초 후에 MainActivity로 이동
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(firstScreen.this, loginScreen.class);
                startActivity(intent);
                finish(); // 현재 액티비티를 종료하여 백스택에 남지 않도록 함
            }
        }, 3000); // 3000ms = 3초
    }
}