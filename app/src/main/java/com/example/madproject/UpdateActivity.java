package com.example.madproject;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateActivity extends AppCompatActivity {
   TextView result;
   DBHelper db;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_update);
       result = findViewById(R.id.result);
       db = new DBHelper(this);
       SQLiteDatabase database = db.getReadableDatabase();
       StringBuilder data = new StringBuilder();
       Cursor updateCursor = database.rawQuery("SELECT * FROM updates ORDER BY id DESC", null);
       while (updateCursor.moveToNext()) {
           String message = updateCursor.getString(1);
           data.append("ADMIN UPDATE\n")
                   .append(message)
                   .append("\n----------------------\n");
       }
       updateCursor.close();
       String user = getIntent().getStringExtra("username");
       Cursor cursor = database.rawQuery(
               "SELECT * FROM issued WHERE customer = ?",
               new String[]{user}
       );
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
       Date today = new Date();
       while(cursor.moveToNext()) {
           String book = cursor.getString(2);
           String returnDateStr = cursor.getString(4);
           try {
               Date returnDate = sdf.parse(returnDateStr);
               if(today.after(returnDate)) {
                   long diff = today.getTime() - returnDate.getTime();
                   int daysLate = (int)(diff / (1000 * 60 * 60 * 24));
                   int fine = daysLate * 10;
                   data.append("⚠ OVERDUE\n")
                           .append("Book: ").append(book)
                           .append("\nDays Late: ").append(daysLate)
                           .append("\nFine: ₹").append(fine)
                           .append("\n----------------------\n");
               } else {
                   long diff = returnDate.getTime() - today.getTime();
                   int daysLeft = (int)(diff / (1000 * 60 * 60 * 24));
                   if(daysLeft <= 2) {
                       data.append("⏳ REMINDER\n")
                               .append("Book: ").append(book)
                               .append("\nReturn in ").append(daysLeft).append(" day(s)")
                               .append("\n----------------------\n");
                   }
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
       cursor.close();
       if(data.length() == 0) {
           result.setText("No updates");
       } else {
           result.setText(data.toString());

           sendNotification("You have new library updates");
       }
   }
   private void sendNotification(String message) {
       String channelId = "updates";
       NotificationManager manager =
               (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           NotificationChannel channel = new NotificationChannel(
                   channelId,
                   "Library Updates",
                   NotificationManager.IMPORTANCE_HIGH
           );
           manager.createNotificationChannel(channel);
       }
       NotificationCompat.Builder builder =
               new NotificationCompat.Builder(this, channelId)
                       .setContentTitle("Library Notification 📚")
                       .setContentText(message)
                       .setSmallIcon(android.R.drawable.ic_dialog_info)
                       .setAutoCancel(true);

       manager.notify(1, builder.build());
   }
}
