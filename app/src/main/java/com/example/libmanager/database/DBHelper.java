package com.example.libmanager.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "libmanager.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_THUTHU = "thuthu";
    public static final String TABLE_THANH_VIEN = "thanhvien";
    public static final String TABLE_LOAI_SACH = "loaisach";
    public static final String TABLE_SACH = "sach";
    public static final String TABLE_PHIEU_MUON = "phieumuon";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng Thủ thư
        String queryThuThu = "CREATE TABLE " + TABLE_THUTHU + "(" +
                "matt TEXT PRIMARY KEY, " +
                "hoten TEXT NOT NULL, " +
                "matkhau TEXT NOT NULL" + ")";

        // Tạo bảng Thành viên
        String queryThanhVien = "CREATE TABLE " + TABLE_THANH_VIEN + "(" +
                "matv INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hotentv TEXT NOT NULL, " +
                "namsinh TEXT NOT NULL" + ")";

        // Tạo bảng Loại sách
        String queryLoaiSach = "CREATE TABLE " + TABLE_LOAI_SACH + "(" +
                "maloai INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenloai TEXT NOT NULL" + ")";

        // Tạo bảng Sách
        String querySach = "CREATE TABLE " + TABLE_SACH + "(" +
                "masach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tensach TEXT NOT NULL, " +
                "giathue INTEGER NOT NULL, " +
                "maloai INTEGER REFERENCES loaisach(maloai)" + ")";

        // Tạo bảng Phiếu mượn
        String queryPhieuMuon = "CREATE TABLE " + TABLE_PHIEU_MUON + "(" +
                "mapm INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "matt TEXT REFERENCES thuthu(matt), " +
                "matv INTEGER REFERENCES thanhvien(matv), " +
                "masach INTEGER REFERENCES sach(masach), " +
                "tienthue INTEGER NOT NULL, " +
                "ngay DATE NOT NULL, " +
                "trasach INTEGER NOT NULL" + ")";

        sqLiteDatabase.execSQL(queryPhieuMuon);
        sqLiteDatabase.execSQL(querySach);
        sqLiteDatabase.execSQL(queryLoaiSach);
        sqLiteDatabase.execSQL(queryThanhVien);
        sqLiteDatabase.execSQL(queryThuThu);

        sqLiteDatabase.execSQL(DataSQLite.INSERT_THU_THU);
        sqLiteDatabase.execSQL(DataSQLite.INSERT_THANH_VIEN);
        sqLiteDatabase.execSQL(DataSQLite.INSERT_LOAI_SACH);
        sqLiteDatabase.execSQL(DataSQLite.INSERT_SACH);
        sqLiteDatabase.execSQL(DataSQLite.INSERT_PHIEU_MUON);




    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THUTHU);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THANH_VIEN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOAI_SACH);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SACH);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PHIEU_MUON);
        onCreate(sqLiteDatabase);
    }
}
