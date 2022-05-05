package com.example.libmanager.database;

public class DataSQLite {

    // Tạo dữ liệu có sẵn
    public static final String INSERT_THU_THU = "INSERT INTO thuthu(matt, hoten, matkhau) VALUES " +
            "('admin', 'Nguyen Admin', '123'), " +
            "('namnv', 'Nguyen Van Nam', '123456'), " +
            "('teonv', 'Nguyen Van Teo', '123456'), " +
            "('nott', 'Tran Thi No', '123456')";

    public static final String INSERT_THANH_VIEN = "INSERT INTO thanhvien(hotentv, namsinh) VALUES " +
            "('Nguyễn Văn Chính', '2000'), " +
            "('Hoàng Ngọc Huy', '2000'), " +
            "('Phan Thanh Tú', '2000'), " +
            "('Nguyễn Phương Hảo', '1998'), " +
            "('Lê Quang Huy', '2000'), " +
            "('Nguyễn Văn Cường', '2000')";

    public static final String INSERT_LOAI_SACH = "INSERT INTO loaisach(tenloai) VALUES " +
            "('Tiếng Anh cơ bản'), " +
            "('Tiếng Anh nâng cao'), " +
            "('Lập trình cơ bản'), " +
            "('Tiếng Anh Android'), " +
            "('Tiếng Anh Java'), " +
            "('Tiếng Anh Web')";
    public static final String INSERT_SACH = "INSERT INTO sach(tensach, giathue, maloai) VALUES " +
            "('Lập trình Java cơ bản', '2000', '5'), " +
            "('Lập trình Java nâng cao', '2000', '5'), " +
            "('Lập trình mạng với Java', '2000', '5'), " +
            "('Lập trình JDBC', '2000', '3'), " +
            "('Lập trình Spring MVC', '2000', '1'), " +
            "('Lập trình MS.NET MVC', '2000', '4'), " +
            "('Thiết kế Web với HTML và CSS', '2000', '6'), " +
            "('Lập trình Font-end với JavaScript và jQuery', '2000', '6'), " +
            "('Lập trình cơ sở dữ liệu Hibernate', '2000', '2'), " +
            "('Lập trình hướng đối tượng', '2000', '3'), " +
            "('Cơ sở dữ liệu SQL Server', '2000', '1'), " +
            "('Dự án với WindowForm', '2000', '4'), " +
            "('Lập trình Desktop với Swing', '2000', '2')";
    public static final String INSERT_PHIEU_MUON = "INSERT INTO phieumuon(matt, matv, masach, tienthue, ngay, trasach) VALUES " +
            "('admin', '1', '1', '2000', '20/4/2022', 1), " +
            "('admin', '4', '4', '2500', '21/4/2022', 0), " +
            "('admin', '2', '2', '3000', '22/4/2022', 1), " +
            "('admin', '3', '3', '3200', '23/4/2022', 0), " +
            "('admin', '6', '6', '4000', '24/4/2022', 1), " +
            "('admin', '9', '9', '2000', '25/4/2022', 0), " +
            "('admin', '5', '5', '3500', '26/4/2022', 1), " +
            "('admin', '7', '7', '3000', '27/4/2022', 0), " +
            "('admin', '8', '8', '2000', '28/4/2022', 1)";

}
