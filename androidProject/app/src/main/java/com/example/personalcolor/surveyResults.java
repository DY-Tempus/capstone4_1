package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
        String[] skinType = new String[3];
        skinType = findSkinType(DO, SR);
        TextView resultText = findViewById(R.id.resultText);


        //화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

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

        // questions 설정 및 레이아웃 매개변수
        int TextViewWidth = (int) (width * 0.9);
        int TextViewHeight = (int) (height * 0.1);
        ConstraintLayout.LayoutParams resultTextParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        resultTextParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        resultTextParams.topToBottom = mainText.getId();
        resultTextParams.leftMargin = dpToPx(16);
        resultTextParams.rightMargin = dpToPx(16);
        resultTextParams.topMargin = dpToPx(8);
        resultText.setLayoutParams(resultTextParams);
        resultText.setText("당신의 피부\n" + skinType[0] + "에 " + skinType[1] + "(" + skinType[2] + ")");



    }

    //피부 타입 찾기
    private String[] findSkinType(float DO, float SR){
        String[] skinType = new String[3];
        String DOSR = null;
        if(DO<=8){
            skinType[0] = "건성";
            DOSR = "D";
        }
        else if(DO<=13){
            skinType[0] = "약간 건성";
            DOSR = "D";
        }
        else if(DO<=16){
            skinType[0] = "약간 지성";
            DOSR = "O";
        }
        else if(DO<=20){
            skinType[0] = "지성";
            DOSR = "O";
        }
        if(SR<=10){
            skinType[1] = "저항성이 강한 피부";
            DOSR += "R";
        }
        else if(SR<=15){
            skinType[1] = "약간 저항성이 있는 피부";
            DOSR += "R";
        }
        else if(SR<=20){
            skinType[1] = "약간 민감피부";
            DOSR += "S";
        }
        else if(SR<=24){
            skinType[1] = "매우 민감피부";
            DOSR += "S";
        }
        skinType[2] = DOSR;
        return skinType;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
