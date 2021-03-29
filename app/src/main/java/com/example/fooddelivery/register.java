package com.example.fooddelivery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText username,password;
    Button button;
    DatabaseReference  databaseReference;
    Member member;
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        button = findViewById(R.id.buttonsave);
        textView = findViewById(R.id.text);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Member");
        member = new Member();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member.setUsername(username.getText().toString().trim());
                member.setPassword(password.getText().toString().trim());
                databaseReference.push().setValue(member);
                Toast.makeText(register.this,"data berhasil ditambahkan",Toast.LENGTH_SHORT).show();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this, login.class);
                startActivity(intent);
            }
        });
    }
}
