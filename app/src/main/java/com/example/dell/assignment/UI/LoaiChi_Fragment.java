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

import com.example.dell.assignment.Adapter.Adapter_Loaichi;
import com.example.dell.assignment.Interface.OnItemDeleteListener;
import com.example.dell.assignment.Interface.OnItemEditListener;
import com.example.dell.assignment.Model.Chi;
import com.example.dell.assignment.R;
import com.example.dell.assignment.SQLite.QlChiThu_DAO;

import java.util.ArrayList;
import java.util.List;

public class LoaiChi_Fragment  extends Fragment implements OnItemEditListener,OnItemDeleteListener{
    FloatingActionButton showdialogLoaichi;
    private Cursor cursorLoaichi;
    private QlChiThu_DAO qlChiThuDao;
    private List<Chi> arraychi;
    Adapter_Loaichi adapter_loaichi;
    ListView listViewLoaichi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_loaichi,container,false);
        showdialogLoaichi = view.findViewById(R.id.showdialogLoaichi);
        showdialogLoaichi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoaichi();
            }
        });
        listViewLoaichi = view.findViewById(R.id.listviewloaichi);
        qlChiThuDao = new QlChiThu_DAO(getActivity());
        arraychi = new ArrayList<>();
        adapter_loaichi = new Adapter_Loaichi(arraychi,getActivity(),this,this);
        listViewLoaichi.setAdapter(adapter_loaichi);
        getLoaichi();
        adapter_loaichi.notifyDataSetChanged();
        return view;
    }
    public void getLoaichi() {
        cursorLoaichi = qlChiThuDao.getLoaichi();
        if (cursorLoaichi == null) {
            return;
        }
        if (cursorLoaichi.moveToFirst()) {
            do {
                Chi chi =new Chi();
                chi.setIdloaichi(cursorLoaichi.getInt(0));
                chi.setTenLoaichi(cursorLoaichi.getString(1));
                arraychi.add(chi);
            } while (cursorLoaichi.moveToNext());
        }
    }
    public void showDialogLoaichi(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_loaichi, null);
        dialog.setView(dialogView);
        Button them = dialogView.findViewById(R.id.themLoaichi);
        Button huy = dialogView.findViewById(R.id.huythemLoaichi);
        final EditText tenloaichi = dialogView.findViewById(R.id.edTenLoaiChi);
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
                String ten = tenloaichi.getText().toString().trim();
                if (ten.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    qlChiThuDao.insertLoaichi(ten);
                    arraychi.clear();
                    getLoaichi();
                    adapter_loaichi.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog1.dismiss();
                }
            }
        });

    }

    @Override
    public void onDelete(final int positon, final int id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Bạn có muốn xóa loại chi: "+ arraychi.get(positon).getTenLoaichi()+" không?");
        builder.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qlChiThuDao.deleteLoaichi(id);
                Toast.makeText(getActivity(), "Đã xóa loại chi: "+ arraychi.get(positon).getTenLoaichi(), Toast.LENGTH_SHORT).show();
                arraychi.clear();
                getLoaichi();
                adapter_loaichi.notifyDataSetChanged();

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
        View dialogView = inflater.inflate(R.layout.dialog_sualoaichi, null);
        dialog.setView(dialogView);
        Button them = dialogView.findViewById(R.id.EditLoaichi);
        final Button huy = dialogView.findViewById(R.id.huyEditLoaichi);
        final EditText tenloaichi = dialogView.findViewById(R.id.edTenLoaiChi_Edit);
        String tenloaichi_Old = arraychi.get(positon).getTenLoaichi();
        tenloaichi.setText(tenloaichi_Old);
        final Dialog dialog1 = dialog.show();
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloaichi_New = tenloaichi.getText().toString();
                if(tenloaichi_New.isEmpty()){
                    Toast.makeText(getActivity(), "Vui lòng nhập dữ liệu", Toast.LENGTH_SHORT).show();
                }
                else{
                    qlChiThuDao.updateLoaiChi(tenloaichi_New,id);
                    arraychi.clear();
                    getLoaichi();
                    adapter_loaichi.notifyDataSetChanged();
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
