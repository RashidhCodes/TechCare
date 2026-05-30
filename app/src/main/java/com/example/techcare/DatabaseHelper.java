package com.example.techcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME    = "techcare.db";
    private static final int    DB_VERSION = 1;

    // Users table
    public static final String TABLE_USERS  = "users";
    public static final String COL_USER_ID  = "id";
    public static final String COL_NAME     = "full_name";
    public static final String COL_EMAIL    = "email";
    public static final String COL_PHONE    = "phone";
    public static final String COL_PASSWORD = "password";

    // Bookings table
    public static final String TABLE_BOOKINGS  = "bookings";
    public static final String COL_BOOKING_ID  = "booking_id";
    public static final String COL_DEVICE      = "device";
    public static final String COL_FAULT       = "fault_description";
    public static final String COL_LOGISTICS   = "logistics";
    public static final String COL_DATE        = "appointment_date";
    public static final String COL_STATUS      = "status";
    public static final String COL_USER_FK     = "user_id";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME     + " TEXT NOT NULL, " +
                COL_EMAIL    + " TEXT UNIQUE NOT NULL, " +
                COL_PHONE    + " TEXT NOT NULL, " +
                COL_PASSWORD + " TEXT NOT NULL)");

        // Create bookings table
        db.execSQL("CREATE TABLE " + TABLE_BOOKINGS + " (" +
                COL_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USER_FK    + " INTEGER, " +
                COL_DEVICE     + " TEXT NOT NULL, " +
                COL_FAULT      + " TEXT NOT NULL, " +
                COL_LOGISTICS  + " TEXT NOT NULL, " +
                COL_DATE       + " TEXT NOT NULL, " +
                COL_STATUS     + " TEXT DEFAULT 'Request Registered')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    // Register new user
    public long registerUser(String name, String email,
                             String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COL_NAME,     name);
        cv.put(COL_EMAIL,    email);
        cv.put(COL_PHONE,    phone);
        cv.put(COL_PASSWORD, password);
        return db.insert(TABLE_USERS, null, cv);
    }

    // Check login credentials
    public Cursor loginUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS +
                        " WHERE email=? AND password=?",
                new String[]{email, password});
    }

    // Check if email already exists
    public boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_USERS +
                " WHERE email=?", new String[]{email});
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    // Add new booking
    public long addBooking(int userId, String device, String fault,
                           String logistics, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv  = new ContentValues();
        cv.put(COL_USER_FK,   userId);
        cv.put(COL_DEVICE,    device);
        cv.put(COL_FAULT,     fault);
        cv.put(COL_LOGISTICS, logistics);
        cv.put(COL_DATE,      date);
        return db.insert(TABLE_BOOKINGS, null, cv);
    }

    // Get all bookings for a user
    public Cursor getUserBookings(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_BOOKINGS +
                        " WHERE user_id=?",
                new String[]{String.valueOf(userId)});
    }
}