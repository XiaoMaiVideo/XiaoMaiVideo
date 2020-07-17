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
import com.edu.whu.xiaomaivideo.model.SecondLevelComment;
import com.edu.whu.xiaomaivideo.ui.dialog.InputTextMsgDialog;
import com.edu.whu.xiaomaivideo.util.RecyclerViewUtil;
import com.edu.whu.xiaomaivideo.widget.VerticalCommentLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;


public class CommentSingleActivity extends AppCompatActivity implements VerticalCommentLayout.CommentItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

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
    private RecyclerViewUtil mRecyclerViewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_multi);
        mRecyclerViewUtil = new RecyclerViewUtil();
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

            List<SecondLevelComment> beanList = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                SecondLevelComment secondLevelComment = new SecondLevelComment();
                secondLevelComment.setContent("一级第" + (i + 1) + "人 二级第" + (j + 1) + "人评论内容" + (j % 3 == 0 ? content + (j + 1) + "次" : ""));
                secondLevelComment.setCreateTime(System.currentTimeMillis());
                secondLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
                secondLevelComment.setId(j + "");
                firstLevelComment.setUserId("ChildUserId" + i);
                secondLevelComment.setIsLike(0);
                secondLevelComment.setLikeCount(j);
                secondLevelComment.setUserName("星梦缘" + (i + 1) + "  " + (j + 1));
                secondLevelComment.setIsReply(j % 5 == 0 ? 1 : 0);
                secondLevelComment.setReplyUserName(j % 5 == 0 ? "闭嘴家族" + j : "");
                secondLevelComment.setPosition(i);
                secondLevelComment.setChildPosition(j);
                beanList.add(secondLevelComment);
            }
            firstLevelComment.setSecondLevelComments(beanList);
            data.add(firstLevelComment);
        }
    }

    /**
     * 重新排列数据
     * 未解决滑动卡顿问题
     */
    private void sort() {
        int size = data.size();
        for (int i = 0; i < size; i++) {
            FirstLevelComment firstLevelBean = data.get(i);
            firstLevelBean.setPosition(i);

            List<SecondLevelComment> secondLevelComments = firstLevelBean.getSecondLevelComments();
            if (secondLevelComments == null || secondLevelComments.isEmpty()) continue;
            int count = secondLevelComments.size();
            for (int j = 0; j < count; j++) {
                SecondLevelComment secondLevelComment = secondLevelComments.get(j);
                secondLevelComment.setPosition(i);
                secondLevelComment.setChildPosition(j);
            }
        }

        bottomSheetAdapter.notifyDataSetChanged();

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

        bottomSheetAdapter = new CommentDialogSingleAdapter(this);
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
            } else if (view1.getId() == R.id.rl_group) {
                //添加二级评论
                CommentSingleActivity.this.initInputTextMsgDialog((View) view1.getParent(), false, firstLevelComment.getHeadImg(), position);
            }
        });
        //滚动事件
        if (mRecyclerViewUtil != null) mRecyclerViewUtil.initScrollListener(rv_dialog_lists);
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
        if (position >= 0) {
            //添加二级评论
            SecondLevelComment secondLevelComment = new SecondLevelComment();
            FirstLevelComment firstLevelComment = bottomSheetAdapter.getData().get(position);
            secondLevelComment.setReplyUserName("replyUserName");
            secondLevelComment.setIsReply(isReply ? 1 : 0);
            secondLevelComment.setContent(msg);
            secondLevelComment.setHeadImg(headImg);
            secondLevelComment.setCreateTime(System.currentTimeMillis());
            secondLevelComment.setIsLike(0);
            secondLevelComment.setUserName("userName");
            secondLevelComment.setId(firstLevelComment.getSecondLevelComments()+ "");
            firstLevelComment.getSecondLevelComments().add(secondLevelComment);

            data.set(firstLevelComment.getPosition(), firstLevelComment);
            bottomSheetAdapter.notifyDataSetChanged();
            rv_dialog_lists.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((LinearLayoutManager) rv_dialog_lists.getLayoutManager())
                            .scrollToPositionWithOffset(position == data.size() - 1 ? position
                                    : position + 1, position == data.size() - 1 ? Integer.MIN_VALUE : rv_dialog_lists.getHeight() / 2);
                }
            }, 100);

        } else {
            //添加一级评论
            FirstLevelComment firstLevelComment = new FirstLevelComment();
            firstLevelComment.setUserName("赵丽颖");
            firstLevelComment.setId(bottomSheetAdapter.getItemCount() + 1 + "");
            firstLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            firstLevelComment.setCreateTime(System.currentTimeMillis());
            firstLevelComment.setContent(msg);
            firstLevelComment.setLikeCount(0);
            firstLevelComment.setSecondLevelComments(new ArrayList<SecondLevelComment>());
            data.add(0, firstLevelComment);
            sort();
            rv_dialog_lists.scrollToPosition(0);
        }
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

    //在项目中是从服务器获取数据，其实就是二级评论分页获取
    @Override
    public void onMoreClick(View layout, int position) {
        FirstLevelComment firstLevelComment = data.get(position);
        List<SecondLevelComment> beans = firstLevelComment.getSecondLevelComments();
        int size = beans.size();
        for (int i = size; i < size + 2; i++) {
            SecondLevelComment secondLevelComment = new SecondLevelComment();
            secondLevelComment.setContent("一级第" + (position + 1) + "人 二级第" + (i + 1) + "人评论内容" + (i % 3 == 0 ? content + (i + 1) + "次" : ""));
            secondLevelComment.setCreateTime(System.currentTimeMillis());
            secondLevelComment.setHeadImg("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1918451189,3095768332&fm=26&gp=0.jpg");
            secondLevelComment.setId(i + "");
            secondLevelComment.setIsLike(i % 2 == 0 ? 1 : 0);
            secondLevelComment.setLikeCount(i);
            secondLevelComment.setUserName("星梦缘" + (i + 1) + "  " + (i + 1));
            secondLevelComment.setIsReply(i % 5 == 0 ? 1 : 0);
            secondLevelComment.setReplyUserName(i % 5 == 0 ? "闭嘴家族" + (i + 1) : "");
            beans.add(secondLevelComment);
        }
        sort();
    }

    //添加二级评论（回复某人）
    @Override
    public void onItemClick(View layout, SecondLevelComment comment, int position) {
        initInputTextMsgDialog(layout, true, comment.getHeadImg(), position);
    }

    //二级评论点赞 本地数据更新喜欢状态
    // 在项目中是还需要通知服务器成功才可以修改本地数据
    @Override
    public void onLikeClick(View layout, SecondLevelComment secondLevelComment, int position) {
        secondLevelComment.setLikeCount(secondLevelComment.getLikeCount() + (secondLevelComment.getIsLike() == 1 ? -1 : 1));
        secondLevelComment.setIsLike(secondLevelComment.getIsLike() == 1 ? 0 : 1);
        data.get(secondLevelComment.getPosition()).getSecondLevelComments().set(secondLevelComment.getChildPosition(), secondLevelComment);
        bottomSheetAdapter.notifyItemChanged(secondLevelComment.getPosition());
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
            firstLevelComment.setSecondLevelComments(new ArrayList<SecondLevelComment>());
            data.add(firstLevelComment);
        }
        sort();
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
        if (mRecyclerViewUtil != null){
            mRecyclerViewUtil.destroy();
            mRecyclerViewUtil = null;
        }
        bottomSheetAdapter = null;
    }
}
