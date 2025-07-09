package com.example.internshipjune;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    SharedPreferences sp;

    TextView name, price, description, qty;
    ImageView image, wishlist, cart, plus, minus;

    Boolean isWhislist;
    Button pay_now;

    LinearLayout cart_layout;

    SQLiteDatabase db;

    int iqty = 0;

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

        String cartTable = "CREATE TABLE IF NOT EXISTS cart(cartid INTEGER PRIMARY KEY AUTOINCREMENT,orderid INTEGER,  productid INTEGER, userid INTEGER, qty INTEGER, price INTEGER, totalPrice INTEGER)";
        db.execSQL(cartTable);

        name = findViewById(R.id.product_detail_text);
        price = findViewById(R.id.product_detail_price);
        description = findViewById(R.id.product_detail_description);
        image = findViewById(R.id.product_detail_image);
        wishlist = findViewById(R.id.product_detail_wishlist);
        pay_now = findViewById(R.id.pay_now);
        cart = findViewById(R.id.product_detail_cart);
        cart_layout = findViewById(R.id.product_detail_cart_layout);
        qty = findViewById(R.id.product_detail_cart_qty);
        plus = findViewById(R.id.product_detail_qty_add);
        minus = findViewById(R.id.product_detail_qty_substract);

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


        pay_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iqty = 1;
                int iPrice = Integer.parseInt(sp.getString(ConstantSp.productprice,""));
                int iTotalPrice = iqty*iPrice;
                String insertCartQuery = "INSERT INTO cart VALUES(NULL,'0','"+sp.getString(ConstantSp.userid, "")+"','"+sp.getString(ConstantSp.productid, "")+"','"+iqty+"','"+iPrice+"','"+iTotalPrice+"')";
                db.execSQL(insertCartQuery);
                Toast.makeText(ProductDetailActivity.this, "Added To Cart", Toast.LENGTH_SHORT).show();
                cart.setVisibility(View.GONE);
                cart_layout.setVisibility(View.VISIBLE);
                qty.setText(String.valueOf(iqty));
            }
        });

//        String checkCart = "SELECT * FROM cart WHERE productid = '"+sp.getString(ConstantSp.productid,"")+"' AND userid = '"+sp.getString(ConstantSp.userid,"")+"'";
//        Cursor cartCursor = db.rawQuery(checkCart, null);
//
//        if(cartCursor.getCount()>0){
//
//        }



        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iqty++;
                updateMethod(iqty, "update");
                qty.setText(String.valueOf(iqty));
                Toast.makeText(ProductDetailActivity.this, "Updated Cart", Toast.LENGTH_SHORT).show();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iqty--;
                if(iqty>0){
                    updateMethod(iqty,"update");
                    qty.setText(String.valueOf(iqty));
                    Toast.makeText(ProductDetailActivity.this, "Updated Cart", Toast.LENGTH_SHORT).show();
                }
                else {
                    updateMethod(iqty, "delete");
                    Toast.makeText(ProductDetailActivity.this, "Removed From Cart", Toast.LENGTH_SHORT).show();
                    cart.setVisibility(View.VISIBLE);
                    cart_layout.setVisibility(View.GONE);
                }
            }
        });

    }

    private void updateMethod(int iqty, String type) {
        if(type.equalsIgnoreCase("update")){
            String updateCart = "UPDATE cart SET qty = '"+iqty+"' AND totalPrice = '"+iqty*Integer.parseInt(sp.getString(ConstantSp.productprice,""))+"' WHERE productid = '"+sp.getString(ConstantSp.productid,"")+"' AND userid = '"+sp.getString(ConstantSp.userid,"")+"'";
            db.execSQL(updateCart);
        }

        if(type.equalsIgnoreCase("delete")){
            String deleteCart = "DELETE FROM cart WHERE productid = '"+sp.getString(ConstantSp.productid,"")+"' AND userid = '"+sp.getString(ConstantSp.userid,"")+"'";
            db.execSQL(deleteCart);
        }
    }

    private void startPayment() {
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_rGhSnxMQBMXmfL");

        try {
            JSONObject options = new JSONObject();
            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Purchase Deal From " + getResources().getString(R.string.app_name));
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", R.mipmap.ic_launcher);
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(sp.getString(ConstantSp.productprice,"")) * 100));

            JSONObject preFill = new JSONObject();
            preFill.put("email", "hemilgarala@gmail.com");
            preFill.put("contact", "9638221084");
            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Toast.makeText(ProductDetailActivity.this, "Payment Successfull", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(ProductDetailActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}