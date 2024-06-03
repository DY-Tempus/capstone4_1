package com.example.personalcolor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class imageUpload extends AppCompatActivity {

    private ImageView imageView;

    private ActivityResultLauncher<String> mGetContent;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String serverUrl = "http://10.0.2.2:8080/"; // 서버 주소
    private int cheackValue = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.imageupload);

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
        mainTextParams.rightMargin = dpToPx(16);
        mainTextParams.topMargin = dpToPx(69);
        mainText.setLayoutParams(mainTextParams);

        int TextViewWidth = (int) (width * 0.9);
        int TextViewHeight = (int) (height * 0.15);

        // explanation 설정 및 레이아웃 매개변수
        TextView explanation = findViewById(R.id.explanation);
        ConstraintLayout.LayoutParams explanationParams = new ConstraintLayout.LayoutParams(
                TextViewWidth,
                TextViewHeight
        );
        explanationParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;

        explanationParams.topToBottom = mainText.getId();
        explanationParams.leftMargin = dpToPx(16);
        explanationParams.rightMargin = dpToPx(16);
        explanationParams.topMargin = dpToPx(8);
        explanation.setLayoutParams(explanationParams);

        // 화면 크기에 따른 ImageView 및 Button 크기 계산
        int imageViewWidth = (int) (width * 0.9);
        int imageViewHeight = (int) (height * 0.5);
        int buttonWidth = (int) (width * 0.7);
        int buttonHeight = (int) (height * 0.1);

        // faceimage 설정 및 레이아웃 매개변수
        imageView = findViewById(R.id.faceimage);
        ConstraintLayout.LayoutParams imageViewParams = new ConstraintLayout.LayoutParams(
                imageViewWidth,
                imageViewHeight
        );
        imageViewParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        imageViewParams.topToBottom = explanation.getId();
        imageViewParams.topMargin = dpToPx(16);
        imageView.setLayoutParams(imageViewParams);

        // diagnosisButton 설정 및 레이아웃 매개변수
        Button diagnosisButton = findViewById(R.id.diagnosisButton);
        ConstraintLayout.LayoutParams buttonParams = new ConstraintLayout.LayoutParams(
                buttonWidth,
                buttonHeight
        );
        buttonParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        buttonParams.topToBottom = imageView.getId();
        buttonParams.topMargin = dpToPx(40);
        diagnosisButton.setLayoutParams(buttonParams);

        // 이미지 선택을 위한 ActivityResultLauncher 초기화
        mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
            // 선택된 이미지의 URI를 사용하여 ImageView에 이미지를 설정합니다.
            imageView.setImageURI(uri);
        });

        // ImageView 클릭 시 갤러리에서 이미지를 선택할 수 있도록 설정
        imageView.setOnClickListener(v -> mGetContent.launch("image/*"));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // diagnosisButton 버튼에 클릭 리스너 설정
        diagnosisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { uploadImage();}

        });
    }

    // dp를 픽셀로 변환하는 메소드
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void uploadImage() {
        if (cheackValue < 1) {
            showDialog("이미지를 선택하지 않았습니다","이미지를 선택해 주세요!");
            return;
        }
        // ProgressDialog 생성
        ProgressDialog dialog = new ProgressDialog(imageUpload.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("결과를 가져오는 중...");

        dialog.show();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "image.jpg", RequestBody.create(MediaType.parse("image/*"), imageBytes)) // 이미지 파일을 RequestBody로 변환하여 추가
                .build();

        Request request = new Request.Builder()
                .url(serverUrl) // 서버 주소를 설정
                .post(requestBody) // POST 요청으로 설정
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() { // OkHttpClient를 사용하여 비동기적으로 서버에 요청 보냄
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace(); // 요청 실패 시 에러 출력
                runOnUiThread(() -> {
                    dialog.dismiss();
                    showDialog("서버 오류", "서버가 작동하지 않습니다. 나중에 다시 시도해 주세요.");
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string(); // 서버에서 받은 응답 결과를 문자열로 저장

                String newresult = result.replaceAll("[\"\\s]", "");
                int a = 1;
                String str = null;
                if(newresult.equals("winter")){
                    str = "겨울쿨톤";
                }
                else if(newresult.equals("summer")){
                    str = "여름쿨톤";
                }
                else if(newresult.equals("spring")){
                    str = "봄웜톤";
                }
                else if(newresult.equals("fall")){
                    str = "가을웜톤";
                }
                else{
                    runOnUiThread(() -> {
                        dialog.dismiss();
                        showDialog("결과 오류", "얼굴이 제대로 검출되지 않았습니다!");
                    });
                    return;
                }
                dialog.dismiss();
                if(a == 1){
                    // diagnosisResults 액티비티로 이동하는 Intent 생성
                    Intent intent = new Intent(imageUpload.this, diagnosisResults.class);
                    intent.putExtra("str",str);
                    startActivity(intent); // Intent 시작
                }
            }
        });
    }
    void showDialog(String title,String message) {
        AlertDialog.Builder msgBuilder = new AlertDialog.Builder(imageUpload.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("확인", null);
        AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    // 갤러리에서 이미지를 선택한 후 결과를 받아오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cheackValue++;
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData(); // 선택된 이미지의 URI를 가져옴
            imageView.setImageURI(imageUri); // 이미지뷰에 선택된 이미지 표시
        }
    }
}