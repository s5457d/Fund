package com.lyq.fund.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lyq.fund.R;
import com.lyq.fund.bean.FundLevelData;
import com.lyq.fund.bean.ImportData;
import com.lyq.fund.database.AppDatabase;
import com.lyq.fund.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ImportActivity extends AppCompatActivity {

    public static final String tag = ImportActivity.class.getSimpleName();
    private Date selectDate = DateUtil.StringToDate(DateUtil.getCurDateStr(), "yyyy-MM-dd");
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static final String key = "key";
    public static final String CODE = "code";
    public static final int IMPORT_REQUEST_CODE = 0;
    private EditText mEtPrice;
    private EditText mEtNumber;
    private TextView mTvDate;
    private Spinner mSpLevel;
    private Spinner mSpType;
    private String code;

    private final ImportData mImportData = new ImportData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

        code = getIntent().getStringExtra(CODE);

        init();

        initData();

        initListener();
    }

    private void initData() {
        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this,
                R.array.level, android.R.layout.simple_spinner_item);
        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpLevel.setAdapter(levelAdapter);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.type, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpType.setAdapter(typeAdapter);
    }

    private void init() {
        mEtPrice = findViewById(R.id.et_import_price);
        mEtNumber = findViewById(R.id.et_import_number);
        mTvDate = findViewById(R.id.et_import_date);

        mSpLevel = findViewById(R.id.sp_level);
        mSpType = findViewById(R.id.sp_type);
    }

    private void initListener() {
        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(ImportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectDate = DateUtil.setDate(selectDate, year, month, day);
                        mTvDate.setText(format.format(selectDate));
                    }
                }, selectDate.getYear() + 1900, selectDate.getMonth(), selectDate.getDate());//year是从1900后开始的，所以要加1
                dp.show();

            }
        });

        findViewById(R.id.import_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.import_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtPrice.getText().toString())) {
                    Toast.makeText(ImportActivity.this, "请输入正确的价格", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mEtNumber.getText().toString())) {
                    Toast.makeText(ImportActivity.this, "请输入正确的股数", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mTvDate.getText().toString())) {
                    Toast.makeText(ImportActivity.this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mImportData.getLevel())) {
                    Toast.makeText(ImportActivity.this, "请选择正确的档位", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mImportData.getType())) {
                    Toast.makeText(ImportActivity.this, "请选择正确的类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                mImportData.setDate(mTvDate.getText().toString());
                mImportData.setNumber(mEtNumber.getText().toString());
                mImportData.setPrice(mEtPrice.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FundLevelData fundLevelData = AppDatabase.getInstance(ImportActivity.this).FundLevelDataDao().fundLevelDatasByCodeLevel(code, mImportData.getLevel(), "2");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (fundLevelData == null) {
                                    Toast.makeText(ImportActivity.this, "您并没有设置该买入的档位", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                Intent intent = new Intent();
                                intent.putExtra(key, mImportData);
                                setResult(IMPORT_REQUEST_CODE, intent);
                                finish();
                            }
                        });

                    }
                }).start();

            }
        });

        mSpLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mImportData.setLevel((i + 1) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mImportData.setType("A");
                } else {
                    mImportData.setType("B");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}