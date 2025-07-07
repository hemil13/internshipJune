package com.example.internshipjune;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashboardActivity extends AppCompatActivity {
    TextView welcome_text;
    Button logout, delete, profile, category, wishlist;


    SQLiteDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp = getSharedPreferences(ConstantSp.pref,MODE_PRIVATE);

        db = openOrCreateDatabase(ConstantSp.databse_name, MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);


        welcome_text = findViewById(R.id.welcome_text);
        logout = findViewById(R.id.dashboard_logout);
        delete = findViewById(R.id.dashboard_delete);
        profile = findViewById(R.id.dashboard_profile);
        category = findViewById(R.id.dashboard_category);
        wishlist = findViewById(R.id.dashboard_wishlist);

        welcome_text.setText("Welcome "+sp.getString(ConstantSp.name, ""));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sp.edit().clear().commit();
                Toast.makeText(DashboardActivity.this, "Logged Out", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String deleteUser = "DELETE FROM user WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"'";
                db.execSQL(deleteUser);

                sp.edit().clear().commit();

                Toast.makeText(DashboardActivity.this, "Profile Deleted", Toast.LENGTH_LONG).show();

                Intent intent  = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, WishlistActivity.class);
                startActivity(intent);
            }
        });

    }
}