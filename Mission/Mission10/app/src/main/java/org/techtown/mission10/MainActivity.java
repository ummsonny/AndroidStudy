package org.techtown.mission10;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //36~42코드가 있어야 제대로 액션바쪽에 메뉴 햄버거 나온다.

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,fragment1);
        ft.addToBackStack(null);
        ft.commit();
        //51~54줄이 이 문제의 핵심이다. 이거 없으면 첫번째 탭으로 갔을때 앱이 강제종료된다. 시발.........
        //https://velog.io/@bonimddal2/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-deprecated-%EB%90%98%EC%A7%80-%EC%95%8A%EC%9D%80-%EB%B0%A9%EB%B2%95%EC%9C%BC%EB%A1%9C-%ED%94%84%EB%9E%98%EA%B7%B8%EB%A8%BC%ED%8A%B8-%EC%95%88%EC%97%90-%EB%B7%B0%ED%8E%98%EC%9D%B4%EC%A0%80-%EB%84%A3%EC%9D%80-%ED%99%94%EB%A9%B4-%EB%A7%8C%EB%93%A4%EA%B8%B0
        //위 링크 참고!!!!!!!
        BottomNavigationView bn = findViewById(R.id.bottom_navi);
        bn.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tab1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commit();
//                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.frameLayout,fragment1);
//                        ft.addToBackStack(null);
//                        ft.commit();
                        //위에 네줄 있어도 되고 없어도 됨. 51~54줄에서 이미 넣어놨기 때문에 안넣어도 되는듯
                        return true;
                    case R.id.tab2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment2).commit();
                        return true;
                    case R.id.tab3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment3).commit();
                        return true;
                }
                return false;
            }
        });

    }
}