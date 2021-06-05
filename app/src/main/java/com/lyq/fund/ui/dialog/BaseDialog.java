package com.lyq.fund.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lyq.fund.R;


public class BaseDialog extends Dialog implements View.OnClickListener {

    private final Context mContext;
    private OnClickListener mListener;
    private final TextView mTvTitle;
    private final Button mBtnCancel;
    private final Button mBtnConfirm;
    private final ImageView mIvIcon;

    private BaseDialog(Context context) {
        this(context, "", true);
    }

    private BaseDialog(Context context, String content) {
        this(context, content, true);

    }

    private BaseDialog(Context context, String content, Boolean isShowCancel) {
        super(context);
//        super(context, R.style.custom_dialog_style);
        mContext = context;

        View contentView = LayoutInflater.from(context).inflate(R.layout.base_view_dialog, null);
        setContentView(contentView);

        mIvIcon = findViewById(R.id.iv_icon);

        mTvTitle = findViewById(R.id.tv_title);
        mTvTitle.setText(content);
        mTvTitle.setMovementMethod(ScrollingMovementMethod.getInstance());

        mBtnCancel = findViewById(R.id.btn_cancel);
        mBtnCancel.setOnClickListener(this);

        mBtnConfirm = findViewById(R.id.btn_confirm);
        mBtnConfirm.setOnClickListener(this);

        if (!isShowCancel) {
            mBtnCancel.setVisibility(View.GONE);
            mBtnConfirm.setText(mContext.getResources().getString(R.string.base_got_it));
        }

        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_confirm) {
            dismiss();
            if (mListener != null) mListener.onConfirm();

        } else if (i == R.id.btn_cancel) {
            dismiss();
            if (mListener != null) mListener.onCancel();
        }
    }

    public void setDialogListener(OnClickListener listener) {
        mListener = listener;
    }

    public void setTitleText(String content) {
        mTvTitle.setText(content);
    }

    public void setButtonText(String cancel, String confirm) {
        mBtnCancel.setText(cancel);
        mBtnConfirm.setText(confirm);
    }

    public void clickConfirmButton() {
        mBtnConfirm.performClick();
    }

    public interface OnClickListener {
        void onConfirm();

        void onCancel();
    }

    public static class Builder {

        private final BaseDialog baseDialog;

        public Builder(Context context) {
            baseDialog = new BaseDialog(context);
        }

        public Builder setContent(String content) {
            baseDialog.mTvTitle.setText(content);
            return this;
        }

        public Builder setIcon(boolean isShow, int drawableId) {
            if (isShow) {
                baseDialog.mIvIcon.setVisibility(View.VISIBLE);
                baseDialog.mIvIcon.setImageResource(drawableId);
            }
            return this;
        }

        public Builder setCancelButton(Boolean isShow, String text) {
            if (isShow) {
                baseDialog.mBtnCancel.setText(text);
            } else {
                baseDialog.mBtnCancel.setVisibility(View.GONE);
            }
            return this;
        }

        public Builder setCancelButtonVisibility(Boolean isShow) {
            baseDialog.mBtnCancel.setVisibility(isShow ? View.VISIBLE : View.GONE);
            return this;
        }

        public Builder setCancelButtonText(String text) {
            baseDialog.mBtnCancel.setText(text);
            return this;
        }

        public Builder setConfirmButton(Boolean isShow, String text) {
            if (isShow) {
                baseDialog.mBtnConfirm.setText(text);
            } else {
                baseDialog.mBtnConfirm.setVisibility(View.GONE);
            }
            return this;
        }

        public Builder setConfirmButtonVisibility(Boolean isShow) {
            baseDialog.mBtnConfirm.setVisibility(isShow ? View.VISIBLE : View.GONE);
            return this;
        }

        public Builder setConfirmButtonText(String text) {
            baseDialog.mBtnConfirm.setText(text);
            return this;
        }

        public Builder setListener(OnClickListener listener) {
            baseDialog.mListener = listener;
            return this;
        }

        public BaseDialog create() {
            return baseDialog;
        }

        public BaseDialog createAndShow() {
            baseDialog.show();
            return baseDialog;
        }
    }
}
