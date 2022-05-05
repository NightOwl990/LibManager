package com.example.libmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.PhieuMuon;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;
import com.example.libmanager.model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PhieuMuonDAO {
    private SQLiteDatabase db;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonDAO(Context context) {
        this.context = context;
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insert(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("matt", phieuMuon.getMaTT());
        values.put("matv", phieuMuon.getMaTV());
        values.put("masach", phieuMuon.getMaSach());
        values.put("ngay", sdf.format(phieuMuon.getNgay()));
        values.put("tienthue", phieuMuon.getTienThue());
        values.put("trasach", phieuMuon.getTraSach());

        return db.insert("phieumuon", null, values);
    }

    public int update(PhieuMuon phieuMuon){
        ContentValues values = new ContentValues();
        values.put("matt", phieuMuon.getMaTT());
        values.put("matv", phieuMuon.getMaTV());
        values.put("masach", phieuMuon.getMaSach());
        values.put("ngay", sdf.format(phieuMuon.getNgay()));
        values.put("tienthue", phieuMuon.getTienThue());
        values.put("trasach", phieuMuon.getTraSach());

        return db.update("phieumuon", values, "mapm=?", new String[]{String.valueOf(phieuMuon.getMaPM())});
    }

    public int delete(String id){
        return db.delete("phieumuon", "mapm=?", new String[]{id});
    }

    public List<PhieuMuon> getData(String sql, String...selectionArgs){
        List<PhieuMuon> list = new ArrayList<PhieuMuon>();
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            PhieuMuon phieuMuon = new PhieuMuon();
            phieuMuon.setMaPM(Integer.parseInt(c.getString(c.getColumnIndex("mapm"))));
            phieuMuon.setMaTT(c.getString(c.getColumnIndex("matt")));
            phieuMuon.setMaTV(Integer.parseInt(c.getString(c.getColumnIndex("matv"))));
            phieuMuon.setMaSach(Integer.parseInt(c.getString(c.getColumnIndex("masach"))));
            phieuMuon.setTienThue(Integer.parseInt(c.getString(c.getColumnIndex("tienthue"))));
            try {
                phieuMuon.setNgay(sdf.parse(c.getString(c.getColumnIndex("ngay"))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            phieuMuon.setTraSach(Integer.parseInt(c.getString(c.getColumnIndex("trasach"))));
            list.add(phieuMuon);
        }
        return list;
    }

    public List<PhieuMuon> getAll(){
        String sql = "SELECT * FROM phieumuon";
        return getData(sql);
    }

    public PhieuMuon getID(String id){
        String sql = "SELECT * FROM phieumuon WHERE mapm=?";
        List<PhieuMuon> list = getData(sql, id);
        return list.get(0);
    }

    public List<Top> getTop(){
        String sqlTop = "SELECT masach, count(masach) as soluong FROM phieumuon GROUP BY masach ORDER BY soluong DESC LIMIT 10";
        List<Top> list = new ArrayList<Top>();
        SachDAO sachDAO = new SachDAO(context);
        Cursor c = db.rawQuery(sqlTop, null);

        while (c.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getID(c.getString(c.getColumnIndex("masach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(c.getString(c.getColumnIndex("soluong"))));
            list.add(top);
        }
        return list;
    }

    public int getDoanhThu(String tuNgay, String denNgay){
        String sqlDoanhThu = "SELECT SUM(tienthue) as doanhthu FROM phieumuon WHERE ngay BETWEEN ? AND ?";
        List<Integer> list = new ArrayList<Integer>();
        Cursor c = db.rawQuery(sqlDoanhThu, new String[]{tuNgay, denNgay});

        while (c.moveToNext()){
            try{
                list.add(Integer.parseInt(c.getString(c.getColumnIndex("doanhthu"))));
            } catch (Exception e){
                list.add(0);
            }

        }

        return list.get(0);
    }
}
