package org.techtown.mission17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean running = false;

    LinearLayout page1;
    LinearLayout page2;
    TextView textView2;

    Animation in;
    Animation out;

    int whatPage = 1;

    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        page1 = findViewById(R.id.page1);
        page2 = findViewById(R.id.page2);
        textView2 = findViewById(R.id.textView2);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimThread thread = new AnimThread();
                thread.start();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });

        SlidingPageListener listener = new SlidingPageListener();
        in = AnimationUtils.loadAnimation(this, R.anim.in);
        out = AnimationUtils.loadAnimation(this, R.anim.out);





    }

    //스레드
    class AnimThread extends Thread{

        @Override
        public void run() {
            running = true;

            while(running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (whatPage == 1) {
                            page1.startAnimation(out);//#anim파일의 xml의 fillAfter속성때문에 이놈은 setVisibility()안씀
                            page2.setVisibility(View.VISIBLE);
                            page2.startAnimation(in);
                            textView2.setText("2/2");
                        } else {
                            page1.setVisibility(View.VISIBLE);
                            page1.startAnimation(in);
                            page2.startAnimation(out);//#anim파일의 xml의 fillAfter속성때문에 이놈은 setVisibility()안씀
                            textView2.setText("1/2");
                        }

                    }
                });

                whatPage++;
                if(whatPage>2){
                    whatPage=1;
                }

                Log.d("Main","hahaha0");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //애니 리스너 : 스레드가 있어서 리스너나 애니매이션이 포함하고 있는 시간차가 살짝 결과를 애매하기 틀리게 만듬....

    class SlidingPageListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.d("Main", "애니끝남 시작!");
            if(whatPage==1){
                Log.d("Main", "첫번 째 시작!");
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.VISIBLE);
                textView2.setText("2/2");
                whatPage=2;
            }else{
                Log.d("Main", "두번 째 시작!");
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.INVISIBLE);
                textView2.setText("1/2");
                whatPage=1;
            }


        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}