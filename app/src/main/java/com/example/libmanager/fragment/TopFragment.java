package com.example.libmanager.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.libmanager.R;
import com.example.libmanager.adapter.TopAdapter;
import com.example.libmanager.dao.PhieuMuonDAO;
import com.example.libmanager.model.Top;

import java.util.ArrayList;

public class TopFragment extends Fragment {
    ListView lvTop;
    ArrayList<Top> lists;
    TopAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top, container, false);
        lvTop = view.findViewById(R.id.lv_top);
        PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(getActivity());
        lists = (ArrayList<Top>) phieuMuonDAO.getTop();
        adapter = new TopAdapter(getActivity(), this, lists);
        lvTop.setAdapter(adapter);
        return view;
    }
}