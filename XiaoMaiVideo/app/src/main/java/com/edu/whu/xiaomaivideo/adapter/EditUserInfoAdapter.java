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

    private Context mContext;
    private OnItemClickListener mListener;
    private List<EditUserInfoViewModel.InfoMap> userInfoList;
    private boolean editUserInfo;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    public void setUserInfoList(List<EditUserInfoViewModel.InfoMap> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public EditUserInfoAdapter(Context context, OnItemClickListener listener,boolean editUserInfo)
    {
        this.mContext = context;
        this.mListener = listener;
        this.editUserInfo = editUserInfo;
        userInfoList = new ArrayList<>();
        sp = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public boolean commit() {
        for (EditUserInfoViewModel.InfoMap item:userInfoList) {
            if (item.key.equals("性别")) {
                if (!(item.value.equals("男")||item.value.equals("女"))) {
                    return false;
                }
            }
        }
        for (EditUserInfoViewModel.InfoMap item:userInfoList) {
            editor.putString(item.key,item.value);
            editor.apply();
            User user = Constant.CurrentUser;
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
            }
        }
        return true;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        if (editUserInfo) {
            return new EditUserInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.edit_user_info_item, viewGroup, false));
        } else {
            return new ShowUserInfoViewHolder(LayoutInflater.from(mContext).inflate(R.layout.show_user_info_item, viewGroup, false));
        }

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int i)
    {
        if (editUserInfo) {
            if (userInfoList.get(i).key.equals("性别")) {
                ((EditUserInfoViewHolder)viewHolder).textInputLayout.setHelperText("性别（男/女）");
            }
            else {
                ((EditUserInfoViewHolder) viewHolder).textInputLayout.setHelperText(userInfoList.get(i).key);
            }
            ((EditUserInfoViewHolder)viewHolder).textInputEditText.setText(userInfoList.get(i).value);
            ((EditUserInfoViewHolder)viewHolder).textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String key=userInfoList.get(i).key;
                    String value=((EditUserInfoViewHolder)viewHolder).textInputEditText.getText().toString();
                    if (key.equals("性别")) {
                        if (!(value.equals("男")||value.equals("女"))) {
                            ((EditUserInfoViewHolder) viewHolder).textInputLayout.setError("性别必须是男或女！");
                            return;
                        }
                        else {
                            ((EditUserInfoViewHolder)viewHolder).textInputLayout.setHelperText("性别（男/女）");
                        }
                    }
                    userInfoList.remove(i);
                    userInfoList.add(i,new EditUserInfoViewModel.InfoMap(key,value));
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        } else {
            ((ShowUserInfoViewHolder)viewHolder).textViewKey.setText(userInfoList.get(i).key);
            ((ShowUserInfoViewHolder)viewHolder).textViewValue.setText(userInfoList.get(i).value);
            ((ShowUserInfoViewHolder)viewHolder).textViewNum.setText(i+1+"");
        }

    }

    @Override
    public int getItemCount()
    {
        return userInfoList.size();
    }

    class EditUserInfoViewHolder extends RecyclerView.ViewHolder
    {

        private TextInputLayout textInputLayout;
        private TextInputEditText textInputEditText;

        public EditUserInfoViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textInputLayout = itemView.findViewById(R.id.edit_user_info_item_text_input_layout);
            textInputEditText = itemView.findViewById(R.id.edit_user_info_item_edit_info);
        }
    }

    class ShowUserInfoViewHolder extends RecyclerView.ViewHolder
    {

        private TextView textViewNum,textViewKey,textViewValue;
        public ShowUserInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNum=itemView.findViewById(R.id.show_user_info_item_num);
            textViewValue=itemView.findViewById(R.id.show_user_info_item_value);
            textViewKey=itemView.findViewById(R.id.show_user_info_item_key);
        }
    }

    public interface OnItemClickListener
    {
        void onClick(int pos);
    }
}