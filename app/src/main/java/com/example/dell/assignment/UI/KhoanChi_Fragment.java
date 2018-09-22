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

import com.example.dell.assignment.Adapter.Adapter_Khoanchi;
import com.example.dell.assignment.Interface.OnItemDeleteListener;
import com.example.dell.assignment.Interface.OnItemEditListener;
import com.example.dell.assignment.Model.Chi;
import com.example.dell.assignment.R;
import com.example.dell.assignment.SQLite.QlChiThu_DAO;

import java.util.ArrayList;
import java.util.List;

public class KhoanChi_Fragment extends Fragment implements OnItemEditListener, OnItemDeleteListener {
    FloatingActionButton showdialogKhoanchi;
    private Cursor cursorKhoanchi, cursorLoaichi;
    private QlChiThu_DAO qlChiThuDao;
    private List<Chi> arraychi;
    Adapter_Khoanchi adapter_khoanchi;
    ListView listViewKhoanchi;
    String tenloaichi1, tenloaichi2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoanchi, container, false);
        showdialogKhoanchi = view.findViewById(R.id.showdialogKhoanchi);
        showdialogKhoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogKhoanchi();
            }
        });
        listViewKhoanchi = view.findViewById(R.id.listviewkhoanchi);
        qlChiThuDao = new QlChiThu_DAO(getActivity());
        arraychi = new ArrayList<>();
        adapter_khoanchi = new Adapter_Khoanchi(arraychi, getActivity(), this, this);
        listViewKhoanchi.setAdapter(adapter_khoanchi);
        getKhoanchi();
        adapter_khoanchi.notifyDataSetChanged();
        listViewKhoanchi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Dialogshow(arraychi.get(position).getTenLoaichi(), arraychi.get(position).getTenkhoanchi(), arraychi.get(position).getSoluong(), arraychi.get(position).getThoigian());
            }
        });
        return view;
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
                arraychi.add(chi);
            } while (cursorKhoanchi.moveToNext());
        }
    }

    public void showDialogKhoanchi() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_khoanchi, null);
        dialog.setView(dialogView);

        Spinner spinner_chi = dialogView.findViewById(R.id.spLoaichi);
        cursorLoaichi = qlChiThuDao.getLoaichi();
        SimpleCursorAdapter adapter_SpinerChi;
        if (cursorLoaichi != null) {
            adapter_SpinerChi = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaichi,
                    new String[]{"tenloaichi"},
                    new int[]{android.R.id.text1});
            spinner_chi.setAdapter(adapter_SpinerChi);
        }
        spinner_chi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                tenloaichi1 = c.getString(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button them = dialogView.findViewById(R.id.themKhoanchi);
        Button huy = dialogView.findViewById(R.id.huythemKhoanchi);
        final EditText tenkhoanchi = dialogView.findViewById(R.id.edTenKhoanchi);
        final EditText soluong = dialogView.findViewById(R.id.edSoLuongKhoanchi);
        final EditText thoigian = dialogView.findViewById(R.id.edThoiGianKhoanchi);
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
                if (tenloaichi1 == null) {
                    Toast.makeText(getActivity(), "Vui lòng thêm loại chi trước", Toast.LENGTH_SHORT).show();
                } else {
                    String ten = tenkhoanchi.getText().toString().trim();
                    String sl = soluong.getText().toString().trim();
                    String time = thoigian.getText().toString().trim();
                    if (ten.isEmpty() || sl.isEmpty() || time.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập dữ liêu", Toast.LENGTH_SHORT).show();
                    } else {
                        qlChiThuDao.insertKhoanChi(tenloaichi1, ten, Integer.parseInt(sl), time);
                        arraychi.clear();
                        getKhoanchi();
                        adapter_khoanchi.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "Đã thêm", Toast.LENGTH_SHORT).show();
                        dialog1.dismiss();
                    }
                }
            }
        });
    }

    @Override
    public void onDelete(final int positon, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Bạn có muốn xóa khoản chi: " + arraychi.get(positon).getTenkhoanchi() + " không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qlChiThuDao.deleteKhoanchi(id);
                Toast.makeText(getActivity(), "Đã xóa khoản chi: " + arraychi.get(positon).getTenkhoanchi(), Toast.LENGTH_SHORT).show();
                arraychi.clear();
                getKhoanchi();
                adapter_khoanchi.notifyDataSetChanged();

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
        View dialogView = inflater.inflate(R.layout.dialog_suakhoanchi, null);
        dialog.setView(dialogView);

        Spinner spinner_chi_edit = dialogView.findViewById(R.id.spLoaichi_Edit);
        cursorLoaichi = qlChiThuDao.getLoaichi();
        SimpleCursorAdapter adapter_SpinerChi;
        if (cursorLoaichi != null) {
            adapter_SpinerChi = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_spinner_item,
                    cursorLoaichi,
                    new String[]{"tenloaichi"},
                    new int[]{android.R.id.text1});
            spinner_chi_edit.setAdapter(adapter_SpinerChi);
        }
        spinner_chi_edit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cursor c = (Cursor) parent.getItemAtPosition(position);
                tenloaichi2 = c.getString(1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button them = dialogView.findViewById(R.id.themKhoanchi_Edit);
        final Button huy = dialogView.findViewById(R.id.huythemKhoanchi_Edit);
        final EditText tenkhoanchi = dialogView.findViewById(R.id.edTenKhoanchi_Edit);
        final EditText soluong = dialogView.findViewById(R.id.edSoLuongKhoanchi_Edit);
        final EditText thoigian = dialogView.findViewById(R.id.edThoiGianKhoanchi_Edit);

        String tenkhoanchi_Old = arraychi.get(positon).getTenkhoanchi();
        String soluong_Old = arraychi.get(positon).getSoluong().toString();
        String thoigian_Old = arraychi.get(positon).getThoigian();
        tenkhoanchi.setText(tenkhoanchi_Old);
        soluong.setText(soluong_Old);
        thoigian.setText(thoigian_Old);
        final Dialog dialog1 = dialog.show();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tenloaichi2 == null) {
                    Toast.makeText(getActivity(), "Vui lòng thêm loại chi trước", Toast.LENGTH_SHORT).show();
                } else {
                    String tenkhoanchi_New = tenkhoanchi.getText().toString();
                    String soluong_New = soluong.getText().toString();
                    String thoigian_New = thoigian.getText().toString();
                    if (tenkhoanchi_New.isEmpty() || soluong_New.isEmpty() || thoigian_New.isEmpty()) {
                        Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                    } else {
                        qlChiThuDao.updateKhoanchi(tenloaichi2, tenkhoanchi_New, id, Integer.parseInt(soluong_New), thoigian_New);
                        arraychi.clear();
                        getKhoanchi();
                        adapter_khoanchi.notifyDataSetChanged();
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

    public void Dialogshow(String tenloaichi1, String tenkhoanchi1, Integer soluong1, String thoigian1) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_showkhoanchi);

        final TextView tenloaichi = dialog.findViewById(R.id.showloaichi_khoanchi);

        final TextView tenkhoanchi = dialog.findViewById(R.id.showten_khoanchi);
        final TextView soluong = dialog.findViewById(R.id.showsoluong_khoanchi);
        final TextView thoigian = dialog.findViewById(R.id.showthoigian_khoanchi);
        Button thoat = dialog.findViewById(R.id.back_showthongtin_khoanchi);
        tenkhoanchi.setText(tenkhoanchi1);
        soluong.setText(soluong1.toString());
        thoigian.setText(thoigian1);
        tenloaichi.setText(tenloaichi1);
        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
