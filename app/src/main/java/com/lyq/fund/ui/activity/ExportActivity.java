package com.lyq.fund.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lyq.fund.R;
import com.lyq.fund.bean.ImportData;
import com.lyq.fund.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportActivity extends AppCompatActivity {

    public static final String tag = ExportActivity.class.getSimpleName();
    private Date selectDate = DateUtil.StringToDate(DateUtil.getCurDateStr(), "yyyy-MM-dd");
    private final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static final String key = "key";
    public static final int EXPORT_REQUEST_CODE = 1;
    private EditText mEtPrice;
    private EditText mEtNumber;
    private TextView mTvDate;

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
        mTvDate = findViewById(R.id.et_export_date);
    }

    private void initListener() {

        mTvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dp = new DatePickerDialog(ExportActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        selectDate = DateUtil.setDate(selectDate, year, month, day);
                        mTvDate.setText(format.format(selectDate));
                    }
                }, selectDate.getYear() + 1900, selectDate.getMonth(), selectDate.getDate());//year是从1900后开始的，所以要加1
                dp.show();

            }
        });

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

                if (TextUtils.isEmpty(mTvDate.getText().toString())) {
                    Toast.makeText(ExportActivity.this, "请输入正确的日期", Toast.LENGTH_SHORT).show();
                    return;
                }

                mImportData.setDate(mTvDate.getText().toString());
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