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

public class FundDetailAdapter extends RecyclerView.Adapter<FundDetailAdapter.TransListViewHolder> {

    private final Context mContext;
    private ArrayList<FundLevelData> mDatas;

    public FundDetailAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(ArrayList<FundLevelData> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.fund_detail_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransListViewHolder holder, int position) {

        FundLevelData fundLevelData = mDatas.get(position);
        holder.stall.setText(fundLevelData.level);
        holder.type.setText(fundLevelData.type);
        holder.trigger.setText(fundLevelData.trigger);
        holder.importPrice.setText(fundLevelData.importPrice);
        holder.importNum.setText(fundLevelData.importNum);
        holder.winPoint.setText(fundLevelData.point);
        holder.importDate.setText(fundLevelData.importDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

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
        TextView trigger;
        TextView importPrice;
        TextView importNum;
        TextView winPoint;
        TextView importDate;

        public TransListViewHolder(View itemView) {
            super(itemView);
            stall = itemView.findViewById(R.id.level);
            type = itemView.findViewById(R.id.type);
            trigger = itemView.findViewById(R.id.trigger);
            importPrice = itemView.findViewById(R.id.import_price);
            importNum = itemView.findViewById(R.id.import_num);
            winPoint = itemView.findViewById(R.id.win_point);
            importDate = itemView.findViewById(R.id.import_date);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(view, getLayoutPosition());
                    }
                    return true;
                }
            });
        }
    }

    private FundLevelAdapter.onItemLongClickListener onItemLongClickListener;
    public interface onItemLongClickListener{
        void onItemLongClick(View itemView, int positon);
    }

    public void setOnItemLongClickListener(FundLevelAdapter.onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

}
