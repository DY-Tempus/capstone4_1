package com.example.personalcolor;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class loginScreen extends AppCompatActivity {

    Database DB = new Database();
    private EditText editID;
    private EditText ediPassword;
    private ExecutorService executorService;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_screen);

        editID = findViewById(R.id.editID);
        ediPassword = findViewById(R.id.ediPassword);

        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        // 화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // blank1 설정 및 레이아웃 매개변수
        int blank1Width = (int) (width * 1.0);
        int blank1Height = (int) (height * 0.2);
        TextView blank1 = findViewById(R.id.blank1);
        ConstraintLayout.LayoutParams blank1Params = new ConstraintLayout.LayoutParams(
                blank1Width,
                blank1Height
        );
        blank1Params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        blank1Params.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        blank1Params.topMargin = dpToPx(8);
        blank1.setLayoutParams(blank1Params);

        // mainText 설정 및 레이아웃 매개변수
        int mainTextWidth = (int) (width * 1.0);
        int mainTextHeight = (int) (height * 0.1);
        TextView mainText = findViewById(R.id.mainText);
        ConstraintLayout.LayoutParams mainTextParams = new ConstraintLayout.LayoutParams(
                mainTextWidth,
                mainTextHeight
        );
        mainTextParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.topToBottom = blank1.getId();
        mainTextParams.topMargin = dpToPx(8);
        mainText.setLayoutParams(mainTextParams);

        // editID 설정 및 레이아웃 매개변수
        int editWidth = (int) (width * 0.7);
        int editHeight = (int) (height * 0.05);
        ConstraintLayout.LayoutParams editIDParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        editIDParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        editIDParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        editIDParams.topToBottom = mainText.getId();
        editIDParams.topMargin = dpToPx(16);
        editID.setLayoutParams(editIDParams);

        // ediPassword 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams ediPasswordParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        ediPasswordParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        ediPasswordParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        ediPasswordParams.topToBottom = editID.getId();
        ediPasswordParams.topMargin = dpToPx(8);
        ediPassword.setLayoutParams(ediPasswordParams);

        // blank2 설정 및 레이아웃 매개변수
        TextView blank2 = findViewById(R.id.blank2);
        ConstraintLayout.LayoutParams blank2Params = new ConstraintLayout.LayoutParams(
                blank1Width,
                blank1Height
        );
        blank2Params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        blank2Params.topToBottom = ediPassword.getId();
        blank2Params.topMargin = dpToPx(8);
        blank2.setLayoutParams(blank2Params);

        // membership 설정 및 레이아웃 매개변수
        int buttonWidth = (int) (width * 0.7);
        int buttonHeight = (int) (height * 0.07);
        Button membership = findViewById(R.id.membership);
        ConstraintLayout.LayoutParams membershipParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        membershipParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        membershipParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        membershipParams.topToBottom = blank2.getId();
        membershipParams.topMargin = dpToPx(16);
        membership.setLayoutParams(membershipParams);

        // find_ID_Password 설정 및 레이아웃 매개변수
        Button find_ID_Password = findViewById(R.id.find_ID_Password);
        ConstraintLayout.LayoutParams find_ID_PasswordParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        find_ID_PasswordParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        find_ID_PasswordParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        find_ID_PasswordParams.topToBottom = membership.getId();
        find_ID_PasswordParams.topMargin = dpToPx(16);
        find_ID_Password.setLayoutParams(find_ID_PasswordParams);

        // login 설정 및 레이아웃 매개변수
        Button login = findViewById(R.id.login);
        ConstraintLayout.LayoutParams loginParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        loginParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        loginParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        loginParams.topToBottom = find_ID_Password.getId();
        loginParams.topMargin = dpToPx(16);
        login.setLayoutParams(loginParams);

        membership.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // joinMembership 액티비티로 이동하는 Intent 생성
                Intent intent = new Intent(loginScreen.this, joinMembership.class);
                startActivity(intent); // Intent 시작
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        boolean result = checkID();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result) {
                                    Intent intent = new Intent(loginScreen.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // 로그인 실패 처리 (필요에 따라 추가)
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    boolean checkID() {
        String ID = editID.getText().toString();    // 아이디 받아오기
        String Password = ediPassword.getText().toString(); // 패스워드 받아오기
        IdPassword user = new IdPassword();
        ResultSet rs = null;
        try {
            rs = DB.findID(ID);
            if (rs != null) {
                while (rs.next()) {
                    String[] information = new String[5];
                    information[0] = rs.getString("ID");
                    information[1]= rs.getString("PW");
                    information[2] = rs.getString("Name1");
                    information[3] = rs.getString("Gender");
                    information[4] = rs.getString("Address");

                    if (ID.equals(information[0]) && Password.equals(information[1])) {
                        user.setIdPassword(information);
                        return true;
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
