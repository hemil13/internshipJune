package com.example.internshipjune;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    int[] productIdArray = {1,2,3,4,5,6};
    int[] subcategoryIdArray = {1,1,1,2,2,2};
    String[] nameArray = {"Redmi","Oneplus","Sony","Noise Headphones","Airpods Max","Sony Headphones"};
    int[] imageArray = {R.drawable.redmi, R.drawable.oneplus, R.drawable.sony,
            R.drawable.noise, R.drawable.aiepodsmax, R.drawable.sonyheadphones};
    String[] priceArray = {"20000", "30000", "40000", "50000", "60000", "70000"};
    String[] descArray = {"Redmi Phone Description", "Oneplus Phone Description", "Sony Phone Description",
            "Noise Headphones Description", "Airpods Max Description", "Sony Headphones Description"};


    ArrayList<ProductList> arrayList;

    RecyclerView recyclerView;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        recyclerView = findViewById(R.id.productRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        arrayList = new ArrayList<>();

        for(int i = 0 ; i<productIdArray.length; i++){
            ProductList list = new ProductList();

            if (subcategoryIdArray[i] == Integer.parseInt(sp.getString(ConstantSp.subcategoryid,""))){
                list.setProductId(productIdArray[i]);
                list.setSubcategoryId(subcategoryIdArray[i]);
                list.setName(nameArray[i]);
                list.setImage(imageArray[i]);
                list.setPrice(priceArray[i]);
                list.setDescription(descArray[i]);
                arrayList.add(list);
            }
        }

        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
        recyclerView.setAdapter(adapter);
    }
}