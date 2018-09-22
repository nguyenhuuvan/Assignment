package com.example.dell.assignment.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "QuanLiThuChi";
    public static final String TB_LOAITHU = "loaithu";
    public static final String TB_KHOANTHU = "khoanthu";
    public static final String TB_LOAICHI = "loaichi";
    public static final String TB_KHOANCHI = "khoanchi";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String loaithu = "CREATE TABLE IF NOT EXISTS loaithu(_id INTEGER PRIMARY KEY AUTOINCREMENT,tenloaithu text)";
        String khoanthu = "CREATE TABLE IF NOT EXISTS khoanthu(_id INTEGER PRIMARY KEY AUTOINCREMENT,tenloaithu Text,tenkhoanthu TEXT,soluong Integer,thoigian text)";
        String loaichi = "CREATE TABLE IF NOT EXISTS loaichi(_id INTEGER PRIMARY KEY AUTOINCREMENT,tenloaichi text)";
        String khoanchi = "CREATE TABLE IF NOT EXISTS khoanchi(_id INTEGER PRIMARY KEY AUTOINCREMENT,tenloaichi text,tenkhoanchi TEXT,soluong Integer,thoigian text)";
        db.execSQL(loaithu);
        db.execSQL(khoanthu);
        db.execSQL(loaichi);
        db.execSQL(khoanchi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS loaithu");
        db.execSQL("DROP TABLE IF EXISTS khoanthu");
        db.execSQL("DROP TABLE IF EXISTS loaichi");
        db.execSQL("DROP TABLE IF EXISTS khoanchi");
        onCreate(db);
    }
}
