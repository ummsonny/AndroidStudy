package org.techtown.mission14;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ThingAdapter extends RecyclerView.Adapter<ThingAdapter.ViewHolder> implements OnThingItemClickListener {

    ArrayList<Thing> items = new ArrayList<>();
    OnThingItemClickListener listener;

    //필수기능
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View item = inflater.inflate(R.layout.thing_item, parent, false);
        return new ViewHolder(item, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Thing item = items.get(position);
        holder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //어댑터 추가기능 : 데이터를 넣기 위해
    public void addItem(Thing item){
        items.add(item);
    }

    public void setItems(ArrayList<Thing> items){
        this.items = items;
    }

    public Thing getItem(int position){
        return items.get(position);
    }

    public void setItem(Thing item, int position){
        items.set(position, item);
    }

    //리스너
    public void setListener(OnThingItemClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(ViewHolder holder, View view, int position) {
        if(listener!=null){
            listener.onClick(holder, view, position);
        }
    }

    //뷰홀더 클래스
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView1;
        TextView textView2;
        TextView textView3;

        public ViewHolder(@NonNull View itemView, OnThingItemClickListener listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(ViewHolder.this, v, getAdapterPosition());
                }
            });
        }

        public void setItem(Thing thing){
            textView1.setText(thing.getName());
            textView2.setText(thing.getCost());
            textView3.setText(thing.getExplain());
            imageView.setImageResource(thing.getResId());
        }
    }
}