package com.example.dell.assignment.UI;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.assignment.Model.Chi;
import com.example.dell.assignment.Model.Thu;
import com.example.dell.assignment.R;
import com.example.dell.assignment.SQLite.QlChiThu_DAO;

import java.util.ArrayList;
import java.util.List;

public class ThongKe_Fragment extends Fragment {
    private TextView soloaichi,soloaithu,soluongthu,soluongchi,chechlech,sokhoanthu,sokhoanchi;
    private Cursor cursorLoaichi, cursorLoaithu,cursorKhoanthu,cursorKhoanchi,cursorKhoanthuofLoaithu,cursorKhoanchiofLoaichi;
    private QlChiThu_DAO qlChiThuDao;
    private List<Thu> arrayloaithu;
    private List<Chi> arrayloaichi;
    private List<Thu> arraysoluongthu;
    private List<Chi> arraysoluongchi;
    private List<Thu> arraysoluongthuofloaithu;
    private List<Chi> arraysoluongchiofloaichi;
    private Spinner thu,chi;
   private SimpleCursorAdapter adapter_thu,adapter_chi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongke_fragment,container,false);
        soloaichi =view.findViewById(R.id.soloaichi);
        soloaithu =view.findViewById(R.id.soloaithu);
        soluongchi =view.findViewById(R.id.soluongchi);
        soluongthu =view.findViewById(R.id.soluongthu);
        chechlech =view.findViewById(R.id.chechlech);
        sokhoanthu =view.findViewById(R.id.sokhoanthu);
        sokhoanchi =view.findViewById(R.id.sokhoanchi);
        qlChiThuDao = new QlChiThu_DAO(getActivity());
        arrayloaichi = new ArrayList<Chi>();
        arraysoluongchi = new ArrayList<Chi>();
        arraysoluongthu = new ArrayList<Thu>();
        arrayloaithu = new ArrayList<Thu>();
        arraysoluongthuofloaithu = new ArrayList<Thu>();
        arraysoluongchiofloaichi = new ArrayList<Chi>();
        Integer tongsoluongthu =0;
        Integer tongsoluongchi=0;
        getloaithu();
        getloaichi();
        getKhoanchi();
        getKhoanthu();
        Integer soloaithu1 = arrayloaithu.size();
        soloaithu.setText(soloaithu1.toString());
        Integer soloaichi1 = arrayloaichi.size();
        soloaichi.setText(soloaichi1.toString());
        for (int i=0;i<arraysoluongthu.size();i++){
            tongsoluongthu+=arraysoluongthu.get(i).getSoluong();
        }
        for (int i=0;i<arraysoluongchi.size();i++){
            tongsoluongchi+=arraysoluongchi.get(i).getSoluong();
        }
        soluongchi.setText(tongsoluongchi.toString());
        soluongthu.setText(tongsoluongthu.toString());
        Integer chechlech1 = tongsoluongthu-tongsoluongchi;
        chechlech.setText(chechlech1.toString());

        thu = view.findViewById(R.id.sptenloaithu);

        cursorLoaithu = qlChiThuDao.getLoaithu();
        if (cursorLoaithu != null) {
            adapter_thu = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaithu,
                    new String[]{"tenloaithu"},
                    new int[]{android.R.id.text1});
            thu.setAdapter(adapter_thu);
        }
        thu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer sokhoanthu1;
                arraysoluongthuofloaithu.clear();
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                String tenloaithu2 = c.getString(1);
                getKhoanthuOfLoaithu(tenloaithu2);
                sokhoanthu1 = arraysoluongthuofloaithu.size();
                sokhoanthu.setText(sokhoanthu1.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        chi = view.findViewById(R.id.sptenloaichi);
        cursorLoaichi = qlChiThuDao.getLoaichi();
        if (cursorLoaichi != null) {
            adapter_chi = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaichi,
                    new String[]{"tenloaichi"},
                    new int[]{android.R.id.text1});
            chi.setAdapter(adapter_chi);
        }
        chi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Integer sokhoanchi1;
                arraysoluongchiofloaichi.clear();
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                String tenloaichi2 = c.getString(1);
                getKhoanchiOfLoaichi(tenloaichi2);
                sokhoanchi1 = arraysoluongchiofloaichi.size();
                sokhoanchi.setText(sokhoanchi1.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getloaithu() {
        cursorLoaithu = qlChiThuDao.getLoaithu();
        if (cursorLoaithu == null) {
            return;
        }
        if (cursorLoaithu.moveToFirst()) {
            do {
                Thu thu = new Thu();
                thu.setIdloaithi(cursorLoaithu.getInt(0));
                thu.setTenLoaithu(cursorLoaithu.getString(1));
                arrayloaithu.add(thu);
            } while (cursorLoaithu.moveToNext());
        }
    }
    public void getloaichi() {

        cursorLoaichi = qlChiThuDao.getLoaichi();
        if (cursorLoaichi == null) {
            return;
        }
        if (cursorLoaichi.moveToFirst()) {
            do {
                Chi chi = new Chi();
                chi.setIdloaichi(cursorLoaichi.getInt(0));
                chi.setTenLoaichi(cursorLoaichi.getString(1));
                arrayloaichi.add(chi);
            } while (cursorLoaichi.moveToNext());
        }
    }
    public void getKhoanthu() {
        cursorKhoanthu = qlChiThuDao.getKhoanthu();
        if (cursorKhoanthu == null) {
            return;
        }
        if (cursorKhoanthu.moveToFirst()) {
            do {
                Thu thu = new Thu();
                thu.setIdkhoanthu(cursorKhoanthu.getInt(0));
                thu.setTenLoaithu(cursorKhoanthu.getString(1));
                thu.setTenkhoanthu(cursorKhoanthu.getString(2));
                thu.setSoluong(cursorKhoanthu.getInt(3));
                thu.setThoigian(cursorKhoanthu.getString(4));
                arraysoluongthu.add(thu);
            } while (cursorKhoanthu.moveToNext());
        }

    }
    public void getKhoanchi() {
        cursorKhoanchi = qlChiThuDao.getKhoanchi();
        if (cursorKhoanchi == null) {
            return;
        }
        if (cursorKhoanchi.moveToFirst()) {
            do {
                Chi chi = new Chi();
                chi.setIdkhoanchi(cursorKhoanchi.getInt(0));
                chi.setTenLoaichi(cursorKhoanchi.getString(1));
                chi.setTenkhoanchi(cursorKhoanchi.getString(2));
                chi.setSoluong(cursorKhoanchi.getInt(3));
                chi.setThoigian(cursorKhoanchi.getString(4));
                arraysoluongchi.add(chi);
            } while (cursorKhoanchi.moveToNext());
        }

    }
    public void getKhoanthuOfLoaithu(String name){
        cursorKhoanthuofLoaithu = qlChiThuDao.getKhoanthuofLoaithu(name);
        if (cursorKhoanthuofLoaithu == null) {
            return;
        }
        if (cursorKhoanthuofLoaithu.moveToFirst()) {
            do {
                Thu thu = new Thu();
                thu.setIdkhoanthu(cursorKhoanthuofLoaithu.getInt(0));
                thu.setTenLoaithu(cursorKhoanthuofLoaithu.getString(1));
                thu.setTenkhoanthu(cursorKhoanthuofLoaithu.getString(2));
                thu.setSoluong(cursorKhoanthuofLoaithu.getInt(3));
                thu.setThoigian(cursorKhoanthuofLoaithu.getString(4));
                arraysoluongthuofloaithu.add(thu);
            } while (cursorKhoanthuofLoaithu.moveToNext());
        }
    }
    public void getKhoanchiOfLoaichi(String name){
        cursorKhoanchiofLoaichi = qlChiThuDao.getKhoanthuofLoaichi(name);
        if (cursorKhoanchiofLoaichi == null) {
            return;
        }
        if (cursorKhoanchiofLoaichi.moveToFirst()) {
            do {
                Chi chi = new Chi();
                chi.setIdloaichi(cursorKhoanchiofLoaichi.getInt(0));
                chi.setTenLoaichi(cursorKhoanchiofLoaichi.getString(1));
                chi.setTenkhoanchi(cursorKhoanchiofLoaichi.getString(2));
                chi.setSoluong(cursorKhoanchiofLoaichi.getInt(3));
                chi.setThoigian(cursorKhoanchiofLoaichi.getString(4));
                arraysoluongchiofloaichi.add(chi);
            } while (cursorKhoanchiofLoaichi.moveToNext());
        }
    }
}
