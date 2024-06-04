package com.example.personalcolor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class skinDiagnosis extends AppCompatActivity {

    int number = 0;//지금 위치 0-10번까지
    int[] score = new int[11];// 문항별 선택한 번호 기입
    private ProgressBar progressBar;
    private TextView Title;
    private TextView questions;
    private RadioButton questions1;
    private RadioButton questions2;
    private RadioButton questions3;
    private RadioButton questions4;
    private RadioButton questions5;
    private TextView pageNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.skin_diagnosis);

        progressBar = findViewById(R.id.progressBar);// progressBar
        Title = findViewById(R.id.Title);//Title
        questions = findViewById(R.id.questions);//questions

        //라디오버튼 이름
        questions1 = findViewById(R.id.questions1);
        questions2 = findViewById(R.id.questions2);
        questions3 = findViewById(R.id.questions3);
        questions4 = findViewById(R.id.questions4);
        questions5 = findViewById(R.id.questions5);

        //화면 크기 계산
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // mainText 설정 및 레이아웃 매개변수
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

        int progressBarWidth = (int) (width * 0.9);
        int progressBarHeight = (int) (height * 0.05);

        // progressBar 설정 및 레이아웃 매개변수
        ConstraintLayout.LayoutParams progressBarParams = new ConstraintLayout.LayoutParams(
                progressBarWidth,
                progressBarHeight
        );
        progressBarParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        progressBarParams.topToBottom = mainText.getId();
        progressBarParams.leftMargin = dpToPx(16);
        progressBarParams.topMargin = dpToPx(8);
        progressBar.setLayoutParams(progressBarParams);
        progressBar.setProgress(100/11);

        // Title 설정 및 레이아웃 매개변수
        int TextViewWidth = (int) (width * 0.9);
        int TextViewHeight = (int) (height * 0.05);
        ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        titleParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;

        titleParams.topToBottom = progressBar.getId();
        titleParams.leftMargin = dpToPx(16);
        titleParams.rightMargin = dpToPx(16);
        titleParams.topMargin = dpToPx(8);
        Title.setLayoutParams(titleParams);
        Title.setText(getString(R.string.questions_Title1));

        // questions 설정 및 레이아웃 매개변수
        int TextView1Height = (int) (height * 0.12);
        ConstraintLayout.LayoutParams questionsParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextView1Height
        );
        questionsParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        questionsParams.topToBottom = Title.getId();
        questionsParams.leftMargin = dpToPx(16);
        questionsParams.rightMargin = dpToPx(16);
        questionsParams.topMargin = dpToPx(8);
        questions.setLayoutParams(questionsParams);
        questions.setText(getString(R.string.questions1));

        int radioGroupWidth = (int) (width * 0.9);
        int radioGroupHeight = (int) (height * 0.4);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        ConstraintLayout.LayoutParams radioGroupParams = new ConstraintLayout.LayoutParams(
                radioGroupWidth,
                radioGroupHeight
        );
        radioGroupParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        radioGroupParams.topToBottom = questions.getId();
        radioGroupParams.leftMargin = dpToPx(16);
        radioGroupParams.rightMargin = dpToPx(16);
        radioGroupParams.topMargin = dpToPx(8);
        radioGroup.setLayoutParams(radioGroupParams);

        //초기 질문
        questions1.setText(getString(R.string.questions1_1));
        questions2.setText(getString(R.string.questions1_2));
        questions3.setText(getString(R.string.questions1_3));
        questions4.setText(getString(R.string.questions1_4));
        questions5.setVisibility(View.INVISIBLE);//첫문항은 비어있음

        /*
        //이때만 글자 크기 변경
        questions5.setTextSize(Dimension.SP, 17);
        questions5.setText(getString(R.string.questions4_5));
         */

        //이전 위치 다음 linear
        int linearWidth = (int) (width * 0.9);
        int linearHeight = (int) (height * 0.1);
        LinearLayout linear = findViewById(R.id.linear);
        Button before = findViewById(R.id.before);
        pageNumber = findViewById(R.id.pageNumber);
        Button next = findViewById(R.id.next);
        ConstraintLayout.LayoutParams linearParams = new ConstraintLayout.LayoutParams(
                linearWidth,
                linearHeight
        );
        linearParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        linearParams.topToBottom = radioGroup.getId();
        linearParams.leftMargin = dpToPx(16);
        linearParams.rightMargin = dpToPx(16);
        linearParams.topMargin = dpToPx(20);
        linear.setLayoutParams(linearParams);
        pageNumber.setText("1");


        //제출 버튼
        int submitWidth = (int) (width * 0.4);
        int submitHeight = (int) (height * 0.07);
        Button submit = findViewById(R.id.submit);
        ConstraintLayout.LayoutParams submitParams = new ConstraintLayout.LayoutParams(
                submitWidth,
                submitHeight
        );
        submitParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        submitParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        submitParams.topToBottom = linear.getId();
        submitParams.leftMargin = dpToPx(16);
        submitParams.rightMargin = dpToPx(16);
        submitParams.topMargin = dpToPx(8);
        submit.setLayoutParams(submitParams);

        before.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number==0){
                    showDialogMessage("첫번째 페이지 입니다","문항을 선택해 주세요");
                }
                else{
                    setScore();
                    number--;
                    pageChange();
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(number==10){
                    showDialogMessage("마지막 페이지 입니다","제출을 눌러 주세요");
                }
                else{
                    setScore();
                    number++;
                    pageChange();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setScore();
                int check = 0;
                int i = 0;
                for(i = 0 ; i < 11; i++){
                    if(score[i]==0){
                        number = i;
                        break;
                    }
                    else{
                        check++;
                    }
                }
                if(check == 11){
                    float DO=0,SR=0;
                    for(int j = 0;j<5;j++){
                        if(score[j]==5){
                            DO += 2.5;
                        }
                        else{
                            DO += score[j];
                        }
                    }
                    for(int j = 5;j<11;j++){
                        if(score[j]==5){
                            SR += 2.5;
                        }
                        else{
                            SR += score[j];
                        }
                    }
                    // ImageUpload 액티비티로 이동하는 Intent 생성
                    Intent intent = new Intent(skinDiagnosis.this, surveyResults.class);
                    intent.putExtra("DO",DO);
                    intent.putExtra("SR",SR);
                    startActivity(intent); // Intent 시작
                }
                else{
                    showDialogCheck((number+1)+"번째 문항이 체크되지 않았습니다.","체크하지않은 화면 돌아가기");
                }
            }
        });

    }

    void showDialogCheck(String title,String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(skinDiagnosis.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {pageChange();}
                });
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    //첫,마지막 페이지 안내 메시지
    void showDialogMessage(String title,String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(skinDiagnosis.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인",null);
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }

    void pageChange(){

        //progressBar조정
        if(number==10){
            progressBar.setProgress(100);
        }
        else{
            progressBar.setProgress((100/11)*(number+1));
        }

        //pageNumber
        pageNumber.setText(Integer.toString(number+1));

        //Title
        if(number < 5){
            Title.setText(getString(R.string.questions_Title1));
        }
        else{
            Title.setText(getString(R.string.questions_Title2));
        }

        //questions 질문
        setQuestionText(questions,"questions" + (number + 1));
        //문항
        setQuestionText(questions1,"questions" + (number + 1) + "_1");
        setQuestionText(questions2,"questions" + (number + 1) + "_2");
        setQuestionText(questions3,"questions" + (number + 1) + "_3");
        setQuestionText(questions4,"questions" + (number + 1) + "_4");
        //5번문항 비었는지 확인
        String resourceId = "questions" + (number + 1) + "_5";
        int resId = getResources().getIdentifier(resourceId, "string", getPackageName());
        if(getString(resId).equals("No")){  //비어있으면 문항 안보이게
            questions5.setVisibility(View.INVISIBLE);
        }
        else{ //문항 보이게
            questions5.setVisibility(View.VISIBLE);
            setQuestionText(questions5,resourceId);
        }

        if(score[number]!=0){setRadio();}//라디오 버튼 체크 만들기
    }

    //점수 배정
    void setScore(){
            if(questions1.isChecked()){
                score[number] = 1;
            }
            else if(questions2.isChecked()){
                score[number] = 2;
            }
            else if(questions3.isChecked()){
                score[number] = 3;
            }
            else if(questions4.isChecked()){
                score[number] = 4;
            }
            else if(questions5.isChecked()){
                score[number] = 5;
            }
            else{
                score[number] = 0;
            }
        questions1.setChecked(true);
    }

    //라디오 클릭 초기화
    void uncheckRadio() {
        questions1.setChecked(false);
        questions2.setChecked(false);
        questions3.setChecked(false);
        questions4.setChecked(false);
        questions5.setChecked(false);
    }

    //라디오 선택
    void setRadio(){
        if(score[number] == 1){
            questions1.setChecked(true);
        }
        else if(score[number] == 2){
            questions2.setChecked(true);
        }
        else if(score[number] == 3){
            questions3.setChecked(true);
        }
        else if(score[number] == 4){
            questions4.setChecked(true);
        }
        else if(score[number] == 5){
            questions5.setChecked(true);
        }
    }
    void setQuestionText(TextView textView, String resourceId) {
        int resId = getResources().getIdentifier(resourceId, "string", getPackageName());
        if (resId != 0) {
            textView.setText(getString(resId));
        } else {
            textView.setText("Resource not found"); // 리소스를 찾지 못한 경우 처리
        }
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}
