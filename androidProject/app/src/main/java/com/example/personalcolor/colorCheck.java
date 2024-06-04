package com.example.personalcolor;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class colorCheck extends AppCompatActivity {
    private TextView explanation_1;
    private GridLayout buttonGrid;
    private int buttonSize;
    private int buttonMargin = 8; // 버튼 사이의 거리 (dp 단위)
    private String personalColor;
    private TextView colorName;
    private List<HashMap<String, String>> colorList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.color_check);

        Intent intent = getIntent();
        personalColor = intent.getStringExtra("str");  //퍼스널 컬러 종류 이름(ex.봄웜톤/여름쿨톤/가을웜톤/겨울쿨톤)

        // 화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int smallerDimension = Math.min(width, height);

        explanation_1 = findViewById(R.id.explanation_1);
        buttonGrid = findViewById(R.id.button_grid);
        explanation_1.setText(personalColor + "의 16가지\n어울리는 색입니다.");
        colorName = findViewById(R.id.colorName);

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

        //설명 내용
        int textViewWidth = (int) (width * 0.7);
        int textViewHeight = (int) (height * 0.2);
        ConstraintLayout.LayoutParams explanationParams = new ConstraintLayout.LayoutParams(
                textViewWidth,
                textViewHeight
        );
        explanationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        explanationParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        explanationParams.topToBottom = mainText.getId();
        explanationParams.topMargin = dpToPx(8);
        explanation_1.setLayoutParams(explanationParams);

        // buttonGrid 크기 및 위치 설정
        int setDimenstion = (int) (smallerDimension * 0.8);
        ConstraintLayout.LayoutParams gridParams = new ConstraintLayout.LayoutParams(
                setDimenstion,
                setDimenstion
        );
        gridParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.topToBottom = explanation_1.getId();
        gridParams.topMargin = dpToPx(16);
        buttonGrid.setLayoutParams(gridParams);

        //색상 이름 보여주기
        ConstraintLayout.LayoutParams colorNameParams = new ConstraintLayout.LayoutParams(
                textViewWidth,
                textViewHeight
        );
        colorNameParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        colorNameParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        colorNameParams.topToBottom = buttonGrid.getId();
        colorNameParams.topMargin = dpToPx(8);
        colorName.setLayoutParams(colorNameParams);
        colorName.setVisibility(View.INVISIBLE);


        //버튼 행/열 개수
        int numRows = 4;
        int numCols = 4;

        // 버튼 크기 계산 (그리드 크기를 기준으로)
        buttonSize = (setDimenstion - dpToPx(buttonMargin) * (numCols + 4)) / numCols;

        // DB에서 색깔 정보 가져오기
        new LoadColorsTask().execute(personalColor);
    }

    private class LoadColorsTask extends AsyncTask<String, Void, List<HashMap<String, String>>> {
        @Override
        protected List<HashMap<String, String>> doInBackground(String... params) {
            List<HashMap<String, String>> colorData = new ArrayList<>();
            Database db = new Database();
            try {
                ResultSet rs = db.getColorInfo(params[0]);
                while (rs.next()) {
                    HashMap<String, String> colorInfo = new HashMap<>();
                    colorInfo.put("ColorHex", rs.getString("ColorHex"));
                    colorInfo.put("ColorName", rs.getString("ColorName"));
                    colorData.add(colorInfo);
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return colorData;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> result) {
            colorList = result;
            createButtons(4, 4);
        }
    }

    private void createButtons(int numRows, int numCols) {
        int totalButtons = numRows * numCols;

        for (int i = 0; i < totalButtons; i++) {
            Button button = new Button(this);
            button.setId(Button.generateViewId());
            // 버튼 텍스트 제거
            button.setText("");

            // DB에서 가져온 색상 정보로 설정
            String colorHex = colorList.get(i).get("ColorHex");
            String colorNameText = colorList.get(i).get("ColorName");
            button.setBackgroundColor(Color.parseColor(colorHex));

            // 클릭 이벤트 설정
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorName.setText(colorNameText);
                    colorName.setVisibility(View.VISIBLE);
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
