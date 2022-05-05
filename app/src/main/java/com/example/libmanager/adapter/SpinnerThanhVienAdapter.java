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
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;

public class SpinnerThanhVienAdapter extends ArrayAdapter<ThanhVien> {

    private Context context;
    private ArrayList<ThanhVien> lists;
    TextView tvMaTV, tvTenTV;

    public SpinnerThanhVienAdapter(@NonNull Context context, ArrayList<ThanhVien> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_thanh_vien, null);
        }

        final ThanhVien thanhVien = lists.get(position);
        if (thanhVien != null){
            tvMaTV = view.findViewById(R.id.tv_spinner_ma_tv);
            tvMaTV.setText(thanhVien.getMaTV() + ". ");

            tvTenTV = view.findViewById(R.id.tv_spinner_ten_tv);
            tvTenTV.setText(thanhVien.getHoTen());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_thanh_vien, null);
        }

        final ThanhVien thanhVien = lists.get(position);
        if (thanhVien != null){
            tvMaTV = view.findViewById(R.id.tv_spinner_ma_tv);
            tvMaTV.setText(thanhVien.getMaTV() + ". ");

            tvTenTV = view.findViewById(R.id.tv_spinner_ten_tv);
            tvTenTV.setText(thanhVien.getHoTen());
        }
        return view;
    }
}
