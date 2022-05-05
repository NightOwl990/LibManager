package com.example.libmanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.libmanager.R;
import com.example.libmanager.adapter.ThanhVienAdapter;
import com.example.libmanager.dao.ThanhVienDAO;
import com.example.libmanager.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class ThanhVienFragment extends Fragment {
    ListView lvThanhVien;
    ArrayList<ThanhVien> list;
    static ThanhVienDAO thanhVienDAO;
    ThanhVienAdapter adapter;
    ThanhVien thanhVien = new ThanhVien();
    FloatingActionButton flbAddThanhVien;
    Dialog dialog;
    TextInputEditText edtMaTV, edtTenTV, edtNamSinh;
    Button btnSave, btnCancel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        lvThanhVien = view.findViewById(R.id.lv_thanh_vien);
        flbAddThanhVien = view.findViewById(R.id.flb_add_thanh_vien);
        thanhVienDAO = new ThanhVienDAO(getActivity());
        capNhatListView();

        flbAddThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Nhấn vào nút thêm thì insert (type = 0)
                openDialog(getActivity(), 0);
            }
        });

        lvThanhVien.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Nhấn giữ vào 1 item trên listview thì sẽ update (type = 1)
                thanhVien = list.get(i);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return view;
    }

    void capNhatListView(){
        list = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        adapter = new ThanhVienAdapter(getActivity(), this, list);
        lvThanhVien.setAdapter(adapter);
    }

    public void xoa(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                thanhVienDAO.delete(Id);
                capNhatListView();
                dialogInterface.cancel();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    protected void openDialog(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_thanh_vien);
        edtMaTV = dialog.findViewById(R.id.edt_ma_tv);
        edtTenTV = dialog.findViewById(R.id.edt_ten_tv);
        edtNamSinh = dialog.findViewById(R.id.edt_nam_sinh);
        btnCancel = dialog.findViewById(R.id.btn_cancel_tv);
        btnSave = dialog.findViewById(R.id.btn_save_tv);

        // Kiểm tra type insert 0 hay update 1
        edtMaTV.setEnabled(false);

        if (type != 0){
            edtMaTV.setText(String.valueOf(thanhVien.getMaTV()));
            edtTenTV.setText(thanhVien.getHoTen());
            edtNamSinh.setText(thanhVien.getNamSinh());
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanhVien.setHoTen(edtTenTV.getText().toString());
                thanhVien.setNamSinh(edtNamSinh.getText().toString());
                if (validate() > 0){
                    if (type == 0){
                        // insert (type = 0)
                        if (thanhVienDAO.insert(thanhVien) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // update (type = 1)
                        thanhVien.setMaTV(Integer.parseInt(edtMaTV.getText().toString()));
                        if (thanhVienDAO.update(thanhVien) > 0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatListView();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int validate(){
        int check = 1;
        if (edtTenTV.getText().length() == 0 || edtNamSinh.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}