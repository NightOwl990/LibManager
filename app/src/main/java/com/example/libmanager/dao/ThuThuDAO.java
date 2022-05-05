package com.example.libmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.ThanhVien;
import com.example.libmanager.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase db;

    public ThuThuDAO(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(ThuThu thuThu){
        ContentValues values = new ContentValues();
        values.put("matt", thuThu.getMaTT());
        values.put("hoten", thuThu.getHoTen());
        values.put("matkhau", thuThu.getMatKhau());

        return db.insert("thuthu", null, values);
    }

    public int updatePass(ThuThu thuthu){
        ContentValues values = new ContentValues();
        values.put("hoten", thuthu.getHoTen());
        values.put("matkhau", thuthu.getMatKhau());

        return db.update("thuthu", values, "matt=?", new String[]{String.valueOf(thuthu.getMaTT())});
    }

    public int delete(String id){
        return db.delete("thuthu", "matt=?", new String[]{id});
    }

    public List<ThuThu> getData(String sql, String...selectionArgs){
        List<ThuThu> list = new ArrayList<ThuThu>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            ThuThu thuthu = new ThuThu();
            thuthu.setMaTT(c.getString(c.getColumnIndex("matt")));
            thuthu.setHoTen(c.getString(c.getColumnIndex("hoten")));
            thuthu.setMatKhau(c.getString(c.getColumnIndex("matkhau")));
            Log.i("BBB", thuthu.toString());
            list.add(thuthu);
        }
        return list;
    }

    public List<ThuThu> getAll(){
        String sql = "SELECT * FROM thuthu";
        return getData(sql);
    }

    public ThuThu getID(String id){
        String sql = "SELECT * FROM thuthu WHERE matt=?";
        List<ThuThu> list = getData(sql, id);
        return list.get(0);
    }

    public int checkLogin(String id, String password){
        String sql = "SELECT * FROM thuthu WHERE matt=? AND matkhau=?";
        List<ThuThu> list = getData(sql, id, password);
        if (list.size() == 0){
            return -1;
        }
        return 1;
    }
}
