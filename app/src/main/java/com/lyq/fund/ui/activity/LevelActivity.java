package com.lyq.fund.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lyq.fund.R;
import com.lyq.fund.adapter.FundLevelAdapter;
import com.lyq.fund.bean.FundLevelData;
import com.lyq.fund.database.AppDatabase;

import java.util.ArrayList;

public class LevelActivity extends AppCompatActivity {

    public static final String tag = LevelActivity.class.getSimpleName();
    public static final String CODE = "code";
    public static final int requestCode = 0;
    private TextView mTvCode;
    private EditText mEtTrigger;
    private Spinner mSpLevel;
    private Spinner mSpType;
    private RecyclerView mRecyclerView;

    private FundLevelData mFundLevelData = new FundLevelData();
    private String mCode;
    private ArrayList<FundLevelData> mFundLevelDataArrayList;
    private FundLevelAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Intent intent = getIntent();
        mCode = intent.getStringExtra(CODE);
        mFundLevelData.code = mCode;
        init();

        initListener();
    }

    private void init() {
        mTvCode = findViewById(R.id.tv_level_code_value);
        mTvCode.setText(mCode);
        mEtTrigger = findViewById(R.id.et_level_trigger);

        mSpLevel = findViewById(R.id.sp_level);
        mSpType = findViewById(R.id.sp_type);

        mRecyclerView = findViewById(R.id.level_list);

        mAdapter = new FundLevelAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this,
                R.array.level, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpLevel.setAdapter(levelAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpType.setAdapter(typeAdapter);
    }

    private void initListener() {

        mAdapter.setOnItemLongClickListener(new FundLevelAdapter.onItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, int positon) {
                mFundLevelData = mFundLevelDataArrayList.get(positon);
                mEtTrigger.setText(mFundLevelData.trigger);
                Log.i(tag, "select:" + mFundLevelData.toString());
                mSpLevel.setSelection(Integer.parseInt(mFundLevelData.level) - 1);
                if (mFundLevelData.type.equalsIgnoreCase("A")) {
                    mSpType.setSelection(0);
                } else {
                    mSpType.setSelection(1);
                }
            }
        });

        findViewById(R.id.level_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkParam()) {
                    mFundLevelData.trigger = mEtTrigger.getText().toString();
                    mFundLevelData.done = "2";
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            FundLevelData fundLevelData = AppDatabase.getInstance(LevelActivity.this).FundLevelDataDao().fundLevelDatasByCodeLevel(mCode, mFundLevelData.level, "2");
                            if (fundLevelData != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LevelActivity.this, "该档位已经设置过了", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            AppDatabase.getInstance(LevelActivity.this).FundLevelDataDao().insertFundLevelDatas(mFundLevelData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LevelActivity.this, "新增档位成功", Toast.LENGTH_SHORT).show();
                                }
                            });
                            initData();
                        }
                    }).start();
                }
            }
        });

        findViewById(R.id.level_modify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkParam()) {
                    if (mFundLevelData.id > 0) {
                        mFundLevelData.trigger = mEtTrigger.getText().toString();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase.getInstance(LevelActivity.this).FundLevelDataDao().updateFundLevelDataByID(mFundLevelData);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(LevelActivity.this, "修改档位成功", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                initData();
                            }
                        }).start();

                    }
                }
            }
        });


        mSpLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(tag, "level select item " + i);
                mFundLevelData.level = (i + 1) + "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(tag, "type select item " + i);
                if (i == 0) {
                    mFundLevelData.type = "A";
                } else {
                    mFundLevelData.type = "B";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private boolean checkParam() {
        if (TextUtils.isEmpty(mFundLevelData.level)) {
            Toast.makeText(LevelActivity.this, "请检查档位", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mFundLevelData.type)) {
            Toast.makeText(LevelActivity.this, "请检查类型", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(mEtTrigger.getText().toString())) {
            Toast.makeText(LevelActivity.this, "请检查触发价", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
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
                mFundLevelDataArrayList = (ArrayList<FundLevelData>) AppDatabase.getInstance(LevelActivity.this).FundLevelDataDao().allFundLevelDatasByCode(mCode, "2");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mFundLevelDataArrayList.size() > 0) {
                            mAdapter.setDatas(mFundLevelDataArrayList);
                        } else {
                            mAdapter.clear();
                        }
                    }
                });
            }
        }).start();
    }
}