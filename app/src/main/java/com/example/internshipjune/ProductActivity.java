package com.example.internshipjune;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        db = openOrCreateDatabase("InternshipJune.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);

        String categoryTable = "CREATE TABLE IF NOT EXISTS category(categoryid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(categoryTable);

        String subcategoryTable = "CREATE TABLE IF NOT EXISTS subcategory(subcategoryid INTEGER PRIMARY KEY AUTOINCREMENT,categoryid VACHAR(10), name VARCHAR(50), image VARCHAR(100))";
        db.execSQL(subcategoryTable);

        String productTable = "CREATE TABLE IF NOT EXISTS product(productid INTEGER PRIMARY KEY AUTOINCREMENT, subcategoryid VACHAR(10), name VARCHAR(50), image VARCHAR(100), price VARCHAR(10), description VARCHAR(100))";
        db.execSQL(productTable);

        String wishlistTable = "CREATE TABLE IF NOT EXISTS wishlist(wishlistid INTEGER PRIMARY KEY AUTOINCREMENT, productid INTEGER, userid INTEGER)";
        db.execSQL(wishlistTable);




        recyclerView = findViewById(R.id.productRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());




        arrayList = new ArrayList<>();


        for(int i = 0;i<nameArray.length;i++){
            String checkProduct = "SELECT * FROM product WHERE name = '"+nameArray[i]+"'AND subcategoryid = '"+subcategoryIdArray[i]+"'";
            Cursor cursor = db.rawQuery(checkProduct, null);

            if(cursor.getCount()==0){
                String insertProduct = "INSERT INTO product VALUES(NULL, '"+subcategoryIdArray[i]+"', '"+nameArray[i]+"', '"+imageArray[i]+"', '"+priceArray[i]+"', '"+descArray[i]+"')";
                db.execSQL(insertProduct);
            }
        }


        String checkProduct = "SELECT * FROM product WHERE subcategoryid = '"+sp.getString(ConstantSp.subcategoryid, "")+"'";
        Cursor cursor = db.rawQuery(checkProduct, null);

        if(cursor.getCount()>0){
            while(cursor.moveToNext()) {
                ProductList list = new ProductList();
                list.setProductId(cursor.getInt(0));
                list.setSubcategoryId(cursor.getInt(1));
                list.setName(cursor.getString(2));
                list.setImage(cursor.getInt(3));
                list.setPrice(cursor.getString(4));
                list.setDescription(cursor.getString(5));

                String chekcWishlist = "SELECT * FROM wishlist WHERE productid = '"+list.getProductId()+"' AND userid = '"+sp.getString(ConstantSp.userid, "")+"'";
                Cursor wishCursor = db.rawQuery(chekcWishlist, null);

                if (wishCursor.getCount()>0){
                    list.setWishlist(true);
                }
                else {
                    list.setWishlist(false);
                }

                arrayList.add(list);
            }

//            ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
//            recyclerView.setAdapter(adapter);

            ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList, db);
            recyclerView.setAdapter(adapter);
        }




//        for(int i = 0 ; i<productIdArray.length; i++){
//
//            if (subcategoryIdArray[i] == Integer.parseInt(sp.getString(ConstantSp.subcategoryid,""))){
//                ProductList list = new ProductList();
//                list.setProductId(productIdArray[i]);
//                list.setSubcategoryId(subcategoryIdArray[i]);
//                list.setName(nameArray[i]);
//                list.setImage(imageArray[i]);
//                list.setPrice(priceArray[i]);
//                list.setDescription(descArray[i]);
//                arrayList.add(list);
//            }
//        }

//        ProductAdapter adapter = new ProductAdapter(ProductActivity.this, arrayList);
//        recyclerView.setAdapter(adapter);
    }
}