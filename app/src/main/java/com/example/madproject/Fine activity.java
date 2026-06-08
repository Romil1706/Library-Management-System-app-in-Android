package com.example.madproject;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FineActivity extends AppCompatActivity {
   Button checkBtn;
   LinearLayout resultLayout;
   DBHelper db;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_fine);
       checkBtn = findViewById(R.id.checkBtn);
       resultLayout = findViewById(R.id.resultLayout);
       db = new DBHelper(this);
       checkBtn.setOnClickListener(v -> {
           resultLayout.removeAllViews();
           SQLiteDatabase database = db.getReadableDatabase();
           Cursor cursor = database.rawQuery(
                   "SELECT * FROM issued WHERE status='issued' AND finePaid='no'",
                   null
           );
           SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
           Date today = new Date();
           boolean found = false;
           while (cursor.moveToNext()) {
               int id = cursor.getInt(0);
               String customer = cursor.getString(1);
               String book = cursor.getString(2);
               String returnDateStr = cursor.getString(4);
               try {
                   Date returnDate = sdf.parse(returnDateStr);
                   if (today.after(returnDate)) {
                       found = true;
                       long diff = today.getTime() - returnDate.getTime();
                       int daysLate = (int)(diff / (1000 * 60 * 60 * 24));
                       int fine = daysLate * 10;
                       TextView tv = new TextView(this);
                       tv.setText(
                               "Customer: " + customer +
                                       "\nBook: " + book +
                                       "\nDays Late: " + daysLate +
                                       "\nFine: ₹" + fine
                       );
                       tv.setPadding(20, 20, 20, 10);
                       Button payBtn = new Button(this);
                       payBtn.setText("Paid");
                       payBtn.setOnClickListener(btn -> {
                           db.markFinePaid(id);
                           Toast.makeText(this, "Fine marked as paid", Toast.LENGTH_SHORT).show();
                           resultLayout.removeView(tv);
                           resultLayout.removeView(payBtn);
                       });
                       Space space = new Space(this);
                       space.setMinimumHeight(20);
                       resultLayout.addView(tv);
                       resultLayout.addView(payBtn);
                       resultLayout.addView(space);
                   }
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
           if (!found) {
               TextView tv = new TextView(this);
               tv.setText("No late books");
               resultLayout.addView(tv);
           }
           cursor.close();
       });
   }
}
