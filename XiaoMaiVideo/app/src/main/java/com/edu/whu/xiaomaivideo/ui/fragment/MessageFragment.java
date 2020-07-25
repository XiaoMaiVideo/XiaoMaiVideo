/**
 * Author: 张俊杰
 * Create Time: 2020/7/10
 * Update Time: 2020/7/23
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.MessageAdapter;
import com.edu.whu.xiaomaivideo.adapter.MsgAdapter;
import com.edu.whu.xiaomaivideo.databinding.MessageFragmentBinding;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.EventBusMessage;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: 李季东、叶俊豪、张俊杰
 * Create Time: 2020/7/14
 * Update Time: 2020/7/25
 * 视频详情页面
 */
public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;
    private MessageFragmentBinding messageFragmentBinding;
    MessageAdapter mAdapter;
    MsgAdapter msgAdapter;
    List<MessageViewModel.ShowMsg> showMsgs;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);
        messageFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.message_fragment,container,false);
        messageFragmentBinding.setViewmodel(messageViewModel);
        messageFragmentBinding.setLifecycleOwner(getActivity());
        showMsgs = new ArrayList<>();
        EventBus.getDefault().register(this);
        initAdapter();
        return messageFragmentBinding.getRoot();
    }

    private void initAdapter() {
        mAdapter = new MessageAdapter(getActivity());
        messageFragmentBinding.recyclerView3.setAdapter(mAdapter);
        messageFragmentBinding.recyclerView3.setLayoutManager(new LinearLayoutManager(getActivity()));

        msgAdapter = new MsgAdapter(getActivity(), showMsgs);
        messageFragmentBinding.msgRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        messageFragmentBinding.msgRecyclerView.setAdapter(msgAdapter);

        messageViewModel.getShowmsgs().observe(getViewLifecycleOwner(), new Observer<List<MessageViewModel.ShowMsg>>() {
            @Override
            public void onChanged(List<MessageViewModel.ShowMsg> showmsgs) {
                Log.e("MessageFragment", "更新"+showmsgs.size());
                showMsgs.clear();
                showMsgs.addAll(showmsgs);
                msgAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void updateShowMsgs(EventBusMessage message) {
        if (message.getType().equals(Constant.UPDATE_MESSAGE_LIST)) {
            messageViewModel.refreshUser();
        } else if (message.getType().equals(Constant.UPDATE_USER)) {
            messageViewModel.updateShowmsgs();
        }
    }
}