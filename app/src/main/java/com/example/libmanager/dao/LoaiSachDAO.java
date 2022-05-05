package com.example.libmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.LoaiSach;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {

    private SQLiteDatabase db;

    public LoaiSachDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("tenloai", loaiSach.getTenLoai());

        return db.insert("loaisach", null, values);
    }

    public int update(LoaiSach loaiSach){
        ContentValues values = new ContentValues();
        values.put("tenloai", loaiSach.getTenLoai());

        return db.update("loaisach", values, "maloai=?", new String[]{String.valueOf(loaiSach.getMaLoai())});
    }

    public int delete(String id){
        return db.delete("loaisach", "maloai=?", new String[]{id});
    }

    public List<LoaiSach> getData(String sql, String...selectionArgs){
        List<LoaiSach> list = new ArrayList<LoaiSach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            LoaiSach loaiSach = new LoaiSach();
            loaiSach.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maloai"))));
            loaiSach.setTenLoai(c.getString(c.getColumnIndex("tenloai")));
            list.add(loaiSach);
        }
        return list;
    }

    public List<LoaiSach> getAll(){
        String sql = "SELECT * FROM loaisach";
        return getData(sql);
    }

    public LoaiSach getID(String id){
        String sql = "SELECT * FROM loaisach WHERE maloai=?";
        List<LoaiSach> list = getData(sql, id);
        return list.get(0);
    }
}
