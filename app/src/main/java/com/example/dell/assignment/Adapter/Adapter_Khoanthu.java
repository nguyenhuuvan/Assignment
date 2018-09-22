package com.example.dell.assignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.assignment.Interface.OnItemDeleteListener;
import com.example.dell.assignment.Interface.OnItemEditListener;
import com.example.dell.assignment.Model.Thu;
import com.example.dell.assignment.R;

import java.util.List;

public class Adapter_Khoanthu extends BaseAdapter {
    List<Thu> thuList;
    private Context context;
    private OnItemDeleteListener deleteListener;
    private OnItemEditListener editListener;

    public Adapter_Khoanthu(List<Thu> thuList, Context context, OnItemDeleteListener deleteListener, OnItemEditListener editListener) {
        this.thuList = thuList;
        this.context = context;
        this.deleteListener = deleteListener;
        this.editListener = editListener;
    }


    public Adapter_Khoanthu(Context context, List<Thu> thuList) {
        this.context = context;
        this.thuList = thuList;
    }

    @Override
    public int getCount() {
        return thuList.size();
    }

    @Override
    public Object getItem(int position) {
        return thuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        public ImageView imgEdit, imgDelete;
        public TextView tvName;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_listview, null);
            viewHolder.imgDelete = view.findViewById(R.id.delete);
            viewHolder.imgEdit = view.findViewById(R.id.edit);
            viewHolder.tvName = view.findViewById(R.id.tvTen);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String TenKhoanThu = thuList.get(position).getTenkhoanthu();

        viewHolder.tvName.setText(TenKhoanThu);
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onDelete(position, thuList.get(position).getIdkhoanthu());
            }
        });
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editListener.onEdit(position, thuList.get(position).getIdkhoanthu());
            }
        });
        return view;
    }
}

