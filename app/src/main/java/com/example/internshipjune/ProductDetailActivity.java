package com.example.internshipjune;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetailActivity extends AppCompatActivity {

    SharedPreferences sp;

    TextView name, price, description;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        name = findViewById(R.id.product_detail_text);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);
        image = findViewById(R.id.product_detail_image);

        name.setText(sp.getString(ConstantSp.productname, ""));
        price.setText(ConstantSp.rupees+sp.getString(ConstantSp.productprice, ""));
        description.setText(sp.getString(ConstantSp.productdesc, ""));
        image.setImageResource(Integer.parseInt(sp.getString(ConstantSp.productimage, "")));

    }
}