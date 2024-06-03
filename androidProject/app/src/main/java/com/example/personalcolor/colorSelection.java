package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class colorSelection extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.color_selection);

        // mainText 좌측 상단 고정
        TextView mainText = findViewById(R.id.mainText);
        ConstraintLayout.LayoutParams mainTextParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        mainTextParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.leftMargin = dpToPx(16);
        mainTextParams.topMargin = dpToPx(69);
        mainText.setLayoutParams(mainTextParams);

        // 버튼 찾기
        Button springWarmButton = findViewById(R.id.springWarmButton);
        Button summerCoolButton = findViewById(R.id.summerCoolButton);
        Button fallWarmButton = findViewById(R.id.fallWarmButton);
        Button winterCoolButton = findViewById(R.id.winterCoolButton);

        // 화면 크기 가져오기
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // 버튼의 크기 설정
        int buttonWidth = (int)(width * 0.90); // 너비는 화면 너비의 90%
        int buttonHeight = height / 5; // 높이는 화면 높이의 1/5

        // 각 버튼에 대한 LayoutParams 설정
        ConstraintLayout.LayoutParams springWarmButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight);
        springWarmButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        springWarmButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        springWarmButtonParams.topToBottom = R.id.mainText;
        springWarmButtonParams.topMargin = dpToPx(16); // 상단 마진
        springWarmButton.setLayoutParams(springWarmButtonParams);

        ConstraintLayout.LayoutParams summerCoolButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight);
        summerCoolButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        summerCoolButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        summerCoolButtonParams.topToBottom = R.id.springWarmButton;
        summerCoolButtonParams.topMargin = dpToPx(16); // 이전 버튼과의 마진
        summerCoolButton.setLayoutParams(summerCoolButtonParams);

        ConstraintLayout.LayoutParams fallWarmButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight);
        fallWarmButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        fallWarmButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        fallWarmButtonParams.topToBottom = R.id.summerCoolButton;
        fallWarmButtonParams.topMargin = dpToPx(16); // 이전 버튼과의 마진
        fallWarmButton.setLayoutParams(fallWarmButtonParams);

        ConstraintLayout.LayoutParams winterCoolButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, buttonHeight);
        winterCoolButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        winterCoolButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        winterCoolButtonParams.topToBottom = R.id.fallWarmButton;
        winterCoolButtonParams.topMargin = dpToPx(16); // 이전 버튼과의 마진
        winterCoolButton.setLayoutParams(winterCoolButtonParams);

        springWarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(colorSelection.this, personalColorCody.class);
                String str = "봄웜톤";
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });

        summerCoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(colorSelection.this, personalColorCody.class);
                String str = "여름쿨톤";
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });

        fallWarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(colorSelection.this, personalColorCody.class);
                String str = "가을웜톤";
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });

        winterCoolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(colorSelection.this, personalColorCody.class);
                String str = "겨울쿨톤";
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });

    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
}