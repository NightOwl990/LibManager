package com.example.libmanager.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.libmanager.R;
import com.example.libmanager.dao.ThuThuDAO;
import com.example.libmanager.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePassFragment extends Fragment {
    TextInputEditText edtPassOld, edtPassNew, edtRePass;
    Button btnSave, btnCancel;
    ThuThuDAO thuThuDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        thuThuDAO = new ThuThuDAO(getActivity());
        edtPassOld = view.findViewById(R.id.edt_pass_old);
        edtPassNew = view.findViewById(R.id.edt_pass_new);
        edtRePass = view.findViewById(R.id.edt_repass_change);
        btnSave = view.findViewById(R.id.btn_save_user_change);
        btnCancel = view.findViewById(R.id.btn_cancel_user_change);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtPassOld.setText("");
                edtPassNew.setText("");
                edtRePass.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = pref.getString("USERNAME", "");
                if (validate() > 0){
                    ThuThu thuthu = thuThuDAO.getID(user);
                    thuthu.setMatKhau(edtPassNew.getText().toString());
//                    thuThuDAO.updatePass(thuthu);
                    if (thuThuDAO.updatePass(thuthu) > 0){
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edtPassOld.setText("");
                        edtPassNew.setText("");
                        edtRePass.setText("");
                    } else {
                        Toast.makeText(getActivity(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int validate(){
        int check = 1;
        if (edtPassOld.getText().length() == 0 || edtPassNew.getText().length() == 0 || edtRePass.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            // Đọc user, pass trong SharePreferences
            @SuppressLint("WrongConstant") SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passOld = preferences.getString("PASSWORD", "");
            String passNew = edtPassNew.getText().toString();
            String rePass = edtRePass.getText().toString();
            if (!passOld.equals(edtPassOld.getText().toString())){
                Toast.makeText(getContext(), "Mật khẩu cũ sai", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!passNew.equals(rePass)){
                Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}