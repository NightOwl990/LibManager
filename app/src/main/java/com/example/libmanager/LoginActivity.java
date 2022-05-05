package com.example.libmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.libmanager.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassword;
    Button btnLogin, btnCancel;
    CheckBox chkRememberPass;
    String strUser, strPass;
    ThuThuDAO thuThuDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edtUserName = findViewById(R.id.edt_user_name);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnCancel = findViewById(R.id.btn_cancel);
        chkRememberPass = findViewById(R.id.chk_remember_pass);
        thuThuDAO = new ThuThuDAO(this);

        // Đọc user, pass trong SharePreferences
        // Khai báo SharedPreferences và tạo 1 file lưu trữ có tên là USER_FILE, với biến MODE_PRIVATE là kiểu int mặc định
        SharedPreferences preferences = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = preferences.getString("USERNAME", "");
        String pass = preferences.getString("PASSWORD", "");
        Boolean rem = preferences.getBoolean("REMEMBER", false);

        edtUserName.setText(user);
        edtPassword.setText(pass);
        chkRememberPass.setChecked(rem);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtUserName.setText("");
                edtPassword.setText("");

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

    }

    public void rememberUser(String u, String p, boolean status){

        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){
            // Xóa trạng thái lưu trữ trước đó
            editor.clear();
        } else {
            // Lưu dữ liệu
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        // Lưu lại toàn bộ
        editor.commit();
    }

    public void checkLogin(){

        // 'matt' và 'matkhau' trong bảng thuthu là hai trường sẽ được lấy làm TK, MK đăng nhập
        strUser = edtUserName.getText().toString();
        strPass = edtPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()){
            Toast.makeText(this, "Tên đăng nhập và mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
        } else {
            if (thuThuDAO.checkLogin(strUser, strPass) > 0){
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRememberPass.isChecked());
                Intent i = new Intent(this, MainActivity.class);
                i.putExtra("user", strUser);  // Gửi 'matt' sang MainActivity với key là 'user'. Mục đích là để lấy được 'matt', từ đó tham chiếu với bảng 'thuthu' ta sẽ lấy được 'hoten' để hiển thị lên header
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
            }
        }
    }

}