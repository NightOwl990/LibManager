package com.example.libmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libmanager.dao.DemoDB;
import com.example.libmanager.dao.PhieuMuonDAO;
import com.example.libmanager.dao.ThuThuDAO;
import com.example.libmanager.fragment.ChangePassFragment;
import com.example.libmanager.fragment.DoanhThuFragment;
import com.example.libmanager.fragment.PhieuMuonFragment;
import com.example.libmanager.fragment.SachFragment;
import com.example.libmanager.fragment.ThanhVienFragment;
import com.example.libmanager.fragment.TopFragment;
import com.example.libmanager.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    View mHeaderView;
    TextView tvUser;
    PhieuMuonDAO phieuMuonDAO;
    ThuThuDAO thuThuDAO;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tạo database và kiểm tra
//        DemoDB demoDB = new DemoDB(getApplicationContext());
//        demoDB.thanhVien();
//        demoDB.thuThu();

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar1);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.menu);
        ab.setHomeButtonEnabled(true);

        // Khi đăng nhập thành công, màn hình sẽ hiển thị luôn fragment phiếu mượn
        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fl_content, phieuMuonFragment).commit();

        NavigationView nv = findViewById(R.id.nv_view);

        // Hiển thị tên người dùng trong header
        mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tv_User);
        Intent i = getIntent();
        String user = i.getStringExtra("user");   // lấy 'matt' từ LoginActivity thông qua key là 'user'
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuthu = thuThuDAO.getID(user);
        String username = thuthu.getHoTen();            // Tham chiếu với bảng 'thuthu' ta lấy được 'hoten'
        tvUser.setText("Welcom " + username + "!");     // Hiện thị lên header với 'hoten' ta vừa tham chiếu được

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                switch (item.getItemId()){
                    case R.id.nav_phieu_muon:
                        setTitle("Quản lý phiếu mượn");
                        PhieuMuonFragment phieuMuonFragment = new PhieuMuonFragment();
                        manager.beginTransaction().replace(R.id.fl_content, phieuMuonFragment).commit();
                        Toast.makeText(MainActivity.this, "Quản lý phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_loai_sach:
                        setTitle("Quản lý loại sách");
                        Toast.makeText(MainActivity.this, "Quản lý loại sách", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_sach:
                        setTitle("Quản lý sách");
                        SachFragment sachFragment = new SachFragment();
                        manager.beginTransaction().replace(R.id.fl_content, sachFragment).commit();
                        Toast.makeText(MainActivity.this, "Quản lý sách", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_thanh_vien:
                        setTitle("Quản lý thành viên");
                        ThanhVienFragment thanhVienFragment = new ThanhVienFragment();
                        manager.beginTransaction().replace(R.id.fl_content, thanhVienFragment).commit();
                        break;
                    case R.id.sub_top:
                        setTitle("Top 10 sách cho thuê nhiều nhất");
                        TopFragment topFragment = new TopFragment();
                        manager.beginTransaction().replace(R.id.fl_content, topFragment).commit();
                        break;
                    case R.id.sub_doanh_thu:
                        setTitle("Thống kê doanh thu");
                        DoanhThuFragment doanhThuFragment = new DoanhThuFragment();
                        manager.beginTransaction().replace(R.id.fl_content, doanhThuFragment).commit();
                        break;
                    case R.id.sub_add_user:
                        setTitle("Thêm người dùng");
                        break;
                    case R.id.sub_pass:
                        setTitle("Thay đổi mật khẩu");
                        ChangePassFragment changePassFragment = new ChangePassFragment();
                        manager.beginTransaction().replace(R.id.fl_content, changePassFragment).commit();
                        break;
                    case R.id.sub_logout:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawers();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}