package com.example.madproject;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
   public DBHelper(Context context) {
       super(context, "Library.db", null, 4);
   }
   @Override
   public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE users(" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT," +
               "username TEXT UNIQUE," +
               "password TEXT," +
               "role TEXT)");
       db.execSQL("CREATE TABLE books(" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT," +
               "name TEXT," +
               "author TEXT," +
               "publisher TEXT)");
       db.execSQL("CREATE TABLE issued(" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT," +
               "customer TEXT," +
               "book TEXT," +
               "issueDate TEXT," +
               "returnDate TEXT," +
               "status TEXT," +
               "finePaid TEXT)");
       db.execSQL("CREATE TABLE updates(" +
               "id INTEGER PRIMARY KEY AUTOINCREMENT," +
               "message TEXT)");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Java Programming','James Gosling','Oracle')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Python Basics','Guido','OReilly')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Classical Mythology','Mark P. O. Morford','Oxford University Press')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Clara Callan','Richard Bruce Wright','HarperFlamingo Canada')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Decision in Normandy','Carlo D''Este','HarperPerennial')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Flu: The Story of the Great Influenza Pandemic of 1918 and the Search for the Virus That Caused It','Gina Bari Kolata','Farrar Straus Giroux')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('The Mummies of Urumchi','E. J. W. Barber','W. W. Norton & Company')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('The Kitchen God''s Wife','Amy Tan','Putnam Pub Group')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('What If?: The World''s Foremost Military Historians Imagine What Might Have Been','Robert Cowley','Berkley Publishing Group')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('PLEADING GUILTY','Scott Turow','Audioworks')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Under the Black Flag: The Romance and the Reality of Life Among the Pirates','David Cordingly','Random House')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Where You''ll Find Me: And Other Stories','Ann Beattie','Scribner')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Nights Below Station Street','David Adams Richards','Emblem Editions')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Hitler''s Secret Bankers: The Myth of Swiss Neutrality During the Holocaust','Adam Lebor','Citadel Press')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('The Middle Stories','Sheila Heti','House of Anansi Press')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Jane Doe','R. J. Kaiser','Mira Books')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('A Second Chicken Soup for the Woman''s Soul','Jack Canfield','Health Communications')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('The Witchfinder','Loren D. Estleman','Brilliance Audio')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('More Cunning Than Man','Robert Hendrickson','Kensington Publishing')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Goodbye to the Buttermilk Sky','Julia Oliver','River City Pub')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('The Testament','John Grisham','Dell')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Beloved','Toni Morrison','Plume')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Our Dumb Century','The Onion','Three Rivers Press')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('New Vegetarian','Celia Brooks Brown','Ryland Peters & Small')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('If I''d Known Then What I Know Now','J. R. Parrish','Cypress House')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Mary-Kate & Ashley Switching Goals','Mary-Kate & Ashley Olsen','HarperEntertainment')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Tell Me This Isn''t Happening','Robynn Clairday','Scholastic')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Flood : Mississippi 1927','Kathleen Duey','Aladdin')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Wild Animus','Rich Shapero','Too Far')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Airframe','Michael Crichton','Ballantine Books')");
       db.execSQL("INSERT INTO books(name, author, publisher) VALUES('Timeline','Michael Crichton','Ballantine Books')");
   }
   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS users");
       db.execSQL("DROP TABLE IF EXISTS books");
       db.execSQL("DROP TABLE IF EXISTS issued");
       db.execSQL("DROP TABLE IF EXISTS updates");
       onCreate(db);
   }
   public boolean registerUser(String username, String password, String role) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("username", username);
       cv.put("password", password);
       cv.put("role", role);
       long result = db.insert("users", null, cv);
       return result != -1;
   }
   public boolean checkUser(String username, String password, String role) {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery(
               "SELECT * FROM users WHERE username=? AND password=? AND role=?",
               new String[]{username, password, role}
       );
       boolean exists = c.getCount() > 0;
       c.close();
       return exists;
   }
   public void issueBook(String customer, String book, String issueDate, String returnDate) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("customer", customer);
       cv.put("book", book);
       cv.put("issueDate", issueDate);
       cv.put("returnDate", returnDate);
       cv.put("status", "issued");
       cv.put("finePaid", "no");
       db.insert("issued", null, cv);
   }
   public void markReturned(int id) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("status", "returned");
       db.update("issued", cv, "id=?", new String[]{String.valueOf(id)});
   }
   public void markFinePaid(int id) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("finePaid", "yes");
       db.update("issued", cv, "id=?", new String[]{String.valueOf(id)});
   }
   public Cursor getAllIssued() {
       SQLiteDatabase db = this.getReadableDatabase();
       return db.rawQuery("SELECT * FROM issued", null);
   }
   public Cursor getUserRecords(String username) {
       SQLiteDatabase db = this.getReadableDatabase();
       return db.rawQuery(
               "SELECT * FROM issued WHERE customer=?",
               new String[]{username}
       );
   }
   public void insertUpdate(String message) {
       SQLiteDatabase db = this.getWritableDatabase();
       ContentValues cv = new ContentValues();
       cv.put("message", message);
       db.insert("updates", null, cv);
   }
   public Cursor searchBook(String text) {
       SQLiteDatabase db = this.getReadableDatabase();
       return db.rawQuery(
               "SELECT * FROM books WHERE name LIKE ? OR author LIKE ?",
               new String[]{"%" + text + "%", "%" + text + "%"}
       );
   }
   public boolean isBooksEmpty() {
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor c = db.rawQuery("SELECT * FROM books", null);
       boolean empty = (c.getCount() == 0);
       c.close();
       return empty;
   }
}
