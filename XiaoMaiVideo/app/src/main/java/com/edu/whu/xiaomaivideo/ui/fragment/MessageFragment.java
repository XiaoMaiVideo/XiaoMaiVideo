/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/14
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MessageAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsFriendAdpater;
import com.edu.whu.xiaomaivideo.databinding.MessageFragmentBinding;
import com.edu.whu.xiaomaivideo.ui.activity.TakeVideoActivity;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import java.util.Objects;

/**
 * Author: 李季东（修改）
 * Create Time: 未知
 * Update Time: 2020/7/15
 * 视频详情页面
 */
public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;
    private MessageFragmentBinding messageFragmentBinding;
    MessageAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);
        messageFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.message_fragment,container,false);
        messageFragmentBinding.setViewmodel(messageViewModel);
        messageFragmentBinding.setLifecycleOwner(getActivity());
        /*
        messageFragmentBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TakeVideoActivity.class);
                startActivity(intent);
            }
        });
        */
        initAdapter();

        return messageFragmentBinding.getRoot();
    }
    private void initAdapter() {
        mAdapter=new MessageAdapter(getActivity(), new MessageAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                //点击item发生的事情
                Toast.makeText(getActivity(), "click..." + pos, Toast.LENGTH_SHORT).show();
//                Intent intent =new Intent(getActivity(), VideoDetialActivity.class);
//                startActivity(intent);
            }
        });
        messageFragmentBinding.recyclerView3.setAdapter(mAdapter);
        messageFragmentBinding.recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}