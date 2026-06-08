package com.example.madproject;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {
   TextView result;
   DBHelper db;
   String username;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_record);
       result = findViewById(R.id.result);
       db = new DBHelper(this);
       username = getIntent().getStringExtra("username");
       SQLiteDatabase database = db.getReadableDatabase();
       Cursor cursor = database.rawQuery(
               "SELECT * FROM issued WHERE customer = ?",
               new String[]{username}
       );
       StringBuilder data = new StringBuilder();
       while(cursor.moveToNext()) {
           String book = cursor.getString(2);
           String issueDate = cursor.getString(3);
           String returnDate = cursor.getString(4);
           data.append("Book: ").append(book)
                   .append("\nIssued: ").append(issueDate)
                   .append("\nReturn: ").append(returnDate)
                   .append("\n----------------------\n");
       }
       if(data.length() == 0) {
           result.setText("No records found");
       } else {
           result.setText(data.toString());
       }
       cursor.close();
   }
}
