package org.techtown.mission12;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.techtown.mission12.MyService;
import org.techtown.mission12.R;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextTextPersonName);
        textView = findViewById(R.id.textView1);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();

                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.putExtra("msg", text);
                startService(intent);
            }
        });

        Intent intent = getIntent();
        processIntent(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }

    private void processIntent(Intent intent) {
        if(intent!=null){
            String text = intent.getStringExtra("msg");
            textView.setText(text);
        }
    }
}

//정적/동적 리시버 설명 https://overcome26.tistory.com/90 중요!!!!!!!!!!!!!! 책에도 2가지 방법 소개해줬자나
//브로드캐스트 채널의 의미 채널을 등록해야 그 채널로 보내지는 브로드캐스트 메세지를 수신할 수 있다. https://brunch.co.kr/@mystoryg/48