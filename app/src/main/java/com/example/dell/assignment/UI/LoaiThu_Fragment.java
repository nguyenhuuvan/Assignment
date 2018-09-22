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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.assignment.Adapter.Adapter_Loaithu;
import com.example.dell.assignment.Interface.OnItemDeleteListener;
import com.example.dell.assignment.Interface.OnItemEditListener;
import com.example.dell.assignment.Model.Thu;
import com.example.dell.assignment.R;
import com.example.dell.assignment.SQLite.QlChiThu_DAO;

import java.util.ArrayList;
import java.util.List;

public class LoaiThu_Fragment extends Fragment  implements OnItemDeleteListener,OnItemEditListener{

    FloatingActionButton showdialogLoaithu;
    private Cursor cursorLoaithu;
    private QlChiThu_DAO qlChiThuDao;
    private List<Thu> arraythu;
    Adapter_Loaithu adapter_loaithu;
    ListView listViewLoaithu;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loaithu, container, false);
        showdialogLoaithu = view.findViewById(R.id.showdialogLoaithu);
        showdialogLoaithu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoaithu();
            }
        });
        listViewLoaithu = view.findViewById(R.id.listviewloaithu);
        qlChiThuDao = new QlChiThu_DAO(getActivity());
        arraythu = new ArrayList<>();
        adapter_loaithu = new Adapter_Loaithu(arraythu,getActivity(),this,this);
        listViewLoaithu.setAdapter(adapter_loaithu);
        getLoaithu();
        adapter_loaithu.notifyDataSetChanged();
        return view;
    }

    public void getLoaithu() {
        cursorLoaithu = qlChiThuDao.getLoaithu();
        if (cursorLoaithu == null) {
            return;
        }
        if (cursorLoaithu.moveToFirst()) {
            do {
                Thu thu =new Thu();
                thu.setIdloaithi(cursorLoaithu.getInt(0));
                thu.setTenLoaithu(cursorLoaithu.getString(1));
                arraythu.add(thu);
            } while (cursorLoaithu.moveToNext());
        }
    }


    public void showDialogLoaithu() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_loaithu, null);
        dialog.setView(dialogView);
        Button them = dialogView.findViewById(R.id.themLoaithu);
        Button huy = dialogView.findViewById(R.id.huythemLoaithu);
        final EditText tenloaithu = dialogView.findViewById(R.id.edTenLoaiThu);
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
                String ten = tenloaithu.getText().toString().trim();
                if (ten.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    qlChiThuDao.insertLoaithu(ten);
                    arraythu.clear();
                    getLoaithu();
                    adapter_loaithu.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                }
            }
        });
    }


    @Override
    public void onDelete(final int positon,final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Bạn có muốn xóa loại thu: "+arraythu.get(positon).getTenLoaithu()+" không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qlChiThuDao.deleteLoaithu(id);
                Toast.makeText(getActivity(), "Đã xóa loại thu: "+ arraythu.get(positon).getTenLoaithu(), Toast.LENGTH_SHORT).show();
                arraythu.clear();
                getLoaithu();
                adapter_loaithu.notifyDataSetChanged();

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
        View dialogView = inflater.inflate(R.layout.dialog_sualoaithu, null);
        dialog.setView(dialogView);
        Button them = dialogView.findViewById(R.id.EditLoaithu);
        final Button huy = dialogView.findViewById(R.id.huyEditLoaithu);
        final EditText tenloaithu = dialogView.findViewById(R.id.edTenLoaiThu_Edit);
        String tenloaithu_Old = arraythu.get(positon).getTenLoaithu();
        tenloaithu.setText(tenloaithu_Old);
        final Dialog dialog1 = dialog.show();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloaithu_New = tenloaithu.getText().toString();
                if(tenloaithu_New.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                }
                else{
                    qlChiThuDao.updateLoaithu(tenloaithu_New,id);
                    arraythu.clear();
                    getLoaithu();
                    adapter_loaithu.notifyDataSetChanged();
                    dialog1.dismiss();
                    Toast.makeText(getActivity(), "Đã sửa thành công", Toast.LENGTH_SHORT).show();
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
}
