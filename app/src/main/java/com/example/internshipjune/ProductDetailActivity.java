package com.example.internshipjune;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    SharedPreferences sp;

    TextView name, price, description;
    ImageView image, wishlist;

    Boolean isWhislist;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

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


        name = findViewById(R.id.product_detail_text);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);
        image = findViewById(R.id.product_detail_image);
        wishlist = findViewById(R.id.product_detail_wishlist);

        name.setText(sp.getString(ConstantSp.productname, ""));
        price.setText(ConstantSp.rupees+sp.getString(ConstantSp.productprice, ""));
        description.setText(sp.getString(ConstantSp.productdesc, ""));
        image.setImageResource(Integer.parseInt(sp.getString(ConstantSp.productimage, "")));

        String checkWishlist = "SELECT * FROM wishlist WHERE productid = '"+sp.getString(ConstantSp.productid,"")+"' AND userid = '"+sp.getString(ConstantSp.userid,"")+"'";
        Cursor cursor = db.rawQuery(checkWishlist, null);

        if(cursor.getCount()>0){
            isWhislist = true;
            wishlist.setImageResource(R.drawable.wishlist_fill);
        }
        else {
            isWhislist = false;
            wishlist.setImageResource(R.drawable.wishlist_empty);
        }



        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isWhislist){
                    String deleteWishlist = "DELETE FROM wishlist WHERE productid = '"+sp.getString(ConstantSp.productid,"")+"' AND userid = '"+sp.getString(ConstantSp.userid,"")+"'";
                    db.execSQL(deleteWishlist);

                    isWhislist = false;
                    wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(ProductDetailActivity.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    String insertWishlist = "INSERT INTO wishlist VALUES (NULL, '"+sp.getString(ConstantSp.productid,"")+"', '"+sp.getString(ConstantSp.userid,"")+"')";
                    db.execSQL(insertWishlist);


                    isWhislist = true;
                    wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(ProductDetailActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}