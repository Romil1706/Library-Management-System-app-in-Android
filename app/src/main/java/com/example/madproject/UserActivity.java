package com.example.madproject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {
   Button searchBtn, recordBtn, logoutBtn;
   ImageButton bellBtn;
   String username;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_user);
       searchBtn = findViewById(R.id.searchBtn);
       recordBtn = findViewById(R.id.recordBtn);
       bellBtn = findViewById(R.id.bellBtn);
       logoutBtn = findViewById(R.id.logoutBtn);
       username = getIntent().getStringExtra("username");
       searchBtn.setOnClickListener(v ->
               startActivity(new Intent(this, UserSearchActivity.class)));
       recordBtn.setOnClickListener(v -> {
           Intent i = new Intent(this, RecordActivity.class);
           i.putExtra("username", username);
           startActivity(i);
       });
       bellBtn.setOnClickListener(v -> {
           Intent i = new Intent(this, UpdateActivity.class);
           i.putExtra("username", username);
           startActivity(i);
       });
       logoutBtn.setOnClickListener(v -> {
           startActivity(new Intent(this, MainActivity.class));
           finish();
       });
   }
}
