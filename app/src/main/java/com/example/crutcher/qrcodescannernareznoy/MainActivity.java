package com.example.crutcher.qrcodescannernareznoy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        AppCompatButton button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singin();
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }



    public void singin()
    {
        EditText email22 = findViewById(R.id.email);
        EditText password22 = findViewById(R.id.password);

        String email = email22.getText().toString();
        String password = password22.getText().toString();

        if(email.equals( "admin@narez.ru" )&& password.equals( "nareznoygh"))
        {
            Toast.makeText(MainActivity.this, "Aвторизация успешна1", Toast.LENGTH_SHORT).show();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Aвторизация успешна2", Toast.LENGTH_SHORT).show();


                        Intent intent= new Intent(MainActivity.this, ScanActivity.class);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.finish();

                    }else
                        Toast.makeText(MainActivity.this, "Aвторизация провалена2", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this, "Aвторизация провалена1 " + email + " " + password, Toast.LENGTH_SHORT).show();
        }
    }
}
