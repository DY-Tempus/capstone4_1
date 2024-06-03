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

public class diagnosisResults extends AppCompatActivity {

    private TextView result;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.diagnosis_results);

        //화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        result = findViewById(R.id.result);

        Button codyButton = findViewById(R.id.codyButton);

        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

        result.setText("당신의 퍼스널 컬러는\n"+ str+"입니다!");

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
        int TextViewHeight = (int) (height * 0.1);

        // explanation 설정 및 레이아웃 매개변수
        TextView explanation = findViewById(R.id.result);
        ConstraintLayout.LayoutParams explanationParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        explanationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        explanationParams.topToBottom = mainText.getId();
        explanationParams.leftMargin = dpToPx(16);
        explanationParams.topMargin = dpToPx(8);
        explanation.setLayoutParams(explanationParams);

        // 화면 크기에 따른 ImageView 및 Button 크기 계산
        int imageViewWidth = (int) (width * 1);
        int imageViewHeight = (int) (height * 0.5);
        int buttonWidth = (int) (width * 0.9);
        int buttonHeight = (int) (height * 0.1);

        // imageView 설정 및 레이아웃 매개변수
        imageView = findViewById(R.id.imageView);
        ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(
                imageViewWidth,
                imageViewHeight
        );
        imageViewParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.topToBottom = explanation.getId();
        imageViewParams.topMargin = dpToPx(16);
        imageView.setLayoutParams(imageViewParams);

        //퍼스널 컬러에 따른 설명 이미지뷰 설정
        imageChoose(str);

        // colorButton 설정 및 레이아웃 매개변수
        Button colorButton = findViewById(R.id.colorButton);
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        buttonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.topToBottom = imageView.getId();
        buttonParams.topMargin = dpToPx(16);
        colorButton.setLayoutParams(buttonParams);

        // codyButton 설정 및 레이아웃 매개변수
        codyButton = findViewById(R.id.codyButton);
        ConstraintLayout.LayoutParams buttonParams1 = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        buttonParams1.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams1.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams1.topToBottom = colorButton.getId();
        buttonParams1.topMargin = dpToPx(16);
        codyButton.setLayoutParams(buttonParams1);

        // codyButton 버튼에 클릭 리스너 설정
        codyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(diagnosisResults.this, personalColorCody.class);
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });

        // colorButton 버튼에 클릭 리스너 설정
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // personalColorCody 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(diagnosisResults.this, colorCheck.class);
                intent.putExtra("str",str);
                startActivity(intent); // Intent 시작
            }
        });
    }

    private void imageChoose(String str){   //이미지 뷰 이미지 선택 함수
        if(str.equals("봄웜톤")){
            imageView.setImageResource(R.drawable.springwarm3);
        }
        else if(str.equals("여름쿨톤")){
            imageView.setImageResource(R.drawable.summercool3);
        }
        else if(str.equals("가을웜톤")){
            imageView.setImageResource(R.drawable.fallwarm3);
        }
        else if(str.equals("겨울쿨톤")){
            imageView.setImageResource(R.drawable.wintercool3);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
