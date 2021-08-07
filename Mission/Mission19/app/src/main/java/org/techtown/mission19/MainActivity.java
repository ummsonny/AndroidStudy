package org.techtown.mission19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textView2;
    WebView webView;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        textView2 = findViewById(R.id.textView2);

        webView = findViewById(R.id.webView);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editText.getText().toString();
                RequestThread thread = new RequestThread(urlStr);
                thread.start();
            }
        });
    }

    class RequestThread extends Thread{
        String urlStr;

        public RequestThread(String urlStr){
            this.urlStr = urlStr;
        }

        @Override
        public void run() {
            final String output = request(urlStr);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    textView2.setText(output);
                    webView.loadDataWithBaseURL(null ,output, "text/html", "uft-8",null);//책 답지랑 다른 부분 --> https://howtolivelikehuman.tistory.com/56 참고
                }
            });
        }
    }

    private String request(String urlStr) {
        StringBuilder output = new StringBuilder();

        try{
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            if(conn!=null){
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);




                int status = conn.getResponseCode();

                //--------------------여기부터----------------------//
                boolean redirect = false;
                if(status!=HttpURLConnection.HTTP_OK){
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP
                            || status == HttpURLConnection.HTTP_MOVED_PERM
                            || status == HttpURLConnection.HTTP_SEE_OTHER)
                        redirect = true;
                }

                System.out.println("Response Code ... " + status);

                if (redirect) {

                    String newUrl = conn.getHeaderField("Location");
                    String cookies = conn.getHeaderField("Set-Cookie");

                    conn = (HttpURLConnection) new URL(newUrl).openConnection();
                    conn.setRequestProperty("Cookie", cookies);

                    System.out.println("Redirect to URL : " + newUrl);
                }
                //-----------------여기까지는 실습말고 추가된 부분, 정상적으로 응답 안 왓을때 처리부분-----------------//

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while(true){
                    line = reader.readLine();
                    if(line==null){
                        break;
                    }
                    output.append(line+"\n");
                }
                reader.close();
                conn.disconnect();
            }

        }catch (Exception ex){
            Log.e("MainActivity", "Exception in processing response.", ex);
            ex.printStackTrace();
        }
        return output.toString();
    }


}