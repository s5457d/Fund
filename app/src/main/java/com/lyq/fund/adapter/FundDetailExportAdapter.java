package com.lyq.fund.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyq.fund.R;
import com.lyq.fund.bean.FundLevelData;

import java.util.ArrayList;

public class FundDetailExportAdapter extends RecyclerView.Adapter<FundDetailExportAdapter.TransListViewHolder> {

    private final Context mContext;
    private ArrayList<FundLevelData> mDatas;

    public FundDetailExportAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(ArrayList<FundLevelData> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_export_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransListViewHolder holder, int position) {

        FundLevelData fundLevelData = mDatas.get(position);
        holder.stall.setText(fundLevelData.level);
        holder.type.setText(fundLevelData.type);
        holder.importDate.setText(fundLevelData.importDate);
        holder.exportDate.setText(fundLevelData.exportDate);
        holder.importPrice.setText(fundLevelData.importPrice);
        holder.exportPrice.setText(fundLevelData.exportPrice);
        holder.exportNumber.setText(fundLevelData.exportNum);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    public void clear() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    class TransListViewHolder extends RecyclerView.ViewHolder {

        TextView stall;
        TextView type;
        TextView importDate;
        TextView exportDate;
        TextView importPrice;
        TextView exportPrice;
        TextView exportNumber;

        public TransListViewHolder(View itemView) {
            super(itemView);
            stall = itemView.findViewById(R.id.export_item_level);
            type = itemView.findViewById(R.id.export_item_type);
            importDate = itemView.findViewById(R.id.export_import_item_date);
            exportDate = itemView.findViewById(R.id.export_export_date);
            importPrice = itemView.findViewById(R.id.export_import_item_price);
            exportPrice = itemView.findViewById(R.id.export_export_item_price);
            exportNumber = itemView.findViewById(R.id.export_export_item_number);

//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//                    if (onItemLongClickListener != null) {
//                        onItemLongClickListener.onItemLongClick(view, getLayoutPosition());
//                    }
//                    return true;
//                }
//            });
        }
    }

//    private FundLevelAdapter.onItemLongClickListener onItemLongClickListener;
//    public interface onItemLongClickListener{
//        void onItemLongClick(View itemView, int positon);
//    }
//
//    public void setOnItemLongClickListener(FundLevelAdapter.onItemLongClickListener onItemLongClickListener) {
//        this.onItemLongClickListener = onItemLongClickListener;
//    }

}
