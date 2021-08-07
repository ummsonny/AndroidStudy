package org.techtown.mission8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button btn1 = findViewById(R.id.button1);
        Button btn2 = findViewById(R.id.button2);
        Button btn3 = findViewById(R.id.button3);
        Button btn10 = findViewById(R.id.button10);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Customer.class);
                startActivityForResult(intent, 102);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sale.class);
                startActivityForResult(intent, 103);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Thing.class);
                startActivityForResult(intent, 104);
            }
        });

        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("name", "메인메뉴!");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        Intent intent;
        if(requestCode==102){//고객관리
            if(resultCode==Customer.BACK_TO_HOME){
                intent = new Intent();
                if(data.getStringExtra("name")==null) {intent.putExtra("name", "고객관리");}
                else{intent.putExtra("name", data.getStringExtra("name"));}
                setResult(102,intent);
                finish();
            }else {
                Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
            }
        }else if(requestCode==103){//매출 관리
            if(resultCode==Customer.BACK_TO_HOME){
                intent = new Intent();
                if(data.getStringExtra("name")==null) {intent.putExtra("name", "매출관리");}
                else{intent.putExtra("name", data.getStringExtra("name"));}
                setResult(103,intent);
                finish();
            }else {
                Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
            }
        }else if(requestCode==104){//상품 관리
            if(resultCode==Customer.BACK_TO_HOME){
                intent = new Intent();
                if(data.getStringExtra("name")==null) {intent.putExtra("name", "상품관리");}
                else{intent.putExtra("name", data.getStringExtra("name"));}
                setResult(104,intent);
                finish();
            }else {
                Toast.makeText(this, "어떤 새끼임 ? : " + data.getStringExtra("name"), Toast.LENGTH_LONG).show();
            }
        }
    }
}