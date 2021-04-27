package com.lyq.fund.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lyq.fund.R;
import com.lyq.fund.bean.FundData;
import com.lyq.fund.ui.activity.SettingActivity;

import java.util.ArrayList;

public class FundAdapter extends RecyclerView.Adapter<FundAdapter.TransListViewHolder> {

    private final Context mContext;
    private ArrayList<FundData> mDatas;

    public FundAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(ArrayList<FundData> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransListViewHolder holder, int position) {

        FundData fundData = mDatas.get(position);
        holder.name.setText(fundData.name);
        holder.code.setText(fundData.code);
        holder.last.setText(fundData.lastPrice);
        
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                intent.putExtra(SettingActivity.id, fundData.id);
                mContext.startActivity(intent);
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

        TextView name;
        TextView code;
        TextView last;

        public TransListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            code = itemView.findViewById(R.id.code);
            last = itemView.findViewById(R.id.last);
        }
    }

}
