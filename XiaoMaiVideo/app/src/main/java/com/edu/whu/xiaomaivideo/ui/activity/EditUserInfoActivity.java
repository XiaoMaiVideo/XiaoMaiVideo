package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.EditUserInfoAdapter;
import com.edu.whu.xiaomaivideo.adapter.SettingsAdapter;

public class EditUserInfoActivity extends AppCompatActivity {
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        recyclerView=findViewById(R.id.edit_user_info_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditUserInfoActivity.this));
        recyclerView.setAdapter(new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(EditUserInfoActivity.this, "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}