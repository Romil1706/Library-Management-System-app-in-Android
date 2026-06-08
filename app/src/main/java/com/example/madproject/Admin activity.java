package com.example.madproject;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
   Button searchBtn, fineBtn, logoutBtn, goToUpdateBtn;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_admin);
       searchBtn = findViewById(R.id.searchPageBtn);
       fineBtn = findViewById(R.id.finePageBtn);
       logoutBtn = findViewById(R.id.logoutBtn);
       goToUpdateBtn = findViewById(R.id.goToUpdateBtn);
       searchBtn.setOnClickListener(v ->
               startActivity(new Intent(this, SearchActivity.class)));
       fineBtn.setOnClickListener(v ->
               startActivity(new Intent(this, FineActivity.class)));
       goToUpdateBtn.setOnClickListener(v ->
               startActivity(new Intent(this, SendUpdateActivity.class)));
       logoutBtn.setOnClickListener(v -> {
           startActivity(new Intent(this, MainActivity.class));
           finish();
       });
   }
}
