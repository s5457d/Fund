package com.lyq.fund.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lyq.fund.R;
import com.lyq.fund.bean.ImportData;

public class ImportActivity extends AppCompatActivity {

    public static final String tag = ImportActivity.class.getSimpleName();
    public static final String key = "key";
    public static final int IMPORT_REQUEST_CODE = 0;
    private EditText mEtPrice;
    private EditText mEtNumber;
    private EditText mEtDate;
    private Spinner mSpLevel;
    private Spinner mSpType;

    private final ImportData mImportData = new ImportData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);

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
        mEtDate = findViewById(R.id.et_import_date);

        mSpLevel = findViewById(R.id.sp_level);
        mSpType = findViewById(R.id.sp_type);
    }

    private void initListener() {
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

                if (TextUtils.isEmpty(mEtDate.getText().toString())) {
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

                mImportData.setDate(mEtDate.getText().toString());
                mImportData.setNumber(mEtNumber.getText().toString());
                mImportData.setPrice(mEtPrice.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(key, mImportData);
                setResult(IMPORT_REQUEST_CODE, intent);
                finish();
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