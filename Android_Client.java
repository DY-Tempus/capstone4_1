package com.example.myapplication;

import android.graphics.drawable.BitmapDrawable;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button uploadButton;
    private TextView resultTextView;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String serverUrl = "http://10.0.2.2:8080/"; // 서버 주소

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML 레이아웃에서 위젯들을 찾아와서 변수에 할당
        imageView = findViewById(R.id.imageView);
        uploadButton = findViewById(R.id.uploadButton);
        resultTextView = findViewById(R.id.resultTextView);

        // 이미지뷰를 클릭하면 갤러리에서 이미지를 선택할 수 있는 메소드 호출
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        // 업로드 버튼을 클릭하면 이미지를 서버로 업로드하는 메소드 호출
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    // 갤러리에서 이미지를 선택하는 메소드
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    // 선택된 이미지를 서버로 업로드하는 메소드
    private void uploadImage() {
        if (imageView.getDrawable() == null) {
            resultTextView.setText("No image selected.");
            return;
        }

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
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string(); // 서버에서 받은 응답 결과를 문자열로 저장
                runOnUiThread(new Runnable() { // UI 스레드에서 결과를 처리하도록 설정
                    @Override
                    public void run() {
                        resultTextView.setText(result);
                    }
                });
            }
        });
    }

    // 갤러리에서 이미지를 선택한 후 결과를 받아오는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            Uri imageUri = data.getData(); // 선택된 이미지의 URI를 가져옴
            imageView.setImageURI(imageUri); // 이미지뷰에 선택된 이미지 표시
        }
    }
}