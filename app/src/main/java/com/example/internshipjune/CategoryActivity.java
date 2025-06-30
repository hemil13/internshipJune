package com.example.internshipjune;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    int[] categoryIdArray = {1,2,3};
    String[] nameArray = {"Electronics", "Clothes", "Books"};
    int[] imageArray = {R.drawable.electronics, R.drawable.clothes, R.drawable.books};

    RecyclerView category_recycler;

    ArrayList<CategoryList> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        category_recycler = findViewById(R.id.category_recycler);

        category_recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<>();


        for(int i=0; i<categoryIdArray.length;i++){
            CategoryList list = new CategoryList();
            list.setCategoryId(categoryIdArray[i]);
            list.setName(nameArray[i]);
            list.setImage(imageArray[i]);
            arrayList.add(list);
        }



//        CategoryAdapter adapter = new CategoryAdapter (CategoryActivity.this, categoryIdArray, nameArray, imageArray);
//        category_recycler.setAdapter(adapter);



        CategoryAdapter adapter = new CategoryAdapter (CategoryActivity.this, arrayList);
        category_recycler.setAdapter(adapter);


    }
}

