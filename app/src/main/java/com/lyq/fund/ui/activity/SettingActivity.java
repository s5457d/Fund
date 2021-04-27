package com.lyq.fund.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyq.fund.R;
import com.lyq.fund.adapter.FundDetailAdapter;
import com.lyq.fund.adapter.FundDetailExportAdapter;
import com.lyq.fund.adapter.FundLevelAdapter;
import com.lyq.fund.bean.FundData;
import com.lyq.fund.bean.FundLevelData;
import com.lyq.fund.bean.ImportData;
import com.lyq.fund.database.AppDatabase;
import com.lyq.fund.ui.dialog.BaseDialog;

import java.util.ArrayList;
import java.util.List;

public class SettingActivity extends AppCompatActivity {

    public static final String tag = SettingActivity.class.getSimpleName();
    public static final String id = "id";
    private FundData mFundData;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewExport;
    private FundDetailAdapter mAdapter;
    private FundDetailExportAdapter mAdapterExport;
    private EditText mLast2;
    private TextView mCode2;
    private TextView mName2;
    private ArrayList<FundLevelData> mFundLevelData;
    private ArrayList<FundLevelData> mFundLevelDataExport;
    private long selectID = -1;
    private View selectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mLast2 = findViewById(R.id.last2);
        mCode2 = findViewById(R.id.code2);
        mName2 = findViewById(R.id.name2);

        mRecyclerView = findViewById(R.id.detail_list);
        mAdapter = new FundDetailAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerViewExport = findViewById(R.id.detail_export_list);
        mAdapterExport = new FundDetailExportAdapter(this);
        mRecyclerViewExport.setAdapter(mAdapterExport);


        LinearLayoutManager linearLayoutManagerExport = new LinearLayoutManager(this);
        linearLayoutManagerExport.setSmoothScrollbarEnabled(true);
        linearLayoutManagerExport.setAutoMeasureEnabled(true);
        mRecyclerViewExport.setLayoutManager(linearLayoutManagerExport);
        mRecyclerViewExport.setHasFixedSize(true);

