package org.techtown.mission14;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ThingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ThingAdapter();
        adapter.addItem(new Thing(R.drawable.back, "축구공", "20000만원", "되게 잘 굴러감 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"));
        adapter.addItem(new Thing(R.drawable.beach, "발리", "300000만원", "휴양지중에 최고인 듯 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"));
        adapter.addItem(new Thing(R.drawable.heart, "심장", "500000만원", "외로운이에게 사랑을............"));
        adapter.addItem(new Thing(R.drawable.home, "집", "2억", "화장실 2개를 포함한 한강뷰를 가진 49평 아파트. 나도 여기 살고싶다 정말정말정말정말정말"));
        adapter.addItem(new Thing(R.drawable.back, "축구공", "20000만원", "되게 잘 굴러감 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"));
        adapter.addItem(new Thing(R.drawable.beach, "발리", "300000만원", "휴양지중에 최고인 듯 ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ"));
        adapter.addItem(new Thing(R.drawable.heart, "심장", "500000만원", "외로운이에게 사랑을............"));
        adapter.addItem(new Thing(R.drawable.home, "집", "2억", "화장실 2개를 포함한 한강뷰를 가진 49평 아파트. 나도 여기 살고싶다 정말정말정말정말정말"));


        recyclerView.setAdapter(adapter);

        adapter.setListener(new OnThingItemClickListener() {
            @Override
            public void onClick(ThingAdapter.ViewHolder holder, View view, int position) {
                Thing item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "이름 : "+item.getName()+", 가격 : "+item.getCost(), Toast.LENGTH_LONG).show();
            }
        });
    }

}