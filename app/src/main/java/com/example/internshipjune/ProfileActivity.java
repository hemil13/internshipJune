package com.example.internshipjune;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfileActivity extends AppCompatActivity {

    EditText name, email, contact, password, cnf_password;
    Button edit, update;

    SharedPreferences sp;

    SQLiteDatabase db;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences(ConstantSp.pref, MODE_PRIVATE);
        db = openOrCreateDatabase("InternshipJune.db", MODE_PRIVATE, null);
        String userTable = "CREATE TABLE IF NOT EXISTS user(userid INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(50), email VARCHAR(100), contact VARCHAR(15), password VARCHAR(20))";
        db.execSQL(userTable);


        name = findViewById(R.id.profile_name);
        email = findViewById(R.id.profile_email);
        contact = findViewById(R.id.profile_contact);
        password = findViewById(R.id.profile_password);
        cnf_password = findViewById(R.id.profile_cnf_password);
        edit = findViewById(R.id.profile_edit);
        update = findViewById(R.id.profile_update);



        setData(false);







        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cnf_password.setVisibility(View.VISIBLE);
                update.setVisibility(View.VISIBLE);
                edit.setVisibility(View.GONE);

                setData(true);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
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

                else if (cnf_password.getText().toString().trim().equals("")) {
                    cnf_password.setError("Enter Confirm Password");
                } else if (!cnf_password.getText().toString().matches(password.getText().toString())) {
                    cnf_password.setError("Password Doesn't Matches");
                }
                else{

                    String updateQuery = "UPDATE user SET name = '"+name.getText().toString()+"', " +
                            "email = '"+email.getText().toString()+"', " +
                            "contact = '"+contact.getText().toString()+"', " +
                            "password = '"+password.getText().toString()+"' " +
                            "WHERE userid = '"+sp.getString(ConstantSp.userid, "")+"'";

                    db.execSQL(updateQuery);

                    sp.edit().putString(ConstantSp.name,name.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.email, email.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.contact, contact.getText().toString()).commit();
                    sp.edit().putString(ConstantSp.password, password.getText().toString()).commit();


                    Toast.makeText(ProfileActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();

                    cnf_password.setVisibility(View.GONE);
                    update.setVisibility(View.GONE);
                    edit.setVisibility(View.VISIBLE);
                    setData(false);
                }




            }
        });

    }

    private void setData(boolean b) {

        name.setText(sp.getString(ConstantSp.name, ""));
        email.setText(sp.getString(ConstantSp.email,""));
        contact.setText(sp.getString(ConstantSp.contact, ""));
        password.setText(sp.getString(ConstantSp.password,""));


        name.setEnabled(b);
        email.setEnabled(b);
        contact.setEnabled(b);
        password.setEnabled(b);


    }
}