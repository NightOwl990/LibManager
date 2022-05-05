package com.example.libmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;

public class SachDAO {
    private SQLiteDatabase db;

    public SachDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTenSach());
        values.put("giathue", sach.getGiaThue());
        values.put("maloai", sach.getMaLoai());

        return db.insert("sach", null, values);
    }

    public int update(Sach sach){
        ContentValues values = new ContentValues();
        values.put("tensach", sach.getTenSach());
        values.put("giathue", sach.getGiaThue());
        values.put("maloai", sach.getMaLoai());

        return db.update("sach", values, "masach=?", new String[]{String.valueOf(sach.getMaSach())});
    }

    public int delete(String id){
        return db.delete("sach", "masach=?", new String[]{id});
    }

    public List<Sach> getData(String sql, String...selectionArgs){
        List<Sach> list = new ArrayList<Sach>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            Sach sach = new Sach();
            sach.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("masach"))));
            sach.setTenSach(c.getString(c.getColumnIndex("tensach")));
            sach.setGiaThue(Integer.parseInt(c.getString(c.getColumnIndex("giathue"))));
            sach.setMaLoai(Integer.parseInt(c.getString(c.getColumnIndex("maloai"))));
            list.add(sach);
        }
        return list;
    }

    public List<Sach> getAll(){
        String sql = "SELECT * FROM sach";
        return getData(sql);
    }

    public Sach getID(String id){
        String sql = "SELECT * FROM sach WHERE masach=?";
        List<Sach> list = getData(sql, id);
        return list.get(0);
    }
}
