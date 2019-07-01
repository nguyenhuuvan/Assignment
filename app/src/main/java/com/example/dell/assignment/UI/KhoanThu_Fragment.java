package com.example.dell.assignment.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.assignment.Adapter.Adapter_Khoanthu;
import com.example.dell.assignment.Interface.OnItemDeleteListener;
import com.example.dell.assignment.Interface.OnItemEditListener;
import com.example.dell.assignment.Model.Thu;
import com.example.dell.assignment.R;
import com.example.dell.assignment.SQLite.QlChiThu_DAO;

import java.util.ArrayList;
import java.util.List;

public class KhoanThu_Fragment extends Fragment implements OnItemEditListener, OnItemDeleteListener {
    FloatingActionButton showdialogKhoanthu;
    private Cursor cursorKhoanthu, cursorLoaithu;
    private QlChiThu_DAO qlChiThuDao;
    private List<Thu> arraythu;
    Adapter_Khoanthu adapter_khoanthu;
    ListView listViewKhoanthu;
    String tenloaithu1, tenloaithu2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoanthu, container, false);
        showdialogKhoanthu = view.findViewById(R.id.showdialogKhoanthu);
        showdialogKhoanthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKhoanthu();
            }
        });
        listViewKhoanthu = view.findViewById(R.id.listviewkhoanthu);
        qlChiThuDao = new QlChiThu_DAO(getActivity());
        arraythu = new ArrayList<>();
        adapter_khoanthu = new Adapter_Khoanthu(arraythu, getActivity(), this, this);
        listViewKhoanthu.setAdapter(adapter_khoanthu);
        getKhoanthu();
        adapter_khoanthu.notifyDataSetChanged();
        listViewKhoanthu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialogshow(arraythu.get(position).getTenLoaithu(), arraythu.get(position).getTenkhoanthu(), arraythu.get(position).getSoluong(), arraythu.get(position).getThoigian());
            }
        });
        return view;
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
                arraythu.add(thu);
            } while (cursorKhoanthu.moveToNext());
        }
    }

    public void showDialogKhoanthu() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_khoanthu, null);
        dialog.setView(dialogView);

        Spinner spinner_thu = dialogView.findViewById(R.id.spLoaithu);
        cursorLoaithu = qlChiThuDao.getLoaithu();
        SimpleCursorAdapter adapter_SpinerThu;
        if (cursorLoaithu != null) {
            adapter_SpinerThu = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaithu,
                    new String[]{"tenloaithu"},
                    new int[]{android.R.id.text1});
            spinner_thu.setAdapter(adapter_SpinerThu);
        }
        spinner_thu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                tenloaithu1 = c.getString(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button them = dialogView.findViewById(R.id.themKhoanthu);
        Button huy = dialogView.findViewById(R.id.huythemKhoanthu);
        final EditText tenkhoanthu = dialogView.findViewById(R.id.edTenKhoanthu);
        final EditText soluong = dialogView.findViewById(R.id.edSoLuongKhoanthu);
        final EditText thoigian = dialogView.findViewById(R.id.edThoiGianKhoanthu);
        final Dialog dialog1 = dialog.show();
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tenloaithu1 == null) {
                    Toast.makeText(getActivity(), "Vui lòng thêm loại thu trước", Toast.LENGTH_SHORT).show();
                } else {
                    String ten = tenkhoanthu.getText().toString().trim();
                    String sl = soluong.getText().toString().trim();
                    String time = thoigian.getText().toString().trim();
                    if (ten.isEmpty() || sl.isEmpty() || time.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập dữ liêu", Toast.LENGTH_SHORT).show();
                    } else {
                        qlChiThuDao.insertKhoanthu(tenloaithu1, ten, Integer.parseInt(sl), time);
                        arraythu.clear();
                        getKhoanthu();
                        adapter_khoanthu.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Thêm thành công khoản thu: " + ten + " vào loại thu: " + tenloaithu1, Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void onDelete(final int positon, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Bạn có muốn xóa khoản thu: " + arraythu.get(positon).getTenkhoanthu() + " không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qlChiThuDao.deleteKhoanthu(id);
                Toast.makeText(getActivity(), "Đã xóa khoản thu: " + arraythu.get(positon).getTenkhoanthu(), Toast.LENGTH_SHORT).show();
                arraythu.clear();
                getKhoanthu();
                adapter_khoanthu.notifyDataSetChanged();

            }
        });
        builder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onEdit(int positon, final int id) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_suakhoanthu, null);
        dialog.setView(dialogView);

        Spinner spinner_thu_edit = dialogView.findViewById(R.id.spLoaithu_Edit);
        cursorLoaithu = qlChiThuDao.getLoaithu();
        SimpleCursorAdapter adapter_SpinerThu;
        if (cursorLoaithu != null) {
            adapter_SpinerThu = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaithu,
                    new String[]{"tenloaithu"},
                    new int[]{android.R.id.text1});
            spinner_thu_edit.setAdapter(adapter_SpinerThu);
        }
        spinner_thu_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                tenloaithu2 = c.getString(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button them = dialogView.findViewById(R.id.themKhoanthu_Edit);
        final Button huy = dialogView.findViewById(R.id.huythemKhoanthu_Edit);
        final EditText tenkhoanthu = dialogView.findViewById(R.id.edTenKhoanthu_Edit);
        final EditText soluong = dialogView.findViewById(R.id.edSoLuongKhoanthu_Edit);
        final EditText thoigian = dialogView.findViewById(R.id.edThoiGianKhoanthu_Edit);

        String tenkhoanthu_Old = arraythu.get(positon).getTenkhoanthu();
        String soluong_Old = arraythu.get(positon).getSoluong().toString();
        String thoigian_Old = arraythu.get(positon).getThoigian();
        tenkhoanthu.setText(tenkhoanthu_Old);
        soluong.setText(soluong_Old);
        thoigian.setText(thoigian_Old);
        final Dialog dialog1 = dialog.show();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tenloaithu2 == null) {
                    Toast.makeText(getActivity(), "Vui lòng thêm loại thu trước", Toast.LENGTH_SHORT).show();
                } else {
                    String tenkhoanthu_New = tenkhoanthu.getText().toString();
                    String soluong_New = soluong.getText().toString();
                    String thoigian_New = thoigian.getText().toString();
                    if (tenkhoanthu_New.isEmpty() || soluong_New.isEmpty() || thoigian_New.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        qlChiThuDao.updateKhoanthu(tenloaithu2, tenkhoanthu_New, id, Integer.parseInt(soluong_New), thoigian_New);
                        arraythu.clear();
                        getKhoanthu();
                        adapter_khoanthu.notifyDataSetChanged();
                        dialog1.dismiss();
                        Toast.makeText(getActivity(), "Đã sửa thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

    }

    public void Dialogshow(String tenloaithu1, String tenkhoanthu1, Integer soluong1, String thoigian1) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_showkhoanthu);

        final TextView tenloaithu = dialog.findViewById(R.id.showloaithu_khoanthu);
        final TextView tenkhoanthu = dialog.findViewById(R.id.showten_khoanthu);
        final TextView soluong = dialog.findViewById(R.id.showsoluong_khoanthu);
        final TextView thoigian = dialog.findViewById(R.id.showthoigian_khoanthu);
        Button thoat = dialog.findViewById(R.id.back_showthongtin_khoanthu);
        tenkhoanthu.setText(tenkhoanthu1);
        soluong.setText(soluong1.toString());
        thoigian.setText(thoigian1);
        tenloaithu.setText(tenloaithu1);
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

