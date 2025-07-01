package com.example.internshipjune;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        db = openOrCreateDatabase("InternshipJune.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);


        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryTable);






        category_recycler = findViewById(R.id.category_recycler);

        category_recycler.setLayoutManager(new LinearLayoutManager(CategoryActivity.this));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
//        category_recycler.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<>();




        for(int i = 0;i<nameArray.length;i++){
            String checkCategory = "SELECT * FROM category WHERE name = '"+nameArray[i]+"'";
            Cursor cursor = db.rawQuery(checkCategory, null);

            if(cursor.getCount()>0){
                // Do Nothing as data already exists
            }
            else{
                String insertCategory = "INSERT INTO category VALUES(NULL, '"+nameArray[i]+"', '"+imageArray[i]+"')";
                db.execSQL(insertCategory);
            }
        }




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

