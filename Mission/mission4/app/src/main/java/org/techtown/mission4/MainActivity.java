package org.techtown.mission4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editTextTextPersonName);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = editText.getText().toString();
                Toast.makeText(getApplicationContext(), content, Toast.LENGTH_LONG).show();
            }
        });

        Button btn2 = findViewById(R.id.button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                byte[] bytes = null;

                try {
                    bytes = s.toString().getBytes("KSC5601");
                    int len = bytes.length;
                    textView.setText(len+" / 80바이트");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String str = s.toString();
                try {
                    byte[] bytes = str.getBytes("KSC5601");
                    if(bytes.length>80){
                        s.delete(s.length()-2, s.length()-1);//s.delete(3,7)라면 4,5,6,7를 삭제한다. 3은 해당안된다.
                        //Log.d("fuckman", String.valueOf(s.length()));//한글의 한글자가 2바이트이므로 80바이트를 오버한다면 82바이트가 된다. 그래서 s.length()-1,s.length()-1 해서 마지막 한글자만 지워준다.
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        };

        editText.addTextChangedListener(tw);
    }
}