package com.example.internshipjune;

import android.content.SharedPreferences;
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

    Boolean isWhislist = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        name = findViewById(R.id.product_detail_text);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);
        image = findViewById(R.id.product_detail_image);
        wishlist = findViewById(R.id.product_detail_wishlist);

        name.setText(sp.getString(ConstantSp.productname, ""));
        price.setText(ConstantSp.rupees+sp.getString(ConstantSp.productprice, ""));
        description.setText(sp.getString(ConstantSp.productdesc, ""));
        image.setImageResource(Integer.parseInt(sp.getString(ConstantSp.productimage, "")));


        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isWhislist){
                    isWhislist = false;
                    wishlist.setImageResource(R.drawable.wishlist_empty);
                    Toast.makeText(ProductDetailActivity.this, "Removed From Wishlist", Toast.LENGTH_SHORT).show();
                }
                else{
                    isWhislist = true;
                    wishlist.setImageResource(R.drawable.wishlist_fill);
                    Toast.makeText(ProductDetailActivity.this, "Added to Wishlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}