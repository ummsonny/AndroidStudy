package org.techtown.mission10;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Fragment1 extends Fragment {

    ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_1, container, false);

        viewPager = rootView.findViewById(R.id.pager);
        viewPager.setOffscreenPageLimit(2);

        MyPagerAdapter adapter = new MyPagerAdapter(this);
        adapter.addItem(new Fragment1_1());
        adapter.addItem(new Fragment1_2());

        viewPager.setAdapter(adapter);

        return rootView;

    }


    class MyPagerAdapter extends FragmentStateAdapter {

        ArrayList<Fragment> items = new ArrayList<>();

        public MyPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        public void addItem(Fragment fm){
            items.add(fm);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return items.get(position);
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }
}