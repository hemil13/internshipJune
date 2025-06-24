package com.example.internshipjune;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    EditText email, passowrd;
    TextView forget_passowrd, create_new_account;
    Button login;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    SQLiteDatabase db;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);

        db = openOrCreateDatabase(ConstantSp.databse_name, MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);



        email = findViewById(R.id.main_email);
        passowrd = findViewById(R.id.main_passoword);
        forget_passowrd = findViewById(R.id.main_forget_passowrd);
        create_new_account = findViewById(R.id.create_new_account);
        login = findViewById(R.id.main_login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                }
                else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter A Valid Email");
                }
                else if (passowrd.getText().toString().trim().equals("")) {
                    passowrd.setError("Enter Password");
                }
                else if (passowrd.getText().toString().length()<6) {
                    passowrd.setError("Minimum 6 Characters");
                }
                else{
                    String checkUser = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"' AND password = '"+passowrd.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(checkUser, null);

                    if (cursor.getCount()>0){

                        while(cursor.moveToNext()){
                            sp.edit().putString(ConstantSp.userid, cursor.getString(0)).commit();
                            sp.edit().putString(ConstantSp.name, cursor.getString(1)).commit();
                            sp.edit().putString(ConstantSp.email, cursor.getString(2)).commit();
                            sp.edit().putString(ConstantSp.contact, cursor.getString(3)).commit();
                            sp.edit().putString(ConstantSp.password, cursor.getString(4)).commit();
                        }


                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    }

                    else {
                        Toast.makeText(MainActivity.this, "Invalid Credential", Toast.LENGTH_LONG).show();
                    }


//                    Snackbar.make(view, "Login Successful", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        forget_passowrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });


        create_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });


    }
}