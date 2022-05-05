package com.example.libmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libmanager.R;
import com.example.libmanager.model.LoaiSach;

import java.util.ArrayList;
import java.util.List;

public class SpinnerLoaiSachAdapter extends ArrayAdapter<LoaiSach> {
    Context context;
    ArrayList<LoaiSach> listLoaiSach;
    TextView tvMaSach, tvTenSach;

    public SpinnerLoaiSachAdapter(@NonNull Context context, ArrayList<LoaiSach> listLoaiSach) {
        super(context, 0, listLoaiSach);
        this.context = context;
        this.listLoaiSach = listLoaiSach;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_loai_sach, null);
        }

        final LoaiSach loaiSach = listLoaiSach.get(position);
        if (loaiSach != null){
            tvMaSach = view.findViewById(R.id.tv_spinner_ma_sach);
            tvTenSach = view.findViewById(R.id.tv_spinner_ten_sach);

            tvMaSach.setText(loaiSach.getMaLoai() + ". ");
            tvTenSach.setText(loaiSach.getTenLoai());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_loai_sach, null);
        }

        final LoaiSach loaiSach = listLoaiSach.get(position);
        if (loaiSach != null){
            tvMaSach = view.findViewById(R.id.tv_spinner_ma_sach);
            tvTenSach = view.findViewById(R.id.tv_spinner_ten_sach);

            tvMaSach.setText(loaiSach.getMaLoai() + ". ");
            tvTenSach.setText(loaiSach.getTenLoai());
        }
        return view;
    }
}
