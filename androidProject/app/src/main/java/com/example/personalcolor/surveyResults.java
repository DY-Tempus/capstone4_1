package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class surveyResults extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.survey_results);

        Intent intent = getIntent();
        float DO = intent.getFloatExtra("DO",0);//피부 건성(D) 지성(O) 확인
        float SR = intent.getFloatExtra("SR",0);//피부 민감성(S) 저항성(R) 확인

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

    }
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
