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
import com.example.libmanager.fragment.TopFragment;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.Top;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {
    private Context context;
    TopFragment topFragment;
    ArrayList<Top> lists;
    TextView tvSach, tvSoLuong;
    ImageView imgDeleteTop;

    public TopAdapter(@NonNull Context context, TopFragment topFragment, ArrayList<Top> lists) {
        super(context, 0, lists);
        this.context = context;
        this.topFragment = topFragment;
        this.lists = lists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_top, null);
        }
        final Top top = lists.get(position);
        if (top != null){
            tvSach = view.findViewById(R.id.tv_top_sach);
            tvSach.setText("Sách: " + top.getTenSach());

            tvSoLuong = view.findViewById(R.id.tv_top_so_luong);
            tvSoLuong.setText("Số lượng: " + top.getSoLuong());
        }
        return view;
    }
}
