package com.example.libmanager.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.libmanager.database.DBHelper;
import com.example.libmanager.model.ThanhVien;
import com.example.libmanager.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class DemoDB {
    private SQLiteDatabase db;
    ThanhVienDAO thanhVienDAO;
    ThuThuDAO thuThuDAO;

    public DemoDB(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        thanhVienDAO = new ThanhVienDAO(context);
        thuThuDAO = new ThuThuDAO(context);
    }
    public void thanhVien(){
        List<ThanhVien> ls = new ArrayList<>();
        ThanhVien tv = new ThanhVien(1, "Nguyễn Văn Chính", "2000");
        if (thanhVienDAO.insert(tv) > 0){
            Log.i("AAA", "Thêm Thành Viên Thành Công");
        } else {
            Log.i("AAA", "Thêm Thành Viên Thất Bại");
        }

        Log.i("AAA", "Tổng số thành viên: " + thanhVienDAO.getAll().size());



        tv = new ThanhVien(1, "Vip Pro", "1999");
        thanhVienDAO.update(tv);
        Log.i("AAA", "Tổng số thành viên: " + thanhVienDAO.getAll().size());


        thanhVienDAO.delete("1");
        Log.i("AAA", "Tổng số thành viên: " + thanhVienDAO.getAll().size());
    }

    public void thuThu(){
        // Test insert thủ thư
        ThuThu tt = new ThuThu("admin", "Nguyễn Admin", "admin123");
        thuThuDAO.insert(tt);
        Log.i("BBB", "Tổng số thành viên: " + thuThuDAO.getAll().size());

        // Test updatePass thủ thư
        tt = new ThuThu("admin", "Trần Admin", "123456");
        thuThuDAO.updatePass(tt);
        Log.i("BBB", "Tổng số thành viên: " + thuThuDAO.getAll().size());

        // Test delete thủ thư
//        thuThuDAO.delete("admin");
//        Log.i("BBB", "Tổng số thành viên: " + thuThuDAO.getAll().size());

        // Test login thủ thư
        if (thuThuDAO.checkLogin("admin", "123456") > 0){
            Log.i("BBB", "Đăng nhập thành công");
        } else {
            Log.i("BBB", "Đăng nhập không thành công");
        }
    }
}
