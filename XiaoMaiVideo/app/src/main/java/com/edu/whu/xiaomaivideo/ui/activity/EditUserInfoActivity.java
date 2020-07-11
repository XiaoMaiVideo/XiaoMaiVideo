package com.edu.whu.xiaomaivideo.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.EditUserInfoAdapter;
import com.edu.whu.xiaomaivideo.viewModel.EditUserInfoViewModel;

import java.util.List;
import java.util.Objects;

public class EditUserInfoActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditUserInfoViewModel editUserInfoViewModel;
    EditUserInfoAdapter editUserInfoAdapter1,editUserInfoAdapter2;
    Button button,button2;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        recyclerView=findViewById(R.id.edit_user_info_recyclerView);
        button=findViewById(R.id.button);
        button2=findViewById(R.id.button2);
        imageView=findViewById(R.id.imageView);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditUserInfoActivity.this));
        editUserInfoAdapter1=new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
            }
        },true);
        editUserInfoAdapter2=new EditUserInfoAdapter(EditUserInfoActivity.this, new EditUserInfoAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
            }
        },false);
        recyclerView.setAdapter(editUserInfoAdapter2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("修改信息")){
                    recyclerView.setAdapter(editUserInfoAdapter1);
                    button.setText("提交信息");
                }else {
                    editUserInfoAdapter1.commit();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                //action表示intent的类型，可以是查看、删除、发布或其他情况；我们选择ACTION_GET_CONTENT，系统可以根据Type类型来调用系统程序选择Type
                //类型的内容给你选择
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //如果第二个参数大于或等于0，那么当用户操作完成后会返回到本程序的onActivityResult方法
                startActivityForResult(intent, 1);
            }
        });

        editUserInfoViewModel = new ViewModelProvider(Objects.requireNonNull(this)).get(EditUserInfoViewModel.class);

        editUserInfoViewModel.getAllUserInfo().observe(this, new Observer<List<EditUserInfoViewModel.InfoMap>>() {
            @Override
            public void onChanged(List<EditUserInfoViewModel.InfoMap> infoMaps) {
                editUserInfoAdapter1.setUserInfoList(infoMaps);
                editUserInfoAdapter2.setUserInfoList(infoMaps);
                editUserInfoAdapter1.notifyDataSetChanged();
                editUserInfoAdapter2.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //用户操作完成，结果码返回是-1，即RESULT_OK
        if(resultCode==RESULT_OK){
            //获取选中文件的定位符
            Uri uri = data.getData();

            Glide.with(this).load(uri).into(imageView);
        }else{
            //操作错误或没有选择图片
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}