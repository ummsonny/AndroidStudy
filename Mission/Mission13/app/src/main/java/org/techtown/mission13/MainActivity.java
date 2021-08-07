package org.techtown.mission13;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;

    TextView textView2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        CustomerAdapter adapter = new CustomerAdapter();
        adapter.addItem(new Customer("유형수", "1996-10-16", "010-2362-6861", R.drawable.ic_launcher_background));
        adapter.addItem(new Customer("최종우", "1996-3-28", "010-2362-6862", R.drawable.ic_launcher_foreground));
        adapter.setListener(new OnCustomerItemClickListener() {
            @Override
            public void onItemClick(CustomerAdapter.ViewHolder holder, View view, int position) {
                Customer item = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), "아이템 선택됨 : "+item.getName(), Toast.LENGTH_LONG).show();
            }
        });
        recyclerView.setAdapter(adapter);


        textView2 = findViewById(R.id.textView2);
        textView2.setText(adapter.getItemCount()+"명");

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);


        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(new Customer(editText1.getText().toString(), editText2.getText().toString(), editText3.getText().toString()));
                adapter.notifyDataSetChanged();//예제들과는 다르게(미리 아이템을 넣어둔 어댑터를 사용) 나중에 내가 앱에서 추가할때는 꼭 이 코드가 있어야한다. 핵심************************
                textView2.setText(adapter.getItemCount()+"명");
            }
        });
    }
}