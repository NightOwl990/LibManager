package com.example.libmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libmanager.R;
import com.example.libmanager.dao.LoaiSachDAO;
import com.example.libmanager.fragment.SachFragment;
import com.example.libmanager.model.LoaiSach;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class SachAdapter extends ArrayAdapter<Sach> {
    Context context;
    SachFragment fragment;
    List<Sach> list;
    TextView tvMaSach, tvTenSach, tvGiaThue, tvLoaiSach;
    ImageView imgDeleteSach;

    public SachAdapter(@NonNull Context context, SachFragment fragment, List<Sach> list) {
        super(context, 0, list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_sach, null);
        }

        final Sach sach = list.get(position);
        if (sach != null){
            LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
            LoaiSach loaiSach = loaiSachDAO.getID(String.valueOf(sach.getMaLoai()));
            tvMaSach = view.findViewById(R.id.tv_ma_sach);
            tvTenSach = view.findViewById(R.id.tv_ten_sach);
            tvGiaThue = view.findViewById(R.id.tv_gia_thue);
            tvLoaiSach = view.findViewById(R.id.tv_loai_sach);
            imgDeleteSach = view.findViewById(R.id.img_delete_sach);

            tvMaSach.setText("Mã sách: " + sach.getMaSach());
            tvTenSach.setText("Tên sách: " + sach.getTenSach());
            tvGiaThue.setText("Giá thuê: " + sach.getGiaThue());
            tvLoaiSach.setText("Loại sách: " + loaiSach.getTenLoai());
        }

        imgDeleteSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment.xoaSach(String.valueOf(sach.getMaSach()));
            }
        });
        return view;
    }
}
