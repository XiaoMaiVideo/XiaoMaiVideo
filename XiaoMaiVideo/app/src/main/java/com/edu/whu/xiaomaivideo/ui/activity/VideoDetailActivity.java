/**
 * Author: 张俊杰
 * Create Time: 2020/7/16
 * Update Time: 2020/7/16
 */


package com.edu.whu.xiaomaivideo.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.CommentAdapter;
import com.edu.whu.xiaomaivideo.databinding.ActivityVideoDetailBinding;
import com.edu.whu.xiaomaivideo.ui.dialog.ShareDialog;
import com.lxj.xpopup.XPopup;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.viewModel.VideoDetailModel;

import org.parceler.Parcels;

import java.util.Objects;

/**
 * Author: 李季东 张俊杰 叶俊豪
 * Create Time: 2020/7/14
 * Update Time: 2020/7/16
 * 视频详情页面
 */
public class VideoDetailActivity extends AppCompatActivity {

    VideoDetailModel videoDetailModel;
    ActivityVideoDetailBinding activityVideoDetailBinding;
    CommentAdapter mAdapter;

    @BindingAdapter("app:avatarSrc")
    public static void setAvatar(ImageView imageView, String url) {
        // 用户圆形头像
        Glide.with(imageView.getContext())
                .load(url)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoDetailModel =new ViewModelProvider(Objects.requireNonNull(this)).get(VideoDetailModel.class);
        activityVideoDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_video_detail);
        activityVideoDetailBinding.setViewmodel(videoDetailModel);
        activityVideoDetailBinding.setLifecycleOwner(this);
        // Bundle bundle = getIntent().getExtras();

        // 当前页面显示的Movie对象，可以用它来做数据绑定之类的
        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        // 有些信息可能没获取到，需要再访问网络，就在这里访问一下

        videoDetailModel.setMovie(movie);
        // activityVideoDetailBinding.friendText.setText();
        // activityVideoDetailBinding.friendVideo.setVideoURI(Uri.parse(bundle.getString("videoUrl")));
        // activityVideoDetailBinding.friendImage.setImageResource(bundle.getInt("userImg"));

        /*activityVideoDetailBinding.friendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new XPopup.Builder(VideoDetailActivity.this)
                        .asCustom(new ShareDialog(VideoDetailActivity.this, movie).show());
            }
        });*/

        initAdapter();
    }
    private void initAdapter() {
        mAdapter=new CommentAdapter(this, new CommentAdapter.OnItemClickListener()
        {
            @Override
            public void onClick(int pos)
            {
                Toast.makeText(VideoDetailActivity.this, "click..." + pos, Toast.LENGTH_SHORT).show();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        activityVideoDetailBinding.recyclerView2.setLayoutManager(linearLayoutManager);
        activityVideoDetailBinding.recyclerView2.setAdapter(mAdapter);
    }

}
