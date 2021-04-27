package com.lyq.fund.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lyq.fund.R;
import com.lyq.fund.bean.FundData;
import com.lyq.fund.database.AppDatabase;

public class AddActivity extends AppCompatActivity {

    public static final String tag = AddActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        EditText name = findViewById(R.id.et_name);
        EditText code = findViewById(R.id.et_code);
        EditText lastPrice = findViewById(R.id.et_last);

        Button save = findViewById(R.id.save);
        Button cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(name.getText())) {
                    Toast.makeText(AddActivity.this, "请输入有效的基金名称", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(code.getText())) {
                    Toast.makeText(AddActivity.this, "请输入有效的基金编码", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(lastPrice.getText())) {
                    Toast.makeText(AddActivity.this, "请输入有效的基金价格", Toast.LENGTH_LONG).show();
                    return;
                }

                FundData fundData = new FundData();
                fundData.name = name.getText().toString();
                fundData.code = code.getText().toString();
                fundData.lastPrice = lastPrice.getText().toString();
                fundData.id = 0;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long[] longs = AppDatabase.getInstance(AddActivity.this).FundDataDao().insertfundDatas(fundData);
                        if (longs[0] >= 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AddActivity.this, "保存成功", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
}