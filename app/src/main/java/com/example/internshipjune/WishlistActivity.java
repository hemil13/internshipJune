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

public class WishlistActivity extends AppCompatActivity {
    RecyclerView wishlist_recycler;

    ArrayList<WishlistList> arrayList;

    SharedPreferences sp;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);

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


        wishlist_recycler = findViewById(R.id.wishlistRecycler);

        wishlist_recycler.setLayoutManager(new LinearLayoutManager(WishlistActivity.this));
        wishlist_recycler.setItemAnimator(new DefaultItemAnimator());

        arrayList = new ArrayList<>();

        String getWishlist = "SELECT * FROM wishlist WHERE userid = '"+sp.getString(ConstantSp.userid,"")+"'";
        Cursor cursor = db.rawQuery(getWishlist, null);

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                WishlistList list = new WishlistList();
                list.setWishlistid(String.valueOf(cursor.getInt(0)));

                String getProducts = "SELECT * FROM product WHERE productid = '"+cursor.getString(1)+"'";
                Cursor productCursor = db.rawQuery(getProducts, null);

                if(productCursor.getCount()>0){
                    while (productCursor.moveToNext()){
                        list.setProductid(String.valueOf(productCursor.getInt(0)));
                        list.setSubcategoryid(productCursor.getString(1));
                        list.setName(productCursor.getString(2));
                        list.setImage(Integer.parseInt(productCursor.getString(3)));
                        list.setPrice(productCursor.getString(4));
                        list.setDescription(productCursor.getString(5));
                    }
                }
                productCursor.close();
                arrayList.add(list);
            }
        }

        WishlistAdapter adapter = new WishlistAdapter(WishlistActivity.this, arrayList, db);
        wishlist_recycler.setAdapter(adapter);





    }
}