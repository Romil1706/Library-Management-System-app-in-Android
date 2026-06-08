package com.example.madproject;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrebookActivity extends AppCompatActivity {
   EditText bookName;
   DatePicker prebookPicker;
   Button bookBtn;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_prebook);
       bookName = findViewById(R.id.bookName);
       prebookPicker = findViewById(R.id.prebookCalendar);
       bookBtn = findViewById(R.id.bookBtn);
       String book = getIntent().getStringExtra("book_name");
       if (book != null) {
           bookName.setText(book);
       }
       bookBtn.setOnClickListener(v -> {
           String bookText = bookName.getText().toString();
           if (bookText.isEmpty()) {
               Toast.makeText(this, "Enter book name", Toast.LENGTH_SHORT).show();
               return;
           }
           String selectedDate = getDateFromPicker(prebookPicker);
           try {
               SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
               sdf.setLenient(false);
               Date today = new Date();
               Date requiredDate = sdf.parse(selectedDate);
               long diff = requiredDate.getTime() - today.getTime();
               int days = (int)(diff / (1000 * 60 * 60 * 24));
               if (days <= 2 && days >= 0) {
                   Toast.makeText(this, "Pre-book successful", Toast.LENGTH_SHORT).show();
               } else {
                   Toast.makeText(this, "You can only pre-book within 2 days", Toast.LENGTH_SHORT).show();
               }
           } catch (Exception e) {
               Toast.makeText(this, "Error in date", Toast.LENGTH_SHORT).show();
           }
       });
   }
   private String getDateFromPicker(DatePicker picker) {
       int day = picker.getDayOfMonth();
       int month = picker.getMonth() + 1;
       int year = picker.getYear();
       return day + "/" + month + "/" + year;
   }
}
