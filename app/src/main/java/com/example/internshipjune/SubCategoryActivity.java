package com.example.internshipjune;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    int[] subcategoryIdArray = {1,2,3,4,5,6,7,8,9};
    int[] categoryIdArray = {1,1,1,2,2,2,3,3,3};
    String[] nameArray = {"Mobile", "Headphones", "Earbuds", "Jeans", "Shirts", "T Shirts", "Horror", "Fiction", "Novels"};
    int[] imageArray = {R.drawable.mobile, R.drawable.headphone, R.drawable.earbuds, R.drawable.jeans, R.drawable.shirt, R.drawable.thsirt, R.drawable.horror, R.drawable.fiction, R.drawable.novel};

    ArrayList<SubCategoryList> arraylist;

    RecyclerView subcategory_recycler;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);


        subcategory_recycler = findViewById(R.id.subcategory_recycler);



        subcategory_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        arraylist = new ArrayList<>();

        for(int i =0 ; i<subcategoryIdArray.length; i++){
            SubCategoryList subCategoryList = new SubCategoryList();
            if(categoryIdArray[i] == Integer.parseInt(sp.getString(ConstantSp.categoryid, ""))){
                subCategoryList.setSubcategoryid(subcategoryIdArray[i]);
                subCategoryList.setName(nameArray[i]);
                subCategoryList.setImage(imageArray[i]);
                arraylist.add(subCategoryList);
            }

        }

//        SubCategoryAdapter adapter = new SubCategoryAdapter (SubCategoryActivity.this, categoryIdArray, subcategoryIdArray, nameArray, imageArray);
//        subcategory_recycler.setAdapter(adapter);

        SubCategoryAdapter adapter = new SubCategoryAdapter (SubCategoryActivity.this, arraylist);
        subcategory_recycler.setAdapter(adapter);



    }
}