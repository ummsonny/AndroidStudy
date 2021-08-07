package org.techtown.mission8;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(editText1.getText().toString().length() < 1 || editText2.getText().toString().length() < 1){
                    Toast.makeText(getApplicationContext(), "비밀번호나 아이디를 입력해주세요", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivityForResult(intent, 101);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        Intent intent;
        if(resultCode==102){
            Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
        }else if(resultCode==103){
            Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
        }else if(resultCode==104){
            Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
        }else if(resultCode==RESULT_OK){
            Toast.makeText(this, "어떤 새끼임? : "+data.getStringExtra("name"),Toast.LENGTH_LONG).show();
        }
    }


}