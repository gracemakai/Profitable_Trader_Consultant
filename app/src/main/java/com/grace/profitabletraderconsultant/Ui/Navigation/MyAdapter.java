package com.grace.profitabletraderconsultant.Ui.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.grace.profitabletraderconsultant.Models.Product;
import com.grace.profitabletraderconsultant.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<Product> products;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView FirstLine;
        public TextView SecondLine;

        public ViewHolder(@NonNull View v) {
            super(v);
            FirstLine = v.findViewById(R.id.First);
            SecondLine = v.findViewById(R.id.Second);
        }

    }

    public MyAdapter(List<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.ViewHolder holder, final int position) {
        Product product = products.get(position);
        holder.FirstLine.setText(product.getProduct());
        holder.SecondLine.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
