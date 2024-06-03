package com.example.personalcolor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import java.util.Random;

public class colorCheck extends AppCompatActivity {
    private TextView explanation_1;
    private GridLayout buttonGrid;
    private int buttonSize;
    private int buttonMargin = 8; // 버튼 사이의 거리 (dp 단위)

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.color_check);

        // 화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int smallerDimension = Math.min(width, height);

        explanation_1 = findViewById(R.id.explanation_1);
        buttonGrid = findViewById(R.id.button_grid);

        Intent intent = getIntent();
        String str = intent.getStringExtra("str");

        explanation_1.setText(str + "의 16가지\n어울리는 색입니다.");

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

        int textViewWidth = (int) (width * 0.9);
        int textViewHeight = (int) (height * 0.1);
        ConstraintLayout.LayoutParams explanationParams = new ConstraintLayout.LayoutParams(
                textViewWidth,
                textViewHeight
        );
        explanationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        explanationParams.topToBottom = mainText.getId();
        explanationParams.leftMargin = dpToPx(16);
        explanationParams.topMargin = dpToPx(8);
        explanation_1.setLayoutParams(explanationParams);

        // buttonGrid 크기 및 위치 설정
        ConstraintLayout.LayoutParams gridParams = new ConstraintLayout.LayoutParams(
                smallerDimension,
                smallerDimension
        );
        gridParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.topToBottom = explanation_1.getId();
        gridParams.topMargin = dpToPx(16);
        buttonGrid.setLayoutParams(gridParams);

        int numRows = 4;
        int numCols = 4;

        // 버튼 크기 계산 (그리드 크기를 기준으로)
        buttonSize = (smallerDimension - dpToPx(buttonMargin) * (numCols + 4)) / numCols;

        // 4x4 버튼 생성 및 배치
        createButtons(numRows, numCols);
    }

    private void createButtons(int numRows, int numCols) {
        int totalButtons = numRows * numCols;

        Random random = new Random();
        for (int i = 0; i < totalButtons; i++) {
            Button button = new Button(this);
            button.setId(Button.generateViewId());
            // 버튼 텍스트 제거
            button.setText("");

            // 임의의 16진수 색상 코드 설정
            String colorCode = String.format("#%06X", (0xFFFFFF & random.nextInt()));
            // 디비연동후 사용: String colorname = 이름 값
            button.setBackgroundColor(Color.parseColor(colorCode));

            // 클릭 이벤트 설정
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(colorCheck.this, colorCode, Toast.LENGTH_SHORT).show(); // 디비 연동시 colorCode대신 넣기
                }
            });

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = buttonSize;
            params.height = buttonSize;
            params.rowSpec = GridLayout.spec(i / numCols);
            params.columnSpec = GridLayout.spec(i % numCols);
            int marginPx = dpToPx(buttonMargin);
            params.setMargins(marginPx, marginPx, marginPx, marginPx); // 버튼 간격 설정
            buttonGrid.addView(button, params);
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
