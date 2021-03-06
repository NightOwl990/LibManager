package com.example.libmanager.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.libmanager.R;
import com.example.libmanager.dao.SachDAO;
import com.example.libmanager.dao.ThanhVienDAO;
import com.example.libmanager.fragment.PhieuMuonFragment;
import com.example.libmanager.model.PhieuMuon;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends ArrayAdapter<PhieuMuon> {
    private Context context;
    PhieuMuonFragment fragment;
    private ArrayList<PhieuMuon> lists;
    TextView tvMaPM, tvTenTVPM, tvTenSachPM, tvTienThuePM, tvNgayPM, tvTraSachPM;
    ImageView imgDeletePM;
    SachDAO sachDAO;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PhieuMuonAdapter(@NonNull Context context, PhieuMuonFragment fragment, ArrayList<PhieuMuon> lists) {
        super(context, 0, lists);
        this.context = context;
        this.lists = lists;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_phieu_muon, null);
        }

        final PhieuMuon phieuMuon = lists.get(position);
        if (phieuMuon != null){
            tvMaPM = view.findViewById(R.id.tv_ma_phieu_muon);
            tvTenTVPM = view.findViewById(R.id.tv_ten_tv_phieu_muon);
            tvTienThuePM = view.findViewById(R.id.tv_tien_thue_phieu_muon);
            tvNgayPM = view.findViewById(R.id.tv_ngay_phieu_muon);
            tvTraSachPM = view.findViewById(R.id.tv_tra_sach_phieu_muon);
            tvTenSachPM= view.findViewById(R.id.tv_ten_sach_phieu_muon);
            imgDeletePM = view.findViewById(R.id.img_delete_phieu_muon);

            // L????y ma?? phi????u m??????n
            tvMaPM.setText("Ma?? phi????u: " + phieuMuon.getMaPM());


            // L????y t??n sa??ch t???? ba??ng 'sach' ????????c n????i v????i ba??ng 'phieumuon' b????ng 'masach'
            sachDAO = new SachDAO(context);
            Sach sach = sachDAO.getID(String.valueOf(phieuMuon.getMaSach()));
            tvTenSachPM.setText("T??n sa??ch: " + sach.getTenSach());


            thanhVienDAO = new ThanhVienDAO(context);
//            ThanhVien thanhVien = thanhVienDAO.getID(String.valueOf(phieuMuon.getMaTV()));
//            // L????y t??n tha??nh vi??n t???? ba??ng 'thanhvien' ????????c n????i v????i ba??ng 'phieumuon' b????ng 'matv'
//            tvTenTVPM.setText("Tha??nh vi??n: " + thanhVien.getHoTen());
            // L????y 'tienthue' t???? ba??ng 'phieumuon'
            tvTienThuePM.setText("Ti????n thu??: " + phieuMuon.getTienThue());
            // L????y 'ngay' t???? ba??ng 'phieumuon'
            tvNgayPM.setText("Nga??y thu??: " + sdf.format(phieuMuon.getNgay()));
            // L????y 'trasach' t???? ba??ng 'phieumuon' va?? so sa??nh tra??ng tha??i ??a?? tra?? hay ch??a
            if (phieuMuon.getTraSach() == 1){
                tvTraSachPM.setTextColor(Color.BLUE);
                tvTraSachPM.setText("??a?? tra?? sa??ch");
            } else {
                tvTraSachPM.setTextColor(Color.RED);
                tvTraSachPM.setText("Ch??a tra?? sa??ch");
            }

            imgDeletePM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.xoaPM(String.valueOf(phieuMuon.getMaPM()));
                }
            });

        }
        return view;
    }
}
