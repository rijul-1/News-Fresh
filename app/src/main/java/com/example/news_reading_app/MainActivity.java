package com.example.news_reading_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mfirebaseauth;
    EditText email_id, password;
    Button sign_in;

    public void sign_up(View view){
        Intent i = new Intent(MainActivity.this , Sign_Up.class);
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mfirebaseauth = FirebaseAuth.getInstance();
        email_id = findViewById(R.id.email_id);
        password = findViewById(R.id.password);
        sign_in = (Button) findViewById(R.id.Sign_Up_button);


        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_id.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this , "Enter Email Id and Password",Toast.LENGTH_SHORT).show();
                }
                else if(email.isEmpty()){
                    email_id.setError("Please Enter Email Address");
                    email_id.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please Enter Your Password");
                    password.requestFocus();
                }
                else {
                    mfirebaseauth.signInWithEmailAndPassword(email, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(MainActivity.this, "Successfully Sign In",Toast.LENGTH_SHORT).show();
                            Intent i  = new Intent(MainActivity.this ,home_page.class);
                            startActivity(i);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this , e.getMessage() , Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}