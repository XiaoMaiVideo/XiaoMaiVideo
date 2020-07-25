/**
 * Author: 叶俊豪
 * Create Time: 2020/7/21
 * Update Time: 2020/7/21
 */
package com.edu.whu.xiaomaivideo.ui.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MovieAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingDialogAdapter;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.util.XPopupUtils;

import java.util.List;

import de.mustafagercek.library.LoadingButton;

public class SettingDialog extends BottomPopupView {

    Context mContext;
    OnConfirmButtonClickListener mListener;
    RecyclerView mRecyclerView;
    SettingDialogAdapter mAdapter;
    private List<String> mSettingNameList;
    private List<String> mSettingDescriptionList;
    public List<Boolean> mStatusList;
    String mSettingTitle;
    public SettingDialog(@NonNull Context context, String settingTitle, List<String> settingNameList, List<String> settingDescriptionList, List<Boolean> statusList, SettingDialog.OnConfirmButtonClickListener onClickListener) {
        super(context);
        this.mContext = context;
        this.mListener = onClickListener;
        this.mSettingNameList = settingNameList;
        this.mSettingDescriptionList = settingDescriptionList;
        this.mStatusList = statusList;
        this.mSettingTitle = settingTitle;
        mAdapter = new SettingDialogAdapter(mContext, mSettingNameList, mSettingDescriptionList, mStatusList);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.setting_dialog;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        LoadingButton confirmButton = findViewById(R.id.saveSettingBtn);
        TextView textView = findViewById(R.id.settingTitle);
        textView.setText(mSettingTitle);
        ImageView imageView = findViewById(R.id.dialog_bottomsheet_iv_close);
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingDialog.this.dismiss();
            }
        });
        confirmButton.setButtonOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mStatusList = mAdapter.mStatusList;
                mListener.onClick(confirmButton, SettingDialog.this, mStatusList);
            }
        });
        mRecyclerView = findViewById(R.id.settingRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
    }

    public interface OnConfirmButtonClickListener {
        void onClick(LoadingButton button, SettingDialog dialog, List<Boolean> statusList);
    }
}
