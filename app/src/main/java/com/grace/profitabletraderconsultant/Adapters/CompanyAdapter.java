package com.grace.profitabletraderconsultant.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.grace.profitabletraderconsultant.Models.Company;
import com.grace.profitabletraderconsultant.R;

import java.util.List;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyAdapter.MyViewHolder> {

    Context context;
    List<Company> mData;

    public CompanyAdapter(Context context, List<Company> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.busName.setText(mData.get(position).getBusinessName());
        holder.busType.setText(mData.get(position).getBusinessType());
        holder.busCounty.setText(mData.get(position).getCounty());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends ViewHolder{

        TextView busName, busType, busCounty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            busName = itemView.findViewById(R.id.businessNameOutput);
            busType = itemView.findViewById(R.id.businessType);
            busCounty = itemView.findViewById(R.id.county);

        }
    }
}
