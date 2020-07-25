/**
 * Author: 张俊杰、叶俊豪、何慷
 * Create Time: 2020/7/10
 * Update Time: 2020/7/25
 */

package com.edu.whu.xiaomaivideo.ui.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.adapter.FindSubFragmentAdapter;
import com.edu.whu.xiaomaivideo.databinding.FindFragmentBinding;
import com.edu.whu.xiaomaivideo.model.Movie;
import com.edu.whu.xiaomaivideo.restcallback.MovieRestCallback;
import com.edu.whu.xiaomaivideo.restservice.MovieRestService;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.ui.activity.MovieTypeActivity;
import com.edu.whu.xiaomaivideo.ui.activity.SearchResultActivity;
import com.edu.whu.xiaomaivideo.viewModel.FindViewModel;
import com.google.android.material.appbar.AppBarLayout;
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch;

import java.util.List;
import java.util.Objects;

public class FindFragment extends Fragment {

    private FindViewModel findViewModel;
    private FindFragmentBinding findFragmentBinding;
    private FindSubFragmentAdapter findSubFragmentAdapter;

    //是否隐藏了头部
    private boolean isHideHeaderLayout = false;

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        findViewModel= new ViewModelProvider(Objects.requireNonNull(getActivity())).get(FindViewModel.class);
        findFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.find_fragment,container,false);
        findFragmentBinding.setViewmodel(findViewModel);
        findFragmentBinding.setLifecycleOwner(getActivity());
        init();
        initTypeButtonClickListener();
        initSearch();
        return findFragmentBinding.getRoot();
    }

    //初始化方法
    private void init() {
        findSubFragmentAdapter = new FindSubFragmentAdapter(getActivity().getSupportFragmentManager(), getContext());
        findFragmentBinding.viewpager.setAdapter(findSubFragmentAdapter);
        findFragmentBinding.tabs.setViewPager(findFragmentBinding.viewpager);

        findFragmentBinding.viewpager.setOffscreenPageLimit(findSubFragmentAdapter.getCount());
        initAppBarLayout();
    }

    private void initTypeButtonClickListener() {
        findFragmentBinding.ivChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "儿童");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "文化");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "时尚");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "美食");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "游戏");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "生活");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "影视");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "音乐");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "体育");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivSociety.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "社会");
                startActivity(intent);
            }
        });
        findFragmentBinding.ivTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MovieTypeActivity.class);
                intent.putExtra("type", "科技");
                startActivity(intent);
            }
        });
    }

    // 初始化AppBarLayout
    private void initAppBarLayout() {
        LayoutTransition mTransition = new LayoutTransition();
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 1.f).
                setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);

        //header layout height
        final int headerHeight = getResources().getDimensionPixelOffset(R.dimen.header_height);
        findFragmentBinding.appbar.setLayoutTransition(mTransition);

        findFragmentBinding.appbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset >= headerHeight) {
                    isHideHeaderLayout = true;

                    //当偏移量超过顶部layout的高度时，认为已经完全移动出屏幕了
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) findFragmentBinding.llHeaderLayout.getLayoutParams();
                            mParams.setScrollFlags(0);
                            findFragmentBinding.llHeaderLayout.setLayoutParams(mParams);
                            findFragmentBinding.llHeaderLayout.setVisibility(View.GONE);
                        }
                    }, 100);
                }
            }
        });
    }

    private void initSearch() {
        findFragmentBinding.searchType.setCheckedPosition(0);
        findFragmentBinding.svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // 不用管这个报错，我也不知道为什么这里报错
                int type = findFragmentBinding.searchType.getCheckedPosition();
                if (type == 0) {
                    Intent intent=new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("keyword", s);
                    intent.putExtra("type", "movie");
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("keyword", s);
                    intent.putExtra("type", "user");
                    startActivity(intent);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public boolean onKeyDown() {
        if (isHideHeaderLayout) {
            isHideHeaderLayout = false;
            ((FindSubFragment) findSubFragmentAdapter.getFragments().get(0)).mRecyclerView.scrollToPosition(0);
            findFragmentBinding.llHeaderLayout.setVisibility(View.VISIBLE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) findFragmentBinding.llHeaderLayout.getLayoutParams();
                    mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                            AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    findFragmentBinding.llHeaderLayout.setLayoutParams(mParams);
                }
            }, 300);
            return true;
        } else {
            return false;
        }
    }
}
