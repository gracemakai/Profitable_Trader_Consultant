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

    TextView ProductName, ProductPrice, Lowest, Highest;
    LineChart lineChart;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual__product);

        ProductName = findViewById(R.id.productName);
        ProductPrice = findViewById(R.id.productPrice);
        Lowest = findViewById(R.id.lowest);
        Highest = findViewById(R.id.highest);
       // verticalBarChart = findViewById(R.id.VerticalBarChart);
        lineChart = findViewById(R.id.lineChart);

        bundle = getIntent().getExtras();
        ProductName.setText(bundle.getString("product"));
        ProductPrice.setText(bundle.getString("price"));
        lineChartData();

        Log.d("6", "Done");
    }

    public void lineChartData(){
        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Product");
        Query query = mPostReference.orderByChild("product").equalTo(bundle.getString("product"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List priceData = new ArrayList();
               ArrayList yData = new ArrayList<>();
                float i =0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
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

    public void calculatePrice(ArrayList<Integer> price){
        //findMin(ArrayList <Integer> price);

    }
    public static Integer findMin(List<Integer> list)
    {

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
    public static Integer findMax(List<Integer> list)
    {

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


  /* public void verticalBarChartData (){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
       Query query = databaseReference.orderByChild("product").equalTo(bundle.getString("product"));
       query.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               collectPrices((Map<String, Object>) dataSnapshot.getValue());

           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
       //Month

/*
       final ArrayList dataset = new ArrayList();
       DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference("Product");
       Query query = mPostReference.orderByChild("product").equalTo(bundle.getString("product"));
       query.addValueEventListener( new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   ArrayList yData = new ArrayList<>();

                   for (int i = 0; i <= month.size(); i++) {
                       for (DataSnapshot ds : dataSnapshot.getChildren()) {
                           i = i + 1;
                           String SV = ds.child("price").getValue().toString();
                           int SensorValue = Integer.parseInt(SV);
                           yData.add(new BarEntry(SensorValue, i) {
                           });
                       }
                   }


                   BarDataSet barDataSet = new BarDataSet(yData, "DataData");
                   dataset.add(barDataSet);
                   BarData data = new BarData(month,dataset);
                   verticalBarChart.setData(data);
                   verticalBarChart.animateXY(2000, 2000);
                   verticalBarChart.setDescription("My Chart");
                   verticalBarChart.invalidate();
               }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       }
 */

  /*  public void verticalBarChartData(){
        BarData data = new BarData(Month(),Price());
        Log.d("1", "Done");
        verticalBarChart.setData(data);
        Log.d("2", "Done");
        verticalBarChart.setDescription(bundle.getString("product"));
        Log.d("3", "Done");
        verticalBarChart.animateXY(2000, 2000);
        Log.d("4", "Done");
        verticalBarChart.invalidate();
        Log.d("5", "Done");
    }

  /*  public ArrayList Product()
    {
        ArrayList productName = new ArrayList<>();
        //product = productList.get(position);
       // productName.add(product.getProduct());
        String products = bundle.getString("product");
        productName.add(products);

        return productName;
    }

   */
/*
    public ArrayList<String>Price(){
        final ArrayList<String> productPrice = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Product");
        Query query = databaseReference.orderByChild("product").equalTo(bundle.getString("product"));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              /*  for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String prices = snapshot.child("price").getValue().toString();
                    productPrice.add(prices);


                    GenericTypeIndicator<Map<String, Product>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Product>>(){};
                    Map<String, Product> hashMap = dataSnapshot.getValue(genericTypeIndicator);
                    for (Map.Entry<String, Product> entry : hashMap.entrySet()) {
                        Product product = entry.getValue();
                        productPrice.add(product.getPrice());


                        Log.d(TAG, product.getPrice());
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Log.d("Price", "Done");
        return productPrice;
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

 */
}
  /*  private void collectPrices(Map<String,Object> users) {

        ArrayList genre = new ArrayList<>();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            genre.add(singleUser.get("movieName"));
        }
        //month
        final ArrayList<String> month = new ArrayList<>();
        month.add("JAN");
        month.add("FEB");
        month.add("MARCH");
        month.add("APRIL");
        month.add("JUNE");

        BarDataSet barDataSet = new BarDataSet(genre, "DataData");
       // dataset.add(barDataSet);
      /*  BarData data = new BarData(month,barDataSet);
        verticalBarChart.setData(data);
        verticalBarChart.animateXY(2000, 2000);
        verticalBarChart.setDescription("My Chart");
        verticalBarChart.invalidate();
       // textView.setText(genre.toString());

       */




