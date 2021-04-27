package com.lyq.fund.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyq.fund.R;
import com.lyq.fund.adapter.FundAdapter;
import com.lyq.fund.bean.FundData;
import com.lyq.fund.database.AppDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String tag = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private Button mAdd;
    private Button mRefresh;
    private FundAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.list);
        mAdd = findViewById(R.id.add);
        mRefresh = findViewById(R.id.refresh);

        mAdapter = new FundAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<FundData> fundData = (ArrayList<FundData>) AppDatabase.getInstance(MainActivity.this).FundDataDao().allFundDatas();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (fundData.size() > 0) {
                            mAdapter.setDatas(fundData);
                        } else {
                            mAdapter.clear();
                        }
                    }
                });
            }
        }).start();
    }



}