package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class colorInformation extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.color_information);

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

        int TextViewWidth = (int) (width * 0.9);
        int TextViewHeight = (int) (height * 0.45);

        // information 설정 및 레이아웃 매개변수
        TextView information = findViewById(R.id.information);
        ConstraintLayout.LayoutParams explanationParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        explanationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        explanationParams.topToBottom = mainText.getId();
        explanationParams.leftMargin = dpToPx(16);
        explanationParams.topMargin = dpToPx(16);
        information.setLayoutParams(explanationParams);
        information.setText("다음 안내 사항을 주의해 주세요!\n" +
                            "- 조명이 밝지 않는 곳에서 찍은 사진이 좋아요!\n" +
                            "- 짙은 화장을 한 사진은 안 돼요!\n" +
                            "- 눈썹, 눈, 입술이 가려짐 없이 나와야 해요!\n" +
                            "- 얼굴이 정면을 보도록 찍어 주세요!\n\n\n\n\n" +
                            "다음은 예시 사진 입니다");

        int imageViewWidth = (int) (width * 1);
        int imageViewHeight = (int) (height * 0.2);
        int buttonWidth = (int) (width * 0.8);
        int buttonHeight = (int) (height * 0.1);

        imageView = findViewById(R.id.exampleImage);
        ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(
                imageViewWidth,
                imageViewHeight
        );
        imageViewParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.topToBottom = information.getId();
        imageViewParams.topMargin = dpToPx(16);
        imageView.setLayoutParams(imageViewParams);

        // nextButton 설정 및 레이아웃 매개변수
        Button nextButton = findViewById(R.id.nextButton);
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        buttonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.topToBottom = imageView.getId();
        buttonParams.topMargin = dpToPx(16);
        nextButton.setLayoutParams(buttonParams);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(colorInformation.this, imageUpload.class);
                startActivity(intent); // Intent 시작
            }
        });

    }


    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
