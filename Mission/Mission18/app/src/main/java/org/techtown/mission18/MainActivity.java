package org.techtown.mission18;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    LinearLayout container;
    Animation translateIn;

    PictureItemView view;
    PictureItemView view2;

    TextView textView;

    int selected = 0;

    boolean running = false;

    Handler handler = new Handler();

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    int pictureCount = 0;

    ArrayList<ImageInfo> pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        textView = findViewById(R.id.textView);

        view = new PictureItemView(this);
        view.setName("사진이름");
        view.setDate("일시");
        view2 = new PictureItemView(this);
        view2.setName("사진이름");
        view2.setDate("일시");

        container.addView(view);
        container.addView(view2);

        translateIn = AnimationUtils.loadAnimation(this, R.anim.in);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pictures = queryAllPictures();//갤러리에 있는 사진 정보 가져오는 것

                AnimationThread thread = new AnimationThread();
                thread.start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                selected = 0;

                view.clearAnimation();
            }
        });


        AndPermission.with(this)
                .runtime()
                .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.d("Main", "허용된 권한 갯수 : " + permissions.size());
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        Log.d("Main", "거부된 권한 갯수 : " + permissions.size());
                    }
                })
                .start();

    }

    class AnimationThread extends Thread {
        public void run() {
            running = true;
            while(running) {
                handler.post(new Runnable() {
                    public void run() {
                        if (selected < pictures.size()) { //pictures.size()가 짝수 일때 홀수일때 처리해줘야하지만 일단 안해줌
                            ImageInfo info = pictures.get(selected);
                            ImageInfo info2 = pictures.get(selected+1);

                            view.setName(info.getDisplayName());
                            view.setDate(info.getDateAdded());
                            view.setImage(info.getPath());

                            view2.setName(info2.getDisplayName());
                            view2.setDate(info2.getDateAdded());
                            view2.setImage(info2.getPath());

                            view.startAnimation(translateIn);
                            view2.startAnimation(translateIn);

                            selected += 2;
                            textView.setText(selected + " / " + pictureCount + " 개");

                        } else {
                            selected = 0;
                        }

                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                }
            }

            running = false;
        }
    }

    private ArrayList<ImageInfo> queryAllPictures() { //단말기 갤러리에서 사진 정보 가져오기, 이부분은 다시 복습
        pictureCount = 0; 

        ArrayList<ImageInfo> result = new ArrayList<>();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATE_ADDED };

        Cursor cursor = getContentResolver().query(uri, projection, null, null, MediaStore.MediaColumns.DATE_ADDED + " desc");
        int columnDataIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        int columnNameIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
        int columnDateIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED);

        while (cursor.moveToNext()) {
            String path = cursor.getString(columnDataIndex);
            String displayName = cursor.getString(columnNameIndex);
            String outDate = cursor.getString(columnDateIndex);
            String addedDate = dateFormat.format(new Date(new Long(outDate).longValue() * 1000L));

            if (!TextUtils.isEmpty(path)) {
                ImageInfo info = new ImageInfo(path, displayName, addedDate);
                result.add(info);
            }

            pictureCount++;
        }

        textView.setText(selected + " / " + pictureCount + " 개");
        Log.d("MainActivity", "Picture count : " + pictureCount);

        for (ImageInfo info : result) {
            Log.d("MainActivity", info.toString());
        }

        return result;
    }

}