package com.grace.profitabletraderconsultant;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual_Product extends AppCompatActivity {

    TextView ProductName, ProductPrice, Countybox, SubCountyBox, Lowest, Highest;
    LineChart lineChart;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual__product);

        initViews();

        lineChartData();

        Log.d("6", "Done");
    }

    private void initViews() {

        ProductName = findViewById(R.id.productName);
        ProductPrice = findViewById(R.id.productPrice);
        Countybox = findViewById(R.id.productCounty);
        SubCountyBox = findViewById(R.id.productSubCounty);
        Lowest = findViewById(R.id.lowest);
        Highest = findViewById(R.id.highest);
        lineChart = findViewById(R.id.lineChart);


        bundle = getIntent().getExtras();
        ProductName.setText(bundle.getString("product"));
        ProductPrice.setText(bundle.getString("price"));
        Countybox.setText(bundle.getString("county"));
        SubCountyBox.setText(bundle.getString("sub"));

    }

    public void lineChartData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(Countybox.getText().toString());
        Query query = databaseReference.orderByChild("product").equalTo(bundle.getString("product"));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<Integer> priceData = new ArrayList<>();
                ArrayList<Entry> yData = new ArrayList<>();
                float i =0;

                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    Log.i(getClass().getSimpleName(), "onDataChange: " + ds.child("price").getValue(String.class));
                    i=i+1;
                    String SV =  ds.child("price").getValue().toString();
                    int SensorValue = Integer.parseInt(SV);
                    priceData.add(SensorValue);
                    yData.add(new Entry(i,SensorValue));
                }
                String low = String.valueOf(findMin(priceData));
                Lowest.setText(low);
                String high = String.valueOf(findMax(priceData));
                Highest.setText(high);
                final LineDataSet lineDataSet = new LineDataSet(yData,"Price");
                LineData data = new LineData(lineDataSet);
                lineChart.setData(data);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static Integer findMin(List<Integer> list) {
        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Integer.MAX_VALUE;
        }

        // create a new list to avoid modification
        // in the original list
        List<Integer> sortedlist = new ArrayList<>(list);

        // sort list in natural order
        Collections.sort(sortedlist);

        // first element in the sorted list
        // would be minimum
        return sortedlist.get(0);
    }

    // function return maximum value in an unsorted
    // list in Java using Collection
    public static Integer findMax(List<Integer> list) {
        // check list is empty or not
        if (list == null || list.size() == 0) {
            return Integer.MIN_VALUE;
        }

        // create a new list to avoid modification
        // in the original list
        List<Integer> sortedlist = new ArrayList<>(list);

        // sort list in natural order
        Collections.sort(sortedlist);

        // last element in the sorted list would be maximum
        return sortedlist.get(sortedlist.size() - 1);
    }

    public ArrayList<String> Month(){
        ArrayList<String> month = new ArrayList<>();
        month.add("JAN");
        month.add("FEB");
        month.add("MARCH");
        month.add("APRIL");
        month.add("JUNE");
        Log.d("Month", "Done");
        return month;
    }
}




