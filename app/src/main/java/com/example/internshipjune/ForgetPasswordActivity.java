package com.example.internshipjune;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ForgetPasswordActivity extends AppCompatActivity {

    EditText email, new_password, new_cnf_password;
    Button change_password;

    String email_pattern = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        email = findViewById(R.id.forget_email);
        new_password = findViewById(R.id.forget_new_password);
        new_cnf_password = findViewById(R.id.forget_new_cnf_password);
        change_password = findViewById(R.id.forget_button);


        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Enter Email");
                } else if (!email.getText().toString().matches(email_pattern)) {
                    email.setError("Enter A Valid Email");
                } else if (new_password.getText().toString().trim().equals("")) {
                    new_password.setError("Enter New Password");
                } else if (new_password.getText().toString().length()<6) {
                    new_password.setError("Minimum 6 Characters");
                } else if (new_cnf_password.getText().toString().trim().equals("")) {
                    new_cnf_password.setError("Enter New Confirm Password");
                } else if (!new_cnf_password.getText().toString().matches(new_password.getText().toString())) {
                    new_cnf_password.setError("New Confirm Password Doesn't Matches");
                }
                else{
                    Toast.makeText(ForgetPasswordActivity.this, "Password Changed Successfully", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });
    }
}