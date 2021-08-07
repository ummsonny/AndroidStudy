package org.techtown.mission13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements OnCustomerItemClickListener{
    ArrayList<Customer> items = new ArrayList<>();
    OnCustomerItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //어댑터 추가기능 : 데이터를 넣기 위해
    public void addItem(Customer item){
        items.add(item);
    }

    public void setItems(ArrayList<Customer> items){
        this.items = items;
    }

    public Customer getItem(int position){
        return items.get(position);
    }

    public void setItem(Customer item, int position){
        items.set(position, item);
    }
    //리스너 관련
    public void setListener(OnCustomerItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener!=null){
            listener.onItemClick(holder, view, position);
        }
    }

    //뷰홀더 클래스
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView_n;
        TextView textView_b;
        TextView textView_p;

        public ViewHolder(@NonNull View itemView, final OnCustomerItemClickListener listener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView_n = itemView.findViewById(R.id.textView_n);
            textView_b = itemView.findViewById(R.id.textView_b);
            textView_p = itemView.findViewById(R.id.textView_p);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Customer cus){
            imageView.setImageResource(cus.getResId());
            textView_n.setText(cus.getName());
            textView_b.setText(cus.getBirth());
            textView_p.setText(cus.getPhone());
        }


    }
}
