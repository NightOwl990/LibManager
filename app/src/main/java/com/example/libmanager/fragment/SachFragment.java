package com.example.libmanager.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.libmanager.R;
import com.example.libmanager.adapter.SachAdapter;
import com.example.libmanager.adapter.SpinnerLoaiSachAdapter;
import com.example.libmanager.dao.LoaiSachDAO;
import com.example.libmanager.dao.SachDAO;
import com.example.libmanager.model.LoaiSach;
import com.example.libmanager.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SachFragment extends Fragment {

    ListView lvSach;
    SachDAO sachDAO;
    SachAdapter adapter;
    Sach sach;
    List<Sach> listSach;
    FloatingActionButton flbAddSach;
    Dialog dialog;
    EditText edtMaSach, edtTenSach, edtGiaThue;
    Spinner spinner;
    Button btnSave, btnCancel;

    SpinnerLoaiSachAdapter spinnerLoaiSachAdapter;
    ArrayList<LoaiSach> listLoaiSach;
    LoaiSachDAO loaiSachDAO;
    LoaiSach loaiSach;
    int maLoaiSach, position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        lvSach = view.findViewById(R.id.lv_sach);
        sachDAO = new SachDAO(getActivity());
        flbAddSach = view.findViewById(R.id.flb_add_sach);
        capNhatListSach();

        flbAddSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog(getActivity(), 0);
            }
        });
        lvSach.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sach = listSach.get(position);
                openDialog(getActivity(), 1);
                return false;
            }
        });
        return view;
    }

    void capNhatListSach(){
        listSach = (List<Sach>) sachDAO.getAll();
        adapter = new SachAdapter(getActivity(), this, listSach);
        lvSach.setAdapter(adapter);
    }

    public void xoaSach(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sachDAO.delete(Id);
                capNhatListSach();
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
        dialog.setContentView(R.layout.dialog_sach);
        edtMaSach = dialog.findViewById(R.id.edt_ma_sach);
        edtTenSach = dialog.findViewById(R.id.edt_ten_sach);
        edtGiaThue = dialog.findViewById(R.id.edt_gia_thue);
        spinner = dialog.findViewById(R.id.spinner_loai_sach);
        btnCancel = dialog.findViewById(R.id.btn_cancel_sach);
        btnSave = dialog.findViewById(R.id.btn_save_sach);

        listLoaiSach = new ArrayList<>();
        loaiSachDAO = new LoaiSachDAO(context);
        listLoaiSach = (ArrayList<LoaiSach>) loaiSachDAO.getAll();

        spinnerLoaiSachAdapter = new SpinnerLoaiSachAdapter(context, listLoaiSach);
        spinner.setAdapter(spinnerLoaiSachAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maLoaiSach = listLoaiSach.get(position).getMaLoai();
                Toast.makeText(context, "Chọn " + listLoaiSach.get(position).getTenLoai(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Kiểm tra type insert 0 hay update 1
        edtMaSach.setEnabled(false);
        if (type != 0) {
            edtMaSach.setText(String.valueOf(sach.getMaSach()));
            edtTenSach.setText(sach.getTenSach());
            edtGiaThue.setText(String.valueOf(sach.getGiaThue()));

            for (int i = 0; i < listLoaiSach.size(); i++) {
                if (sach.getMaLoai() == (listLoaiSach.get(i).getMaLoai())) {
                    position = i;
                }
                Log.i("demo", "posSach" + position);
                spinner.setSelection(position);
            }
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
                sach = new Sach();
                sach.setTenSach(edtTenSach.getText().toString());
                sach.setGiaThue(Integer.parseInt(edtGiaThue.getText().toString()));
                sach.setMaLoai(maLoaiSach);
                if (validate() > 0){
                    if (type == 0){
                        // type = 0 -> insert
                        if (sachDAO.insert(sach) > 0){
                            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                        }
                        Log.e("abc", "insert");
                    } else {
                        // type = 1 -> update
                        sach.setMaSach(Integer.parseInt(edtMaSach.getText().toString()));
                        if (sachDAO.update(sach) > 0){
                            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                    capNhatListSach();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    public int validate(){
        int check = 1;
        if (edtTenSach.getText().length() == 0 || edtGiaThue.getText().length() == 0){
            Toast.makeText(getContext(), "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        }
        return check;
    }
}