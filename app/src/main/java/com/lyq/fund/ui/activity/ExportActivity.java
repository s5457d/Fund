package com.lyq.fund.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lyq.fund.R;
import com.lyq.fund.bean.ImportData;

public class ExportActivity extends AppCompatActivity {

    public static final String tag = ExportActivity.class.getSimpleName();
    public static final String key = "key";
    public static final int EXPORT_REQUEST_CODE = 1;
    private EditText mEtPrice;
    private EditText mEtNumber;
    private EditText mEtDate;

    private final ImportData mImportData = new ImportData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        init();

        initData();

        initListener();
    }

    private void initData() {
//        ArrayAdapter<CharSequence> levelAdapter = ArrayAdapter.createFromResource(this,
//                R.array.level, android.R.layout.simple_spinner_item);
//        levelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpLevel.setAdapter(levelAdapter);
//
//        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
//                R.array.type, android.R.layout.simple_spinner_item);
//        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        mSpType.setAdapter(typeAdapter);
    }

    private void init() {
        mEtPrice = findViewById(R.id.et_export_price);
        mEtNumber = findViewById(R.id.et_export_number);
        mEtDate = findViewById(R.id.et_export_date);
    }

    private void initListener() {
        findViewById(R.id.export_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        findViewById(R.id.export_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEtPrice.getText().toString())) {
                    Toast.makeText(ExportActivity.this, "请输入正确的价格", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mEtNumber.getText().toString())) {
                    Toast.makeText(ExportActivity.this, "请输入正确的股数", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(mEtDate.getText().toString())) {
                    Toast.makeText(ExportActivity.this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                mImportData.setDate(mEtDate.getText().toString());
                mImportData.setNumber(mEtNumber.getText().toString());
                mImportData.setPrice(mEtPrice.getText().toString());

                Intent intent = new Intent();
                intent.putExtra(key, mImportData);
                setResult(EXPORT_REQUEST_CODE, intent);
                finish();
            }
        });
    }
}