        Intent intent = getIntent();
        long id = intent.getLongExtra(SettingActivity.id, -1);
        if (id != -1) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mFundData = AppDatabase.getInstance(SettingActivity.this).FundDataDao().queryFundDataByID(id);
                    if (mFundData != null) {
                        initData();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mName2.setText(mFundData.name);
                                mCode2.setText(mFundData.code);
                                mLast2.setText(mFundData.lastPrice);
                            }
                        });
                    }
                }
            }).start();
        }

        initListener();
    }

    private void initData() {
        mFundLevelData = (ArrayList<FundLevelData>) AppDatabase.getInstance(this).FundLevelDataDao().allFundLevelDatasByCode(mFundData.code, "0");
        mFundLevelDataExport = (ArrayList<FundLevelData>) AppDatabase.getInstance(this).FundLevelDataDao().allFundLevelDatasByCode(mFundData.code, "1");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mFundLevelData.size() > 0) {
                    mAdapter.setDatas(mFundLevelData);
                } else {
                    mAdapter.clear();
                }

                if (mFundLevelDataExport.size() > 0) {
                    mAdapterExport.setDatas(mFundLevelDataExport);
                } else {
                    mAdapterExport.clear();
                }
            }
        });
    }

    private void initListener() {

        mAdapter.setOnItemLongClickListener(new FundLevelAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int positon) {
                itemView.setBackgroundColor(Color.BLUE);

                if (selectView != null && selectView != itemView) {
                    selectView.setBackgroundColor(Color.WHITE);
                }
                selectView = itemView;
                selectID = mFundLevelData.get(positon).id;
                Toast.makeText(SettingActivity.this, "选择成功", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.bt_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mLast2.getText())) {
                    Toast.makeText(SettingActivity.this, "请输入有效的基金金额", Toast.LENGTH_LONG).show();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mFundData.lastPrice = mLast2.getText().toString();
                        int i = AppDatabase.getInstance(SettingActivity.this).FundDataDao().updateFundDataByID(mFundData);
                        if (i != 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });

        findViewById(R.id.bt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BaseDialog.Builder(SettingActivity.this)
                        .setContent("您确定要删除吗？")
                        .setConfirmButtonText(getString(R.string.confirm))
                        .setListener(new BaseDialog.OnClickListener() {
                            @Override
                            public void onConfirm() {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        int i = AppDatabase.getInstance(SettingActivity.this).FundDataDao().deletefundDatas(mFundData);
                                        Log.i(tag, "删除数据库:" + i);
                                        if (i != 0) {
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(SettingActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
//                                        if (mFundLevelData.size() > 0) {
//                                            AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().deleteFundLevelDatas(mFundLevelData);
//                                            initData();
//                                        }
                                        initData();
                                    }
                                }).start();
                            }

                            @Override
                            public void onCancel() {

                            }
                        }).createAndShow();
            }
        });

        findViewById(R.id.bt_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, LevelActivity.class);
                intent.putExtra(LevelActivity.CODE, mFundData.code);
                startActivity(intent);
            }
        });

        findViewById(R.id.bt_import).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<FundLevelData> fundLevelData1 = AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().allFundLevelDatasByCode(mFundData.code, "2");
                        if (fundLevelData1.size() <= 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SettingActivity.this, "您没有设置任何的档位", Toast.LENGTH_LONG).show();
                                }
                            });
                            return;
                        }

                        Intent intent = new Intent(SettingActivity.this, ImportActivity.class);
                        intent.putExtra(ImportActivity.CODE, mFundData.code);
                        startActivityForResult(intent, ImportActivity.IMPORT_REQUEST_CODE);
                    }
                }).start();


            }
        });

        findViewById(R.id.bt_export).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectID == -1) {
                    Toast.makeText(SettingActivity.this, "请先选择一条记录后，再点击卖出", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(SettingActivity.this, ExportActivity.class);
                startActivityForResult(intent, ExportActivity.EXPORT_REQUEST_CODE);
            }
        });

        findViewById(R.id.bt_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingActivity.this, "您点击了卖出历史，暂未实现", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ImportData importData;
                    FundLevelData fundLevelData = new FundLevelData();
                    if (requestCode == ImportActivity.IMPORT_REQUEST_CODE) {
                        importData = (ImportData) data.getExtras().get(ImportActivity.key);
                        fundLevelData.code = mFundData.code;
                        fundLevelData.level = importData.getLevel();
                        fundLevelData.type = importData.getType();
                        fundLevelData.importPrice = importData.getPrice();
                        fundLevelData.importNum = importData.getNumber();
                        fundLevelData.importDate = importData.getDate();
                        Log.i(tag, "买入数据:"+ fundLevelData.toString());
                        FundLevelData fundLevelData1 = AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().fundLevelDatasByCodeLevel(fundLevelData.code, fundLevelData.level,"2");
                        fundLevelData.trigger = fundLevelData1.trigger;
                        fundLevelData.done = "0";
                    } else {
                        importData = (ImportData) data.getExtras().get(ExportActivity.key);
                        fundLevelData = AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().fundLevelDatasByID(selectID);
                        fundLevelData.done = "1";
                        fundLevelData.id = selectID;
                        fundLevelData.exportPrice = importData.getPrice();
                        fundLevelData.exportDate = importData.getDate();
                        fundLevelData.exportNum = importData.getNumber();
                    }

                    if (requestCode == ImportActivity.IMPORT_REQUEST_CODE) {
                        AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().insertFundLevelDatas(fundLevelData);
                    } else {
                        AppDatabase.getInstance(SettingActivity.this).FundLevelDataDao().updateFundLevelDataByID(fundLevelData);
                    }
                    selectID = -1;
                    initData();
                }
            }).start();
        }
    }
}