/**
 * author:何慷
 * 时间：2020/7/17
 * 描述：仿抖音评论dialog 使用design的BottomSheetDialog
 */
package com.edu.whu.xiaomaivideo.ui.activity;

import android.content.res.Resources;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.CommentDialogSingleAdapter;
import com.edu.whu.xiaomaivideo.model.FirstLevelComment;
import com.edu.whu.xiaomaivideo.ui.dialog.InputTextMsgDialog;
import com.edu.whu.xiaomaivideo.widget.CommentRecyclerView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class CommentSingleActivity extends AppCompatActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private List<FirstLevelComment> data = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialog;
    private InputTextMsgDialog inputTextMsgDialog;
    private float slideOffset = 0;
    private String content = "我听见你的声音，有种特别的感觉。让我不断想，不敢再忘记你。" +
            "如果真的有一天，爱情理想会实现，我会加倍努力好好对你，永远不改变";
    private CommentDialogSingleAdapter bottomSheetAdapter;
    private RecyclerView rv_dialog_lists;
    private long totalCount = 30;//总条数不得超过它
    private int offsetY;
    private CommentRecyclerView mCommentRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_multi);
        mCommentRecyclerView = new CommentRecyclerView();
        initData();
        showSheetDialog();
    }

    //初始化数据 在项目中是从服务器获取数据
    private void initData() {
        for (int i = 0; i < 10; i++) {
            FirstLevelComment firstLevelComment = new FirstLevelComment();
            firstLevelComment.setContent("第" + (i + 1) + "人评论内容" + (i % 3 == 0 ? content + (i + 1) + "次" : ""));
            firstLevelComment.setCreateTime(System.currentTimeMillis());
            firstLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3370302115,85956606&fm=26&gp=0.jpg");
            firstLevelComment.setId(i + "");
            firstLevelComment.setUserId("UserId" + i);
            firstLevelComment.setIsLike(0);
            firstLevelComment.setPosition(i);
            firstLevelComment.setLikeCount(i);
            firstLevelComment.setUserName("星梦缘" + (i + 1));
            data.add(firstLevelComment);
        }
    }

    public void show(View view) {
        slideOffset = 0;
        bottomSheetDialog.show();
    }

    private void showSheetDialog() {
        if (bottomSheetDialog != null) {
            bottomSheetDialog.show();
            return;
        }
        View view = View.inflate(this, R.layout.dialog_bottomsheet, null);
        ImageView iv_dialog_close = (ImageView) view.findViewById(R.id.dialog_bottomsheet_iv_close);
        rv_dialog_lists = (RecyclerView) view.findViewById(R.id.dialog_bottomsheet_rv_lists);
        RelativeLayout rl_comment = view.findViewById(R.id.rl_comment);

        iv_dialog_close.setOnClickListener(v -> bottomSheetDialog.dismiss());

        rl_comment.setOnClickListener(v -> {
            CommentSingleActivity.this.initInputTextMsgDialog(null, false, null, -1);
        });

        bottomSheetAdapter = new CommentDialogSingleAdapter();
        bottomSheetAdapter.setNewData(data);
        rv_dialog_lists.setHasFixedSize(true);
        rv_dialog_lists.setLayoutManager(new LinearLayoutManager(this));
        rv_dialog_lists.setItemAnimator(new DefaultItemAnimator());
        bottomSheetAdapter.setLoadMoreView(new SimpleLoadMoreView());
        bottomSheetAdapter.setOnLoadMoreListener(this, rv_dialog_lists);
        rv_dialog_lists.setAdapter(bottomSheetAdapter);

        initListener();

        bottomSheetDialog = new BottomSheetDialog(this, R.style.dialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        final BottomSheetBehavior mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        mDialogBehavior.setPeekHeight(getWindowHeight());
        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else if (newState == BottomSheetBehavior.STATE_SETTLING) {
                    if (slideOffset <= -0.28) {
                        bottomSheetDialog.dismiss();
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                CommentSingleActivity.this.slideOffset = slideOffset;

            }
        });
    }

    private void initListener() {
        // 点击事件
        bottomSheetAdapter.setOnItemChildClickListener((adapter, view1, position) -> {
            FirstLevelComment firstLevelComment = bottomSheetAdapter.getData().get(position);
            if (firstLevelComment == null) return;
            if (view1.getId() == R.id.ll_like) {
                //一级评论点赞 项目中还得通知服务器 成功才可以修改
                firstLevelComment.setLikeCount(firstLevelComment.getLikeCount() + (firstLevelComment.getIsLike() == 0 ? 1 : -1));
                firstLevelComment.setIsLike(firstLevelComment.getIsLike() == 0 ? 1 : 0);
                data.set(position, firstLevelComment);
                bottomSheetAdapter.notifyItemChanged(firstLevelComment.getPosition());
            }
        });
        //滚动事件
        if (mCommentRecyclerView != null) mCommentRecyclerView.initScrollListener(rv_dialog_lists);
    }

    private void initInputTextMsgDialog(View view, final boolean isReply, final String headImg, final int position) {
        dismissInputDialog();
        if (view != null) {
            offsetY = view.getTop();
            scrollLocation(offsetY);
        }
        if (inputTextMsgDialog == null) {
            inputTextMsgDialog = new InputTextMsgDialog(this, R.style.dialog_center);
            inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
                @Override
                public void onTextSend(String msg) {
                    addComment(isReply,headImg,position,msg);
                }

                @Override
                public void dismiss() {
                    scrollLocation(-offsetY);
                }
            });
        }
        showInputTextMsgDialog();
    }

    private void addComment(boolean isReply, String headImg, final int position, String msg) {
        //添加一级评论
        FirstLevelComment firstLevelComment = new FirstLevelComment();
        firstLevelComment.setUserName("赵丽颖");
        firstLevelComment.setId(bottomSheetAdapter.getItemCount() + 1 + "");
        firstLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
        firstLevelComment.setCreateTime(System.currentTimeMillis());
        firstLevelComment.setContent(msg);
        firstLevelComment.setLikeCount(0);
        data.add(0, firstLevelComment);
        rv_dialog_lists.scrollToPosition(0);
    }

    private void dismissInputDialog() {
        if (inputTextMsgDialog != null) {
            if (inputTextMsgDialog.isShowing()) inputTextMsgDialog.dismiss();
            inputTextMsgDialog.cancel();
            inputTextMsgDialog = null;
        }
    }

    private void showInputTextMsgDialog() {
        inputTextMsgDialog.show();
    }

    private int getWindowHeight() {
        Resources res = getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    //在项目中是从服务器获取数据，其实就是一级评论分页获取
    @Override
    public void onLoadMoreRequested() {
        if (data.size() >= totalCount) {
            bottomSheetAdapter.loadMoreEnd(false);
            return;
        }
        //加载更多
        for (int i = 0; i < 10; i++) {
            FirstLevelComment firstLevelComment = new FirstLevelComment();
            firstLevelComment.setUserName("赵丽颖 add more" + i);
            firstLevelComment.setId(bottomSheetAdapter.getItemCount() + (i + 1) + "");
            firstLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            firstLevelComment.setCreateTime(System.currentTimeMillis());
            firstLevelComment.setContent("add more" + i);
            firstLevelComment.setLikeCount(0);
            data.add(firstLevelComment);
        }
        bottomSheetAdapter.loadMoreComplete();
    }

    // item滑动到原位
    public void scrollLocation(int offsetY) {
        try {
            rv_dialog_lists.smoothScrollBy(0, offsetY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCommentRecyclerView != null){
            mCommentRecyclerView.destroy();
            mCommentRecyclerView = null;
        }
        bottomSheetAdapter = null;
    }
}
