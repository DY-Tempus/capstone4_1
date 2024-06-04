package com.example.personalcolor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.gridlayout.widget.GridLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.InputStream;

public class personalColorCody extends AppCompatActivity {
    Database DB = new Database();
    private int buttonSize;
    private GridLayout buttonGrid;
    private ImageView codyImage;
    private TextView codyPrice;
    private TextView codyTotalAmount;
    private int buttonMargin = 8; // 버튼 사이의 거리 (dp 단위)
    private String personalColor;
    private String siteLink; // 사이트 링크를 저장할 변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.personal_color_cody);

        Intent intent = getIntent();
        personalColor = intent.getStringExtra("str");  //퍼스널 컬러 종류 이름(ex.봄웜톤/여름쿨톤/가을웜톤/겨울쿨톤)

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

        int buttonGridWidth = (int) (width * 0.9);
        int buttonGridHeight = (int) (height * 0.1);

        buttonGrid = findViewById(R.id.button_grid);
        // buttonGrid 크기 및 위치 설정
        ConstraintLayout.LayoutParams gridParams = new ConstraintLayout.LayoutParams(
                buttonGridWidth,
                buttonGridHeight
        );
        gridParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        gridParams.topToBottom = mainText.getId();
        gridParams.topMargin = dpToPx(16);

        buttonGrid.setLayoutParams(gridParams);
        int numRows = 1;
        int numCols = 3;

        // 버튼 크기 계산 (그리드 크기를 기준으로)
        buttonSize = (buttonGridHeight - dpToPx(buttonMargin) * (numRows + 5)) / numRows;

        // 1x3 버튼 생성 및 배치
        createButtons(numRows, numCols);

        // 크기 설정
        int imageViewWidth = (int) (width * 1);
        int imageViewHeight = (int) (height * 0.45);
        int priceLayoutWidth = (int) (width * 0.9);
        int priceLayoutHeight = (int) (height * 0.32);

        // codyImage 설정 및 레이아웃 매개변수
        codyImage = findViewById(R.id.codyImage);
        ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(
                imageViewWidth,
                imageViewHeight
        );
        imageViewParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.topToBottom = buttonGrid.getId();
        codyImage.setLayoutParams(imageViewParams);
        codyImage.setVisibility(View.INVISIBLE);

        // priceLayout 설정 및 레이아웃 매개변수
        LinearLayout priceLayout = findViewById(R.id.priceLayout);
        ConstraintLayout.LayoutParams LinearLayout1 = new ConstraintLayout.LayoutParams(
                priceLayoutWidth,
                priceLayoutHeight
        );
        LinearLayout1.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        LinearLayout1.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        LinearLayout1.topToBottom = codyImage.getId();
        LinearLayout1.topMargin = dpToPx(16);
        priceLayout.setLayoutParams(LinearLayout1);

        // codyPrice, codyTotalAmount 텍스트뷰 설정
        codyPrice = findViewById(R.id.codyPrice);
        codyTotalAmount = findViewById(R.id.codyTotalAmount);

        LinearLayout.LayoutParams priceParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        priceParams.setMargins(dpToPx(8), 0, dpToPx(8), 0);

        codyPrice.setLayoutParams(priceParams);
        codyPrice.setVisibility(View.INVISIBLE);
        codyTotalAmount.setLayoutParams(priceParams);
        codyTotalAmount.setVisibility(View.INVISIBLE);

        // 이미지 클릭 이벤트 설정
        codyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (siteLink != null && !siteLink.isEmpty()) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(siteLink));
                    startActivity(browserIntent);
                }
            }
        });
    }

    private void createButtons(int numRows, int numCols) {
        int totalButtons = numRows * numCols;
        for (int i = 0; i < totalButtons; i++) {
            Button button = new Button(this);
            button.setId(Button.generateViewId());
            // 버튼 텍스트 제거
            button.setText("");

            // 버튼 색상 모양 만들기
            if (i == 0) {
                // cody1번 사진
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button));
            } else if (i == 1) {
                // cody2번 사진
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button1));
            } else if (i == 2) {
                // cody3번 사진
                button.setBackground(ContextCompat.getDrawable(this, R.drawable.rounded_button2));
            }

            // 각 버튼에 대응하는 이미지 리소스 ID 설정 = 코디별 이미지 여기서 정하기
            final int buttonIndex = i; // for use in the inner class

            // 클릭 이벤트 설정
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 숨겼던 이미지, 텍스트뷰 보이게 하기
                    codyImage.setVisibility(View.VISIBLE);
                    codyPrice.setVisibility(View.VISIBLE);
                    codyTotalAmount.setVisibility(View.VISIBLE);
                    // AsyncTask 실행
                    new LoadImageTask().execute(String.valueOf(buttonIndex + 1)); // CosmeticID를 전달
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

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        private String totalAmount;
        private String codyInformation;
        private String price;
        @Override
        protected Bitmap doInBackground(String... params) {
            String ClothesID = params[0];
            Bitmap image = null;
            IdPassword user = new IdPassword();
            String Gender = user.getGender();
            int num = Integer.parseInt(ClothesID);
            int total = 0;
            if(personalColor.equals("봄웜톤")){    //봄웜톤 +0

            }
            else if(personalColor.equals("여름쿨톤")){//여름쿨톤 +3
                num += 3;
            }
            else if(personalColor.equals("가을웜톤")){//가을웜톤 +6
                num += 6;
            }
            else if(personalColor.equals("겨울쿨톤")){//겨울쿨톤 +9
                num += 9;
            }

            if(Gender.equals("여자")){ //여자 +0

            }
            else if(Gender.equals("남자")){//남자 +12
                num += 12;
            }
            ClothesID = Integer.toString(num);
            try {
                ResultSet rs = DB.findImage(ClothesID);
                if (rs.next()) {
                    InputStream inputStream = rs.getBinaryStream("ClothesImage");

                    //Total 계산
                    for(int i = 1 ; i < 6 ; i++){
                        price = rs.getString("Price"+i);
                        if(price != null && !price.isEmpty()){
                            int itemPrice = Integer.parseInt(price);
                            total += itemPrice;
                        }
                    }

                    //codyInformation 제작
                    String checkNull;
                    codyInformation = rs.getString("ClothesName1") + ": " + rs.getString("Price1") + "\n";//첫번째는 받아옴
                    for(int i = 2 ; i < 6 ; i++){
                        checkNull = rs.getString("ClothesName"+i);
                        if(checkNull != null && !checkNull.isEmpty()){
                            codyInformation += rs.getString("ClothesName"+i) + ": " + rs.getString("Price"+i) + "\n";
                        }
                        else{
                            break;
                        }
                    }
                    totalAmount = "총가격\n" + total; // 총합 나타내는거
                    image = BitmapFactory.decodeStream(inputStream);
                    siteLink = rs.getString("SiteLink"); // 사이트 링크 가져오기
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (DB.con != null) {
                        DB.con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                codyImage.setImageBitmap(result);
                codyPrice.setText(codyInformation);
                codyTotalAmount.setText(totalAmount);
            }
        }
    }
}
