/**
 * Author: 叶俊豪
 * Create Time: 2020/7/22
 * Update Time: 2020/7/22
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.donkingliang.groupedadapter.holder.BaseViewHolder;
import com.edu.whu.xiaomaivideo.R;

import java.util.List;

public class SettingDialogAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private Context mContext;
    private List<String> mSettingNameList;
    private List<String> mSettingDescriptionList;
    public List<Boolean> mStatusList;
    public SettingDialogAdapter(Context context, List<String> settingNameList, List<String> settingDescriptionList, List<Boolean> statusList) {
        mContext = context;
        mSettingDescriptionList = settingDescriptionList;
        mSettingNameList = settingNameList;
        mStatusList = statusList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.setting_dialog_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        TextView settingName = holder.get(R.id.settingName);
        settingName.setText(mSettingNameList.get(position));
        TextView settingDescription = holder.get(R.id.settingDescription);
        settingDescription.setText(mSettingDescriptionList.get(position));
        CheckBox settingCheckBox = holder.get(R.id.settingCheckBox);
        settingCheckBox.setChecked(mStatusList.get(position));
        settingCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mStatusList.set(position, b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSettingDescriptionList.size();
    }
}
