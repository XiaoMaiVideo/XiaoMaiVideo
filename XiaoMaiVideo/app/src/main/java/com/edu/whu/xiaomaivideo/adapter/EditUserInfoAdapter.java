/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/10
 * Update Time: 2020/7/12
 */

package com.edu.whu.xiaomaivideo.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.viewModel.EditUserInfoViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class EditUserInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private final int SECOND_TYPE_EDIT_VIEW = 10;

    private Context mContext;
    private OnItemClickListener mListener;
    private List<EditUserInfoViewModel.InfoMap> userInfoList;
    private boolean editUserInfo;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public void setUserInfoList(List<EditUserInfoViewModel.InfoMap> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public EditUserInfoAdapter(Context context, OnItemClickListener listener, boolean editUserInfo)
    {
        this.mContext = context;
        this.mListener = listener;
        this.editUserInfo = editUserInfo;
        userInfoList = new ArrayList<>();
        // sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        // editor = sp.edit();
    }

    public EditUserInfoAdapter(Context context, boolean editUserInfo)
    {
        this.mContext = context;
        this.editUserInfo = editUserInfo;
        userInfoList = new ArrayList<>();
        // sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        // editor = sp.edit();
    }

    public void commit() {
        User user = Constant.currentUser;
        for (EditUserInfoViewModel.InfoMap item: userInfoList) {
            // editor.putString(item.key,item.value);
            // editor.apply();
            switch (item.key) {
                case "昵称":
                    user.setNickname(item.value);
                    break;
                case "性别":
                    user.setGender(item.value);
                    break;
                case "个人简介":
                    user.setDescription(item.value);
                    break;
                case "工作单位":
                    user.setWorkplace(item.value);
                    break;
                case "生日":
                    user.setBirthday(item.value);
                    break;
                case "地区":
                    user.setArea(item.value);
                    break;
            }
        }
        Constant.currentUser = user;
    }

    public void updateBirthday(String birthday) {
        userInfoList.get(4).value = birthday;
    }

    public void updateGender(String gender) {
        userInfoList.get(3).value = gender;
    }

    public void updateArea(String area) {
        userInfoList.get(5).value = area;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        if (editUserInfo) {
            if (i == SECOND_TYPE_EDIT_VIEW) {
                return new EditUserInfoViewHolder_2(LayoutInflater.from(mContext).inflate(R.layout.edit_user_info_item_2, viewGroup, false));
            }
            else {
                return new EditUserInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.edit_user_info_item, viewGroup, false));
            }
        } else {
            return new ShowUserInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.show_user_info_item, viewGroup, false));
        }
    }



    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i) {
        if (editUserInfo) {
            if (i < 3) {
                EditUserInfoViewHolder editUserInfoVH = (EditUserInfoViewHolder) viewHolder;
                editUserInfoVH.textInputLayout.setHelperText(userInfoList.get(i).key);
                editUserInfoVH.textInputEditText.setText(userInfoList.get(i).value);
                editUserInfoVH.textInputEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        userInfoList.get(i).value = editUserInfoVH.textInputEditText.getText().toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                ((EditUserInfoViewHolder) viewHolder).textViewNum.setText(i + 1 + "");
            }
            else {
                EditUserInfoViewHolder_2 editUserInfoVH_2 = (EditUserInfoViewHolder_2) viewHolder;
                editUserInfoVH_2.textViewNum.setText(i + 1 + "");
                editUserInfoVH_2.editInfoItem.setText("选择"+userInfoList.get(i).key);
                viewHolder.itemView.setOnClickListener(view -> mListener.onClick(editUserInfoVH_2, i));
            }
        } else {
            ((ShowUserInfoViewHolder)viewHolder).textViewKey.setText(userInfoList.get(i).key);
            ((ShowUserInfoViewHolder)viewHolder).textViewValue.setText(userInfoList.get(i).value);
            ((ShowUserInfoViewHolder)viewHolder).textViewNum.setText(i+1+"");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 3) {
            return SECOND_TYPE_EDIT_VIEW;
        }
        else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getItemCount()
    {
        return userInfoList.size();
    }

    static class EditUserInfoViewHolder extends RecyclerView.ViewHolder {
        private TextInputLayout textInputLayout;
        private TextInputEditText textInputEditText;
        private TextView textViewNum;

        public EditUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textInputLayout = itemView.findViewById(R.id.edit_user_info_item_text_input_layout);
            textInputEditText = itemView.findViewById(R.id.edit_user_info_item_edit_info);
            textViewNum = itemView.findViewById(R.id.edit_user_info_item_num);
        }
    }

    public static class EditUserInfoViewHolder_2 extends RecyclerView.ViewHolder {
        public TextView textViewNum, editInfoItem;

        public EditUserInfoViewHolder_2(@NonNull View itemView) {
            super(itemView);
            textViewNum = itemView.findViewById(R.id.edit_user_info_item_num);
            editInfoItem = itemView.findViewById(R.id.edit_info);
        }
    }

    static class ShowUserInfoViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNum, textViewKey, textViewValue;

        public ShowUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNum = itemView.findViewById(R.id.show_user_info_item_num);
            textViewValue = itemView.findViewById(R.id.show_user_info_item_value);
            textViewKey = itemView.findViewById(R.id.show_user_info_item_key);
        }
    }

    public interface OnItemClickListener {
        void onClick(EditUserInfoViewHolder_2 viewHolder, int pos);
    }
}