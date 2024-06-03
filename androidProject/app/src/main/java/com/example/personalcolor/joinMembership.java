package com.example.personalcolor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class joinMembership extends AppCompatActivity {
    Database DB = new Database();
    private EditText name;
    private EditText editID;
    private EditText ediPassword;
    private EditText checkEdiPassword;
    private EditText address;
    private Button boy;
    private Button girl;
    private Button join;
    private TextView checkID_Text;

    private Button selectedButton = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.join_membership);

        name = findViewById(R.id.name);
        editID = findViewById(R.id.editID);
        ediPassword = findViewById(R.id.ediPassword);
        checkEdiPassword = findViewById(R.id.checkEdiPassword);
        address = findViewById(R.id.address);

        boy = findViewById(R.id.boy);
        girl = findViewById(R.id.girl);
        join = findViewById(R.id.join);

        //화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // mainText 설정 및 레이아웃 매개변수
        int mainTextWidth = (int) (width * 1.0);
        int mainTextHeight = (int) (height * 0.05);
        TextView mainText = findViewById(R.id.mainText);
        ConstraintLayout.LayoutParams mainTextParams = new ConstraintLayout.LayoutParams(
                mainTextWidth,
                mainTextHeight
        );
        mainTextParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        mainTextParams.topMargin = dpToPx(50);
        mainText.setLayoutParams(mainTextParams);

        int TextViewWidth = (int) (width * 0.7);
        int TextViewHeight = (int) (height * 0.12);
        TextView Information = findViewById(R.id.Information);
        ConstraintLayout.LayoutParams InformationParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        InformationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        InformationParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        InformationParams.topToBottom = mainText.getId();
        InformationParams.topMargin = dpToPx(20);
        Information.setLayoutParams(InformationParams);
        Information.setText("·아이디는 7자리 이상 영어 숫자를 포함해주세요\n" +
                "·비밀번호는 8자리 이상 영어, 숫자, 특수문자를 포함해주세요");

        // name 설정 및 레이아웃 매개변수
        int editWidth = (int) (width * 0.7);
        int editHeight = (int) (height * 0.05);
        ConstraintLayout.LayoutParams nameParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        nameParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        nameParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        nameParams.topToBottom = Information.getId();
        nameParams.topMargin = dpToPx(20);
        name.setLayoutParams(nameParams);

        // editID 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams editIDParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        editIDParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        editIDParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        editIDParams.topToBottom = name.getId();
        editIDParams.topMargin = dpToPx(20);
        editID.setLayoutParams(editIDParams);

        //중복확인 linear
        int linearWidth = (int) (width * 0.7);
        int linearHeight = (int) (height * 0.07);
        LinearLayout linear = findViewById(R.id.linear);
        Button checkID = findViewById(R.id.checkID);
        checkID_Text = findViewById(R.id.checkID_Text);
        ConstraintLayout.LayoutParams linearParams = new ConstraintLayout.LayoutParams(
                linearWidth,
                linearHeight
        );
        linearParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        linearParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        linearParams.topToBottom = editID.getId();
        linearParams.leftMargin = dpToPx(16);
        linearParams.rightMargin = dpToPx(16);
        linearParams.topMargin = dpToPx(20);
        linear.setLayoutParams(linearParams);
        checkID_Text.setVisibility(View.INVISIBLE);

        // ediPassword 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams ediPasswordParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        ediPasswordParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        ediPasswordParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        ediPasswordParams.topToBottom = linear.getId();
        ediPasswordParams.topMargin = dpToPx(20);
        ediPassword.setLayoutParams(ediPasswordParams);

        // ediPassword 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams checkEdiPasswordParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        checkEdiPasswordParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        checkEdiPasswordParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        checkEdiPasswordParams.topToBottom = ediPassword.getId();
        checkEdiPasswordParams.topMargin = dpToPx(20);
        checkEdiPassword.setLayoutParams(checkEdiPasswordParams);

        //이전 위치 다음 linear
        LinearLayout gender = findViewById(R.id.gender);
        Button boy = findViewById(R.id.boy);
        Button girl = findViewById(R.id.girl);
        ConstraintLayout.LayoutParams genderParams = new ConstraintLayout.LayoutParams(
                linearWidth,
                linearHeight
        );
        genderParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        genderParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        genderParams.topToBottom = checkEdiPassword.getId();
        genderParams.leftMargin = dpToPx(16);
        genderParams.rightMargin = dpToPx(16);
        genderParams.topMargin = dpToPx(20);
        gender.setLayoutParams(genderParams);

        // ediPassword 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams addressParams = new ConstraintLayout.LayoutParams(
                editWidth,
                editHeight
        );
        addressParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        addressParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        addressParams.topToBottom = gender.getId();
        addressParams.topMargin = dpToPx(20);
        address.setLayoutParams(addressParams);

        // join 설정 및 레이아웃 매개변수
        int joinWidth = (int) (width * 0.7);
        int joinHeight = (int) (height * 0.07);
        ConstraintLayout.LayoutParams joinParams = new ConstraintLayout.LayoutParams(
                joinWidth,
                joinHeight
        );
        joinParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        joinParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        joinParams.topToBottom = address.getId();
        joinParams.topMargin = dpToPx(60);
        join.setLayoutParams(joinParams);

        checkID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디 중복 확인
                new CheckIDTask().execute(editID.getText().toString());
            }
        });

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button clickedButton = (Button) v;

                // 선택된 버튼을 클릭하면 선택 상태를 토글
                if (selectedButton != null) {
                    selectedButton.setSelected(false);
                }

                // 클릭된 버튼이 선택 상태가 아니면 선택 상태로 변경
                if (selectedButton != clickedButton) {
                    clickedButton.setSelected(true);
                    selectedButton = clickedButton;
                } else {
                    selectedButton = null; // 아무것도 선택되지 않은 상태로 설정
                }
            }
        };

        boy.setOnClickListener(buttonClickListener);
        girl.setOnClickListener(buttonClickListener);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPassword()) {
                    new JoinMembershipTask().execute();
                }
            }
        });
    }

    private class CheckIDTask extends AsyncTask<String, Void, Boolean> {
        private String errorMessage = null;

        @Override
        protected Boolean doInBackground(String... params) {
            String ID = params[0];
            ResultSet rs = null;

            // 7자리 이상
            if (ID.length() < 7) {
                errorMessage = "7자리를 넘지 않습니다";
                return false;
            }

            // 조건
            Pattern letter = Pattern.compile("[a-zA-Z]");   //영문자 포함
            Pattern digit = Pattern.compile("[0-9]");   // 숫자 포함

            if (!letter.matcher(ID).find()) {
                errorMessage = "영문자가 포함되지 않았습니다";
                return false;
            }

            if (!digit.matcher(ID).find()) {
                errorMessage = "숫자가 포함되지 않았습니다";
                return false;
            }

            try {
                rs = DB.findID(ID);
                if (rs != null) {
                    while (rs.next()) {
                        String a = rs.getString("ID");
                        if (ID.equals(a)) {
                            errorMessage = "이미 존재하는 아이디입니다";
                            return false;
                        }
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                errorMessage = "데이터베이스 오류";
                return false;
            } finally {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            checkID_Text.setVisibility(View.VISIBLE);
            if (result) {
                checkID_Text.setText("사용 가능한 아이디");
            } else {
                checkID_Text.setText(errorMessage);
            }
        }
    }

    private class JoinMembershipTask extends AsyncTask<Void, Void, Boolean> {
        private String errorMessage = null;

        @Override
        protected Boolean doInBackground(Void... params) {
            String ID = editID.getText().toString();
            String Password = ediPassword.getText().toString();
            String CheckPassword = checkEdiPassword.getText().toString();
            String Name = name.getText().toString();
            String Address = address.getText().toString();

            if (!checkIDLocal(ID)) {
                errorMessage = "아이디가 정확히 입력되지 않았습니다.";
                return false;
            }

            if (selectedButton == null) {
                errorMessage = "성별 선택은 필수적으로 진행해야 합니다.";
                return false;
            }

            if (!Password.equals(CheckPassword)) {
                errorMessage = "입력한 비밀번호와 중복확인이 같지 않습니다.";
                return false;
            }

            if (TextUtils.isEmpty(Address)) {
                errorMessage = "주소 입력은 필수적으로 진행해야 합니다.";
                return false;
            }

            String selectedText = selectedButton.getText().toString();
            String[] userInformation = new String[5];
            userInformation[0] = ID;
            userInformation[1] = Password;
            userInformation[2] = Name;
            userInformation[3] = selectedText;
            userInformation[4] = Address;

            try {
                DB.insertUser(userInformation);
                return true;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                errorMessage = "회원가입에 실패했습니다. 다시 시도해주세요.";
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                goMainDialogMessage("회원가입 성공!", "회원가입이 정상적으로 성공했습니다!");
            } else {
                showDialogMessage("회원가입 실패", errorMessage);
            }
        }

        private boolean checkIDLocal(String ID) {
            if (ID.length() < 7) {
                return false;
            }

            Pattern letter = Pattern.compile("[a-zA-Z]");
            Pattern digit = Pattern.compile("[0-9]");

            if (!letter.matcher(ID).find() || !digit.matcher(ID).find()) {
                return false;
            }
            return true;
        }
    }

    boolean checkPassword() {
        String Password = ediPassword.getText().toString();
        // 8자리 이상
        if (Password.length() < 8) {
            showDialogMessage("비밀번호 입력", "8자리를 넘지 않습니다");
            return false;
        }

        // 조건
        Pattern letter = Pattern.compile("[a-zA-Z]");   //영문자 포함
        Pattern digit = Pattern.compile("[0-9]");   // 숫자 포함
        Pattern special = Pattern.compile("[^a-zA-Z0-9]");  //특수문자 포함

        if (!letter.matcher(Password).find()) {
            showDialogMessage("비밀번호 입력", "영문자가 포함되지 않았습니다");
            return false;
        }

        if (!digit.matcher(Password).find()) {
            showDialogMessage("비밀번호 입력", "숫자가 포함되지 않았습니다");
            return false;
        }

        if (!special.matcher(Password).find()) {
            showDialogMessage("비밀번호 입력", "특수문자가 포함되지 않았습니다");
            return false;
        }
        return true;
    }

    //메시지 전달
    void showDialogMessage(String title, String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(joinMembership.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", null);
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    void goMainDialogMessage(String title, String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(joinMembership.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(joinMembership.this, loginScreen.class);
                        startActivity(intent); // Intent 시작
                    }
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
