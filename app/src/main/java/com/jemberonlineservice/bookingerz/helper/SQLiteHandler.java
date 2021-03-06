package com.jemberonlineservice.bookingerz.helper;

/**
 * Created by vmmod on 12/10/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "android_api";

    private static final String TABLE_USER = "user";
    private static final String TABLE_ACARA = "tb_acara";

    private static final String KEY_ID = "id";
    private static final String KEY_IMG = "img_user";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    private static final String KEY_ID_ACARA = "id";
    private static final String KEY_UID_ACARA = "uid";
    private static final String KEY_UID_USER = "uid_user";
    private static final String KEY_IMG_ACARA = "img_acara";
    private static final String KEY_JDL_ACARA = "jdl_acara";
    private static final String KEY_ADMIN_ACARA = "admin_acara";
    private static final String KEY_HRG_TIKET = "hrg_tiket";
    private static final String KEY_TGL_ACARA = "tgl_acara";
    private static final String KEY_JAM_ACARA = "jam_acara";
    private static final String KEY_ACARA_CREATED_AT = "created_at";

    public SQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_IMG + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database berhasil dibuat");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public void addUser(String imguser, String name, String email, String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMG, imguser); // Image
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "User baru dimasukan ke sqlite: " + id);
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("img_user", cursor.getString(1));
            user.put("name", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("uid", cursor.getString(4));
            user.put("created_at", cursor.getString(5));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Mengambil user dari Sqlite: " + user.toString());

        return user;
    }

    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Hapus semua user info pada sqlite");
    }
}
