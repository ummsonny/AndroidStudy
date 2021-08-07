package org.techtown.mission20;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static String rssUrl = "https://news.sbs.co.kr/news/sitemapRSS.do";

    ProgressDialog progressDialog;
    Handler handler = new Handler();

    RecyclerView recyclerView;
    RSSNewsAdapter adapter;
    ArrayList<RSSNewsItem> newsItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RSSNewsAdapter();
        adapter.setOnItemClickListener(new OnRSSNewsItemClickListener() {
            @Override
            public void onItemClick(RSSNewsAdapter.ViewHolder holder, View view, int position) {
                Toast.makeText(getApplicationContext(),"haha",Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);


        newsItemList = new ArrayList<RSSNewsItem>();

        final EditText edit01 = findViewById(R.id.edit01);
        edit01.setText(rssUrl);

        Button show_btn = findViewById(R.id.show_btn);
        show_btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String inputStr = edit01.getText().toString();
                showRSS(inputStr);
            }

        });

    }

    private void showRSS(String urlStr) {
        try {
            progressDialog = ProgressDialog.show(this, "RSS Refresh", "RSS 정보 업데이트 중...", true, true);

            RefreshThread thread = new RefreshThread(urlStr);
            thread.start();

        } catch (Exception e) {
            Log.e(TAG, "Error", e);
        }
    }

    class RefreshThread extends Thread {
        String urlStr;

        public RefreshThread(String str) {
            urlStr = str;
        }

        public void run() {

            try {
                URL urlForHttp = new URL(urlStr);
                int countItem=getInputStreamUsingHTTP(urlForHttp);
                Log.d(TAG, countItem + " news item processed.");

                // post for the display of fetched RSS info.
                handler.post(updateRSSRunnable);

            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    public int getInputStreamUsingHTTP(URL url) {
        int count=0;
        try {
            // RSS의 값들을 XmlParser에 연결.
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            BufferedInputStream bis = new BufferedInputStream(url.openStream());
            parser.setInput(bis, null); //InputStream과 String을 인자로 받음.

            // getEventType()으로 문서의 시작과 끝 확인가능
            String tag="";
            boolean isItem = false;

            String title="";
            String link="";
            String description="";
            String pubDate="";
            String author="";
            String category="";

            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) { //문서가 끝날때까지 동작해라.
                switch (parserEvent) {

                    //---------------------------------------------여기부터
                    case XmlPullParser.START_TAG: // TAG 시작. 예시) <title> 태그
                        tag = parser.getName();
                        if(tag.equals("item")){ // news 들만 가져오기 위한 boolean 확인
                            isItem = true;
                        }
                        break;

                    case XmlPullParser.TEXT: // TAG 안의 문자열. 예시) <title> 과 </title> 사이의 문자열
                        if(isItem) {
                            if (tag.equals("title")) { // title TAG 확인
                                title = parser.getText();
                            }
                            if (tag.equals("link")) { // link TAG 확인
                                link = parser.getText();
                            }
                            if (tag.equals("description")) { // description TAG 확인
                                description = parser.getText();
                            }
                            if (tag.equals("pubDate")) { // pubDate TAG 확인
                                pubDate = parser.getText();
                            }
                            if(pubDate.equals("dc:date")){//dc:date TAG 확인
                                pubDate = parser.getText();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG: // TAG 종료. 예시) </title> 태그
                        tag = parser.getName();
                        if(tag.equals("item")){ // news 태그 종료확인
                            newsItemList.add(new RSSNewsItem(title, link,description,pubDate, author, category)); // 한개의 news 를 저장

                            title =""; //다시 모든 값들 초기화.
                            link ="";
                            description ="";
                            pubDate ="";
                            author="";
                            category="";
                            isItem = false;
                        }
                        tag = ""; // tag 값 초기화 하자
                        count++;
                        break;
                    //---------------------------------------------여기까지가 1개의 아이템 추출!
                }

                // 다음 TAG 로 이동
                // 예시) </title>의 다음 Tag 인 <link>로 이동.
                parserEvent = parser.next();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return count;

    }


    Runnable updateRSSRunnable = new Runnable() {
        public void run() {

            try {

                Resources res = getResources();
                Drawable rssIcon = res.getDrawable(R.drawable.rss_icon);
                for (int i = 0; i < newsItemList.size(); i++) {//파싱해서 가져온 객체들이 저장되었는 newsItemList에 있는 애들을 1명씩 꺼내서 어댑터에다가 넣어준다.
                    RSSNewsItem newsItem = newsItemList.get(i);
                    newsItem.setIcon(rssIcon);
                    adapter.addItem(newsItem);
                }

                adapter.notifyDataSetChanged();

                progressDialog.dismiss();
            } catch(Exception ex) {
                ex.printStackTrace();
            }

        }
    };

}