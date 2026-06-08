package com.example.madproject;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class UserSearchActivity extends AppCompatActivity {
   EditText searchInput;
   Button searchBtn;
   LinearLayout resultLayout;
   DBHelper db;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_search);
       searchInput = findViewById(R.id.searchInput);
       searchBtn = findViewById(R.id.searchBtn);
       resultLayout = findViewById(R.id.resultLayout);
       db = new DBHelper(this);
       searchBtn.setOnClickListener(v -> {
           String text = searchInput.getText().toString().trim();
           Cursor cursor = db.searchBook(text);
           resultLayout.removeAllViews();
           if (cursor.getCount() == 0) {
               Toast.makeText(this, "No Book Found", Toast.LENGTH_SHORT).show();
           } else {
               while (cursor.moveToNext()) {
                   String title = cursor.getString(1);
                   String author = cursor.getString(2);
                   String publisher = cursor.getString(3);
                   TextView tv = new TextView(this);
                   tv.setText(
                           "Title: " + title +
                                   "\nAuthor: " + author +
                                   "\nPublisher: " + publisher
                   );
                   tv.setPadding(20, 30, 20, 10);
                   Button prebookBtn = new Button(this);
                   prebookBtn.setText("Pre-book");
                   prebookBtn.setOnClickListener(btn -> {
                       Intent i = new Intent(UserSearchActivity.this, PrebookActivity.class);
                       i.putExtra("book_name", title);
                       startActivity(i);
                   });
                   Space space = new Space(this);
                   space.setMinimumHeight(20);
                   resultLayout.addView(tv);
                   resultLayout.addView(prebookBtn);
                   resultLayout.addView(space);
               }
           }
           cursor.close();
       });
   }
}
