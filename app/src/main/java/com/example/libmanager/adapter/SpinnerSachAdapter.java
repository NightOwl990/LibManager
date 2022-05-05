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

import java.util.ArrayList;

public class SpinnerSachAdapter extends ArrayAdapter<Sach> {
    private Context context;
    private ArrayList<Sach> lists;
    TextView tvMaSach, tvTenSach;

    public SpinnerSachAdapter(@NonNull Context context, ArrayList<Sach> lists) {
        super(context,0, lists);
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_sach, null);
        }

        final Sach sach = lists.get(position);
        if (sach != null){
            tvMaSach = view.findViewById(R.id.tv_spinner_ma_sach_Sach);
            tvMaSach.setText(sach.getMaSach() + ". ");

            tvTenSach = view.findViewById(R.id.tv_spinner_ten_sach_Sach);
            tvTenSach.setText(sach.getTenSach());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.spinner_sach, null);
        }

        final Sach sach = lists.get(position);
        if (sach != null){
            tvMaSach = view.findViewById(R.id.tv_spinner_ma_sach_Sach);
            tvMaSach.setText(sach.getMaSach() + ". ");

            tvTenSach = view.findViewById(R.id.tv_spinner_ten_sach_Sach);
            tvTenSach.setText(sach.getTenSach());
        }
        return view;
    }
}
