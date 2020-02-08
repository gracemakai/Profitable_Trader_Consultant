package com.grace.profitabletraderconsultant.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.grace.profitabletraderconsultant.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView FirstLine;
        public TextView SecondLine;
        public View layout;

        public ViewHolder(@NonNull View v) {
            super(v);
            layout = v;
            FirstLine = v.findViewById(R.id.firstLine);
            SecondLine = v.findViewById(R.id.secondLine);
        }
    }

    public void add(int position, String item){
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position){
        values.remove(position);
        notifyItemRemoved(position);
    }

    public MyAdapter(List<String> myDataSet) {
        values = myDataSet;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, final int position) {

        final String name = values.get(position);
        holder.FirstLine.setText(name);
        holder.FirstLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });
        holder.SecondLine.setText("Footer " + name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}
