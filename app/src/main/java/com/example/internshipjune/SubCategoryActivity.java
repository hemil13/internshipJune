package com.example.internshipjune;

import android.content.SharedPreferences;
import android.database.Cursor;
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

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        db = openOrCreateDatabase("InternshipJune.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid VACHAR(10), name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryTable);


        subcategory_recycler = findViewById(R.id.subcategory_recycler);



        subcategory_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        arraylist = new ArrayList<>();


        for(int i = 0;i<nameArray.length;i++){
            String checkSubCategory = "SELECT * FROM subcategory WHERE name = '"+nameArray[i]+"'AND categoryid = '"+categoryIdArray[i]+"'";
            Cursor cursor = db.rawQuery(checkSubCategory, null);

            if(cursor.getCount()>0){
                // Do Nothing as data already exists
            }
            else{
                String insertSubCategory = "INSERT INTO subcategory VALUES(NULL, '"+categoryIdArray[i]+"', '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertSubCategory);
            }
        }




        String checkSubcategory = "SELECT * FROM subcategory WHERE categoryid = '"+sp.getString(ConstantSp.categoryid, "")+"'";
        Cursor cursor = db.rawQuery(checkSubcategory, null);

        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                SubCategoryList subCategoryList = new SubCategoryList();
                subCategoryList.setSubcategoryid(cursor.getInt(0));
                subCategoryList.setName(cursor.getString(2));
                subCategoryList.setImage(cursor.getInt(3));
                arraylist.add(subCategoryList);
            }
            SubCategoryAdapter adapter = new SubCategoryAdapter (SubCategoryActivity.this, arraylist);
            subcategory_recycler.setAdapter(adapter);

        }



//        for(int i =0 ; i<subcategoryIdArray.length; i++){
//            SubCategoryList subCategoryList = new SubCategoryList();
//            if(categoryIdArray[i] == Integer.parseInt(sp.getString(ConstantSp.categoryid, ""))){
//                subCategoryList.setSubcategoryid(subcategoryIdArray[i]);
//                subCategoryList.setName(nameArray[i]);
//                subCategoryList.setImage(imageArray[i]);
//                arraylist.add(subCategoryList);
//            }
//
//        }

//        SubCategoryAdapter adapter = new SubCategoryAdapter (SubCategoryActivity.this, categoryIdArray, subcategoryIdArray, nameArray, imageArray);
//        subcategory_recycler.setAdapter(adapter);

//        SubCategoryAdapter adapter = new SubCategoryAdapter (SubCategoryActivity.this, arraylist);
//        subcategory_recycler.setAdapter(adapter);



    }
}