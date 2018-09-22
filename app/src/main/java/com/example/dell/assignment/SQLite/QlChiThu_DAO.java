package com.example.dell.assignment.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QlChiThu_DAO {

    private SQLiteDatabase database;

    public QlChiThu_DAO(Context context) {
        DBHelper helper = new DBHelper(context);
        database = helper.getWritableDatabase();
    }
    /*INSERT*/

    public void insertLoaithu(String name) {
        ContentValues values = new ContentValues();
        values.put("tenloaithu", name);
        database.insert(DBHelper.TB_LOAITHU, null, values);
    }

    public void insertKhoanthu(String tenloaithu, String tenkhoanthu, Integer soluong, String thoigian) {
        ContentValues values = new ContentValues();
        values.put("tenloaithu", tenloaithu);
        values.put("tenkhoanthu", tenkhoanthu);
        values.put("soluong", soluong);
        values.put("thoigian", thoigian);
        database.insert(DBHelper.TB_KHOANTHU, null, values);

    }

    public void insertLoaichi(String name) {
        ContentValues values = new ContentValues();
        values.put("tenloaichi", name);
        database.insert(DBHelper.TB_LOAICHI, null, values);

    }

    public void insertKhoanChi(String tenloaichi, String tenkhoanchi, Integer soluong, String thoigian) {
        ContentValues values = new ContentValues();
        values.put("tenloaichi", tenloaichi);
        values.put("tenkhoanchi", tenkhoanchi);
        values.put("soluong", soluong);
        values.put("thoigian", thoigian);
        database.insert(DBHelper.TB_KHOANCHI, null, values);

    }

    /*UPDATE*/

    public void updateLoaithu(String name, int id) {
        ContentValues values = new ContentValues();
        values.put("tenloaithu", name);
        database.update(DBHelper.TB_LOAITHU, values, "_id = " + id, null);
    }

    public void updateLoaiChi(String name, int id) {
        ContentValues values = new ContentValues();
        values.put("tenloaichi", name);
        database.update(DBHelper.TB_LOAICHI, values, "_id = " + id, null);
    }

    public void updateKhoanchi(String tenloaichi, String tenkhoanchi, int id, int soluong, String thoigian) {
        ContentValues values = new ContentValues();
        values.put("tenloaichi", tenloaichi);
        values.put("tenkhoanchi", tenkhoanchi);
        values.put("soluong", soluong);
        values.put("thoigian", thoigian);
        database.update(DBHelper.TB_KHOANCHI, values, "_id = " + id, null);
    }

    public void updateKhoanthu(String tenloaithu, String tenkhoanthu, int id, int soluong, String thoigian) {
        ContentValues values = new ContentValues();
        values.put("tenloaithu", tenloaithu);
        values.put("tenkhoanthu", tenkhoanthu);
        values.put("soluong", soluong);
        values.put("thoigian", thoigian);
        database.update(DBHelper.TB_KHOANTHU, values, "_id = " + id, null);
    }

    /*Delete*/
    public void deleteLoaithu(int id) {
        database.delete(DBHelper.TB_LOAITHU, "_id = " + id, null);
    }

    public void deleteLoaichi(int id) {
        database.delete(DBHelper.TB_LOAICHI, "_id = " + id, null);
    }

    public void deleteKhoanthu(int id) {
        database.delete(DBHelper.TB_KHOANTHU, "_id = " + id, null);
    }

    public void deleteKhoanchi(int id) {
        database.delete(DBHelper.TB_KHOANCHI, "_id = " + id, null);
    }

    public Cursor getLoaichi() {
        return database.rawQuery("Select * from loaichi", null);
    }

    public Cursor getLoaithu() {
        return database.rawQuery("Select * from loaithu", null);
    }
    public Cursor getKhoanthu() {
        return database.rawQuery("Select * from khoanthu", null);
    }
    public Cursor getKhoanchi() {
        return database.rawQuery("Select * from khoanchi", null);
    }
    public Cursor getKhoanthuofLoaithu(String tenloaithu) {
        return database.rawQuery("Select * from khoanthu where tenloaithu= '" + tenloaithu + "'", null);
    }

    public Cursor getKhoanthuofLoaichi(String tenloaichi) {
        return database.rawQuery("Select * from khoanchi where tenloaichi= '" + tenloaichi + "'", null);
    }
}
