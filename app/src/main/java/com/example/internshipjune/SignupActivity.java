package com.example.internshipjune;

import android.content.Intent;
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

public class SignupActivity extends AppCompatActivity {

    EditText name, email, contact, password, cnfpassword;
    Button signup;

    TextView already_account;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";


    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        db = openOrCreateDatabase("InternshipJune.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);


        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);
        password = findViewById(R.id.signup_password);
        cnfpassword = findViewById(R.id.signup_cnf_password);
        signup = findViewById(R.id.signup_button);
        already_account = findViewById(R.id.already_account);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("")){
                    name.setError("Enter Name");
                }
                else if (contact.getText().toString().trim().equals("")) {
                    contact.setError("Enter Contact Number");
                } else if (!(contact.getText().toString().length() ==10)) {
                    contact.setError("10 digits Required");
                }

                else if (email.getText().toString().trim().equals("")) {
                    email.setError("Enter Email");
                } else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter a Valid Email");
                }

                else if (password.getText().toString().trim().equals("")) {
                    password.setError("Enter Password");
                } else if (password.getText().toString().length()<6) {
                    password.setError("Minimum 6 Characters");
                }

                else if (cnfpassword.getText().toString().trim().equals("")) {
                    cnfpassword.setError("Enter Confirm Password");
                } else if (!cnfpassword.getText().toString().matches(password.getText().toString())) {
                    cnfpassword.setError("Password Doesn't Matches");
                }
                else{

                    String selectQuery = "SELECT * FROM user WHERE email = '"+email.getText().toString()+"'";
                    Cursor cursor = db.rawQuery(selectQuery, null);


                    if(cursor.getCount()>0){
                        Toast.makeText(SignupActivity.this, "Email Already Exists", Toast.LENGTH_LONG).show();
                    }
                    else{
                        String insertUser = "INSERT INTO user VALUES (NULL, '"+name.getText().toString()+"', '"+email.getText().toString()+"', '"+contact.getText().toString()+"', '"+password.getText().toString()+"')";
                        db.execSQL(insertUser);
                        Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//                    startActivity(intent);
                    }
                }
            }
        });


        already_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}