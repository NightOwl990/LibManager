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
import com.example.libmanager.fragment.ThanhVienFragment;
import com.example.libmanager.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienAdapter extends ArrayAdapter<ThanhVien> {
    private Context context;
    ThanhVienFragment thanhVienFragment;
    private ArrayList<ThanhVien> mList;
    TextView tvMaTV, tvTenTV, tvNamSinh;
    ImageView imgDeleteTV;

    public ThanhVienAdapter(@NonNull Context context, ThanhVienFragment fragment, ArrayList<ThanhVien> mList) {
        super(context, 0, mList);
        this.context = context;
        this.thanhVienFragment = fragment;
        this.mList = mList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_thanh_vien, null);
        }

        final ThanhVien thanhVien = mList.get(position);
        if (thanhVien != null){
            tvMaTV = view.findViewById(R.id.tv_ma_tv);
            tvTenTV = view.findViewById(R.id.tv_ten_tv);
            tvNamSinh = view.findViewById(R.id.tv_nam_sinh);
            imgDeleteTV = view.findViewById(R.id.img_delete_tv);

            tvMaTV.setText("Mã thành viên: " + thanhVien.getMaTV());
            tvTenTV.setText("Tên thành viên: " + thanhVien.getHoTen());
            tvNamSinh.setText("Năm sinh: " + thanhVien.getNamSinh());
        }

        imgDeleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanhVienFragment.xoa(String.valueOf(thanhVien.getMaTV()));
            }
        });
        return view;
    }
}
