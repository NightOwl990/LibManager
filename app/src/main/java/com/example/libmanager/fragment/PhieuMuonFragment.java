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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.libmanager.R;
import com.example.libmanager.adapter.PhieuMuonAdapter;
import com.example.libmanager.adapter.SpinnerSachAdapter;
import com.example.libmanager.adapter.SpinnerThanhVienAdapter;
import com.example.libmanager.dao.PhieuMuonDAO;
import com.example.libmanager.dao.SachDAO;
import com.example.libmanager.dao.ThanhVienDAO;
import com.example.libmanager.model.PhieuMuon;
import com.example.libmanager.model.Sach;
import com.example.libmanager.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PhieuMuonFragment extends Fragment {
    ListView lvPhieuMuon;
    ArrayList<PhieuMuon> list;
    static PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter adapter;
    PhieuMuon phieuMuon;

    FloatingActionButton flbAddPhieuMuon;
    Dialog dialog;
    EditText edtMaPM;
    Spinner spinnerMaTV, spinnerMaSach;
    TextView tvNgay, tvTienThue;
    CheckBox chkTraSach;
    Button btnSavePM, btnCancelPM;

    SpinnerThanhVienAdapter spinnerThanhVienAdapter;
    ArrayList<ThanhVien> listThanhVien;
    ThanhVienDAO thanhVienDAO;
    ThanhVien thanhVien;
    int maThanhVien;

    SpinnerSachAdapter spinnerSachAdapter;
    ArrayList<Sach> listSach;
    SachDAO sachDAO;
    Sach sach;
    int maSach, tienThue;
    int positionTV, positionSach;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        lvPhieuMuon = view.findViewById(R.id.lv_phieu_muon);
        flbAddPhieuMuon = view.findViewById(R.id.flb_add_phieu_muon);
        phieuMuonDAO = new PhieuMuonDAO(getActivity());

        flbAddPhieuMuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogPM(getActivity(), 0);
            }
        });

        lvPhieuMuon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                phieuMuon = list.get(i);
                openDialogPM(getActivity(), 1);
                return false;
            }
        });

        capNhatListPM();
        return view;
    }

    void capNhatListPM(){
        list = (ArrayList<PhieuMuon>) phieuMuonDAO.getAll();
        adapter = new PhieuMuonAdapter(getActivity(), this, list);
        lvPhieuMuon.setAdapter(adapter);
    }

    protected  void openDialogPM(final Context context, final int type){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_phieu_muon);

        edtMaPM = dialog.findViewById(R.id.edt_ma_phieu_muon);
        spinnerMaTV = dialog.findViewById(R.id.spinner_ma_thanhvien);
        spinnerMaSach = dialog.findViewById(R.id.spinner_ma_sach);
        tvNgay = dialog.findViewById(R.id.tv_ngay);
        tvTienThue = dialog.findViewById(R.id.tv_tien_thue);
        chkTraSach = dialog.findViewById(R.id.chk_tra_sach);
        btnCancelPM = dialog.findViewById(R.id.btn_cancel_phieu_muon);
        btnSavePM = dialog.findViewById(R.id.btn_save_phieu_muon);

        // Set ngày thuê (mặc định ngày hiện hành)
        tvNgay.setText("Ngày thuê: " + sdf.format(new Date()));

        edtMaPM.setEnabled(false);

        // Gán dữ liệu cho spinnerMaTV
        thanhVienDAO = new ThanhVienDAO(context);
        listThanhVien = new ArrayList<ThanhVien>();
        listThanhVien = (ArrayList<ThanhVien>) thanhVienDAO.getAll();
        spinnerThanhVienAdapter = new SpinnerThanhVienAdapter(context, listThanhVien);
        spinnerMaTV.setAdapter(spinnerThanhVienAdapter);
        spinnerMaTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maThanhVien = listThanhVien.get(i).getMaTV();
                Toast.makeText(context, "Chọn " + listThanhVien.get(i).getHoTen(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Gán dữ liệu cho spinnerMaSach
        sachDAO = new SachDAO(context);
        listSach = new ArrayList<Sach>();
        listSach = (ArrayList<Sach>) sachDAO.getAll();
        spinnerSachAdapter = new SpinnerSachAdapter(context, listSach);
        spinnerMaSach.setAdapter(spinnerSachAdapter);
        spinnerMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maSach = listSach.get(i).getMaSach();
                tienThue = listSach.get(i).getGiaThue();
                tvTienThue.setText("Tiền thuê: " + tienThue);
                Toast.makeText(context, "Chọn " + listSach.get(i).getTenSach(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Edit set data lên form dialog
        if (type != 0){
            edtMaPM.setText(String.valueOf(phieuMuon.getMaPM()));
            for (int i = 0; i < listThanhVien.size(); i++){
                if (phieuMuon.getMaTV() == (listThanhVien.get(i).getMaTV())){
                    positionTV = i;
                }
                spinnerMaTV.setSelection(positionTV);
            }

            for (int i = 0; i < listSach.size(); i++){
                if (phieuMuon.getMaSach() == (listSach.get(i).getMaSach())){
                    positionSach = i;
                }
                spinnerMaSach.setSelection(positionSach);
            }

            tvNgay.setText("Ngày thuê: " + sdf.format(phieuMuon.getNgay()));
            tvTienThue.setText("Tiền thuê: " + phieuMuon.getTienThue());
            if (phieuMuon.getTraSach() == 1){
                chkTraSach.setChecked(true);
            } else {
                chkTraSach.setChecked(false);
            }
        }

        btnCancelPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnSavePM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phieuMuon = new PhieuMuon();
                phieuMuon.setMaSach(maSach);
                phieuMuon.setMaTV(maThanhVien);
                phieuMuon.setNgay(new Date());
                phieuMuon.setTienThue(tienThue);
                if (chkTraSach.isChecked()){
                    phieuMuon.setTraSach(1);
                } else {
                    phieuMuon.setTraSach(0);
                }

                if (type == 0){
                    // type = 0 -> insert
                    if (phieuMuonDAO.insert(phieuMuon) > 0){
                        Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // type = 1 -> update
                    phieuMuon.setMaPM(Integer.parseInt(edtMaPM.getText().toString()));
                    if (phieuMuonDAO.update(phieuMuon) > 0){
                        Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
                capNhatListPM();
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void xoaPM(final String Id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Delete");
        builder.setMessage("Bạn có muốn xóa không ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                phieuMuonDAO.delete(Id);
                capNhatListPM();
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
}