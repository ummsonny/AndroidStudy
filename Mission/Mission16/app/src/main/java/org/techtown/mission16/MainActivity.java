package org.techtown.mission16;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    boolean isPageOpen = false;

    LinearLayout page;
    Animation in;
    Animation out;

    EditText editText;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page = findViewById(R.id.page);
        in = AnimationUtils.loadAnimation(this, R.anim.in);
        out = AnimationUtils.loadAnimation(this, R.anim.out);
        SlidingPageAnimationListener listener = new SlidingPageAnimationListener();
        in.setAnimationListener(listener); out.setAnimationListener(listener);

        editText = findViewById(R.id.editText);
        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new ViewClient());

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(editText.getText().toString());

                page.startAnimation(out);

                button2.setVisibility(View.VISIBLE);//'패널 보이기'버튼 보이게하기

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);//키패드 감추기
                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page.startAnimation(in);

                button2.setVisibility(View.INVISIBLE);//'패널 보이기'버튼 사라지게하기

            }
        });
    }

    private class ViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener{//애니메이션 리스너

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if(isPageOpen){
                page.setVisibility(View.INVISIBLE);
                isPageOpen=false;
            }else{
                page.setVisibility(View.VISIBLE);
                isPageOpen=true;
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}