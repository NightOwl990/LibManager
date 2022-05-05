package com.example.libmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    private SQLiteDatabase db;

    public ThanhVienDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hotentv", thanhVien.getHoTen());
        values.put("namsinh", thanhVien.getNamSinh());

        return db.insert("thanhvien", null, values);
    }

    public int update(ThanhVien thanhVien){
        ContentValues values = new ContentValues();
        values.put("hotentv", thanhVien.getHoTen());
        values.put("namsinh", thanhVien.getNamSinh());

        return db.update("thanhvien", values, "matv=?", new String[]{String.valueOf(thanhVien.getMaTV())});
    }

    public int delete(String id){
        return db.delete("thanhvien", "matv=?", new String[]{id});
    }

    public List<ThanhVien> getData(String sql, String...selectionArgs){
        List<ThanhVien> list = new ArrayList<ThanhVien>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThanhVien thanhVien = new ThanhVien();
            thanhVien.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("matv"))));
            thanhVien.setHoTen(c.getString(c.getColumnIndex("hotentv")));
            thanhVien.setNamSinh(c.getString(c.getColumnIndex("namsinh")));
            Log.i("AAA", thanhVien.toString());
            list.add(thanhVien);
        }
        return list;
    }

    public List<ThanhVien> getAll(){
        String sql = "SELECT * FROM thanhvien";
        return getData(sql);
    }

    public ThanhVien getID(String id){
        String sql = "SELECT * FROM thanhvien WHERE matv=?";
        List<ThanhVien> list = getData(sql, id);
        return list.get(0);

    }
}
