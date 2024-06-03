package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // mainText 좌측 상단 고정
        TextView mainText = findViewById(R.id.mainText);
        ConstraintLayout.LayoutParams mainTextParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        );
        mainTextParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.leftMargin = dpToPx(16);
        mainTextParams.topMargin = dpToPx(16);
        mainText.setLayoutParams(mainTextParams);

        //화면 크기에 따른 버튼 크기 변경
        Button personalColorTest = findViewById(R.id.personalColorTest);
        Button personalColorCody = findViewById(R.id.personalColorCody);
        Button cosmeticRecommendations = findViewById(R.id.cosmeticRecommendations);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // 첫 번째 버튼의 크기를 화면 크기의 일정 비율로 설정
        int buttonWidth = (int) (width * 0.90); // 모든 버튼의 너비는 화면 너비의 90%
        int firstButtonHeight = height / 3; // 첫 번째 버튼의 높이는 화면 높이의 1/3
        int otherButtonHeight = height / 4; // 두 번째, 세 번째 버튼의 높이는 화면 높이의 1/4

        // 첫 번째 버튼에 대한 LayoutParams 설정
        ConstraintLayout.LayoutParams firstButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, firstButtonHeight);
        firstButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        firstButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        firstButtonParams.topToBottom = R.id.mainText;
        firstButtonParams.topMargin = dpToPx(16); // mainText와의 마진
        personalColorTest.setLayoutParams(firstButtonParams);

        // 두 번째 버튼에 대한 LayoutParams 설정
        ConstraintLayout.LayoutParams secondButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, otherButtonHeight);
        secondButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        secondButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        secondButtonParams.topToBottom = R.id.personalColorTest;
        secondButtonParams.topMargin = dpToPx(16); // 첫 번째 버튼과의 마진
        personalColorCody.setLayoutParams(secondButtonParams);

        // 세 번째 버튼에 대한 LayoutParams 설정
        ConstraintLayout.LayoutParams thirdButtonParams = new ConstraintLayout.LayoutParams(buttonWidth, otherButtonHeight);
        thirdButtonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        thirdButtonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        thirdButtonParams.topToBottom = R.id.personalColorCody;
        thirdButtonParams.topMargin = dpToPx(16); // 두 번째 버튼과의 마진
        cosmeticRecommendations.setLayoutParams(thirdButtonParams);

        personalColorTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ImageUpload 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(MainActivity.this, colorInformation.class);
                startActivity(intent); // Intent 시작
            }
        });

        personalColorCody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ImageUpload 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(MainActivity.this, colorSelection.class);
                startActivity(intent); // Intent 시작
            }
        });

        cosmeticRecommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ImageUpload 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(MainActivity.this, skinDiagnosis.class);
                startActivity(intent); // Intent 시작
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}