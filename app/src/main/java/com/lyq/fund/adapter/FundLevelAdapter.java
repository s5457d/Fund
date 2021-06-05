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

public class FundLevelAdapter extends RecyclerView.Adapter<FundLevelAdapter.TransListViewHolder> {

    private final Context mContext;
    private ArrayList<FundLevelData> mDatas;

    public FundLevelAdapter(Context context) {
        mContext = context;
    }

    public void setDatas(ArrayList<FundLevelData> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TransListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_level_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransListViewHolder holder, int position) {

        FundLevelData fundLevelData = mDatas.get(position);
        holder.level.setText(fundLevelData.level);
        holder.type.setText(fundLevelData.type);
        holder.trigger.setText(fundLevelData.trigger);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(mContext, SettingActivity.class);
//                intent.putExtra(SettingActivity.id, fundData.id);
//                mContext.startActivity(intent);
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

        TextView level;
        TextView type;
        TextView trigger;

        public TransListViewHolder(View itemView) {
            super(itemView);
            level = itemView.findViewById(R.id.level_item_level);
            type = itemView.findViewById(R.id.level_item_type);
            trigger = itemView.findViewById(R.id.level_item_trigger);

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

    private onItemLongClickListener onItemLongClickListener;

    public interface onItemLongClickListener {
        void onItemLongClick(View itemView, int positon);
    }

    public void setOnItemLongClickListener(onItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}
