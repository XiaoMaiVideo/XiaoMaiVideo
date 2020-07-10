package com.edu.whu.xiaomaivideo.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.databinding.MessageFragmentBinding;
import com.edu.whu.xiaomaivideo.viewModel.MessageViewModel;

import java.util.Objects;

public class MessageFragment extends Fragment {

    private MessageViewModel messageViewModel;
    private MessageFragmentBinding messageFragmentBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messageViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(MessageViewModel.class);
        messageFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.message_fragment,container,false);
        messageFragmentBinding.setViewmodel(messageViewModel);
        messageFragmentBinding.setLifecycleOwner(getActivity());
        return messageFragmentBinding.getRoot();
    }
}