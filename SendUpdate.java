package com.example.madproject;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class SendUpdateActivity extends AppCompatActivity {
   EditText messageInput;
   Button sendBtn;
   DBHelper db;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_send_update);
       messageInput = findViewById(R.id.messageInput);
       sendBtn = findViewById(R.id.sendBtn);
       db = new DBHelper(this);
       sendBtn.setOnClickListener(v -> {
           String msg = messageInput.getText().toString().trim();
           if (msg.isEmpty()) {
               Toast.makeText(this, "Enter message", Toast.LENGTH_SHORT).show();
               return;
           }
           db.insertUpdate(msg);
           Toast.makeText(this, "Update Sent", Toast.LENGTH_SHORT).show();
           messageInput.setText("");
       });
   }
}
