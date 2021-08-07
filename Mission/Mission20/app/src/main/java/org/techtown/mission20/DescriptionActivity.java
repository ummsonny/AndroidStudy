package org.techtown.mission20;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class DescriptionActivity extends AppCompatActivity{

    TextView textView;
    //https://gg-i-dont-know.tistory.com/6 액티비티를 다이얼로그처럼 보이게하기
    //다이얼로그 모양 바꾸기 https://apt-info.github.io/%EA%B0%9C%EB%B0%9C/android-layout-corner/
    //activity_description의 textview의 마진을 주어서 딱 안에 들어오게 하였다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Des");
        textView.setText(data);
    }
}