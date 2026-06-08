package com.example.madproject;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class IssueActivity extends AppCompatActivity {
   EditText customerName, bookName;
   DatePicker issuePicker, returnPicker;
   Button issueBtn;
   DBHelper db;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_issue);
       bookName = findViewById(R.id.bookName);
       customerName = findViewById(R.id.customerName);
       issuePicker = findViewById(R.id.issuePicker);
       returnPicker = findViewById(R.id.returnPicker);
       issueBtn = findViewById(R.id.issueBtn);
       db = new DBHelper(this);
       String book = getIntent().getStringExtra("book_name");
       if (book != null) {
           bookName.setText(book);
       }
       issueBtn.setOnClickListener(v -> {
           String cust = customerName.getText().toString().trim();
           String bookN = bookName.getText().toString().trim();
           String issueDate = getDate(issuePicker);
           String returnDate = getDate(returnPicker);
           if (cust.isEmpty() || bookN.isEmpty()) {
               Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
               return;
           }
           db.issueBook(cust, bookN, issueDate, returnDate);
           Toast.makeText(this, "Book Issued Successfully", Toast.LENGTH_SHORT).show();
           customerName.setText("");
       });
   }
   private String getDate(DatePicker picker) {
       int day = picker.getDayOfMonth();
       int month = picker.getMonth() + 1;
       int year = picker.getYear();

       return day + "/" + month + "/" + year;
   }
}
