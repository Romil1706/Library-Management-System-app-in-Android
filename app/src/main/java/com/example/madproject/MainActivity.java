package com.example.madproject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
   EditText username, password;
   Button loginUserBtn, loginAdminBtn, registerUserBtn, registerAdminBtn;
   DBHelper db;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       username = findViewById(R.id.username);
       password = findViewById(R.id.password);
       loginUserBtn = findViewById(R.id.loginUserBtn);
       loginAdminBtn = findViewById(R.id.loginAdminBtn);
       registerUserBtn = findViewById(R.id.registerUserBtn);
       registerAdminBtn = findViewById(R.id.registerAdminBtn);
       db = new DBHelper(this);

       loginUserBtn.setOnClickListener(v -> {
           String user = username.getText().toString().trim();
           String pass = password.getText().toString().trim();
           if (db.checkUser(user, pass, "user")) {
               Toast.makeText(this, "User Login Success", Toast.LENGTH_SHORT).show();
               Intent i = new Intent(this, UserActivity.class);
               i.putExtra("username", user);
               startActivity(i);
           } else {
               Toast.makeText(this, "Invalid User Credentials", Toast.LENGTH_SHORT).show();
           }
       });
       loginAdminBtn.setOnClickListener(v -> {
           String user = username.getText().toString().trim();
           String pass = password.getText().toString().trim();
           if (db.checkUser(user, pass, "admin")) {
               Toast.makeText(this, "Admin Login Success", Toast.LENGTH_SHORT).show();
               startActivity(new Intent(this, AdminActivity.class));
           } else {
               Toast.makeText(this, "Invalid Admin Credentials", Toast.LENGTH_SHORT).show();
           }
       });
       registerUserBtn.setOnClickListener(v -> {
           String user = username.getText().toString().trim();
           String pass = password.getText().toString().trim();
           if (db.registerUser(user, pass, "user")) {
               Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
           }
       });
       registerAdminBtn.setOnClickListener(v -> {
           String user = username.getText().toString().trim();
           String pass = password.getText().toString().trim();
           if (db.registerUser(user, pass, "admin")) {
               Toast.makeText(this, "Admin Registered", Toast.LENGTH_SHORT).show();
           } else {
               Toast.makeText(this, "Admin already exists", Toast.LENGTH_SHORT).show();
           }
       });
   }
}
