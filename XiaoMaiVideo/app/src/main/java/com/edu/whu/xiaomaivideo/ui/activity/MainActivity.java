/**
 * Author: 张俊杰、叶俊豪
 * Create Time: 2020/7/7
 * Update Time: 2020/7/10
 */

package com.edu.whu.xiaomaivideo.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.edu.whu.xiaomaivideo.R;
import com.edu.whu.xiaomaivideo.model.User;
import com.edu.whu.xiaomaivideo.restcallback.UserRestCallback;
import com.edu.whu.xiaomaivideo.restservice.UserRestService;
import com.edu.whu.xiaomaivideo.service.JWebSocketService;
import com.edu.whu.xiaomaivideo.ui.dialog.SimpleBottomDialog;
import com.edu.whu.xiaomaivideo.ui.fragment.BlankFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.MeFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.MessageFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.HomeFragment;
import com.edu.whu.xiaomaivideo.ui.fragment.FindFragment;
import com.edu.whu.xiaomaivideo.util.CommonUtil;
import com.edu.whu.xiaomaivideo.util.Constant;
import com.edu.whu.xiaomaivideo.util.HttpUtil;
import com.edu.whu.xiaomaivideo.widget.MyViewPager;
import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends FragmentActivity {

    private MyViewPager mViewPager;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private Long exitTime = 0L;
    Intent webSocketService;
    SetWebSocketMessageReceiver setWebSocketMessageReceiver;
    FindFragment findFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册广播
        setWebSocketMessageReceiver = new SetWebSocketMessageReceiver();
        IntentFilter filter = new IntentFilter(Constant.SET_WEBSOCKET);
        registerReceiver(setWebSocketMessageReceiver, filter);

        initView();
        checkPermission();
        tryLogin();
    }

    private void stopWebSocketService() {
        // 如果以前登录了，先关闭以前的WebSocket
        if (webSocketService != null) {
            stopService(webSocketService);
            webSocketService = null;
        }
    }

    private void startWebSocketService() {
        // 再开启新的WebSocket
        if (webSocketService == null) {
            webSocketService = new Intent(MainActivity.this, JWebSocketService.class);
            startService(webSocketService);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(setWebSocketMessageReceiver);
    }

    private void initView() {
        findFragment = new FindFragment();
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tab_layout);
        mFragments = new ArrayList<>(5);
        mFragments.add(new HomeFragment()); // 第一个tab
        mFragments.add(new MessageFragment()); // 第二个tab
        mFragments.add(new BlankFragment()); // 没用，占个位置
        mFragments.add(findFragment); // 第三个tab
        mFragments.add(new MeFragment()); // 第四个tab
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    // 表示点击了中间的那个按钮，在这里处理中间按钮的点击触发事件
                    onCenterButtonPressed();
                } else {
                    // 切换到对应的tab
                    mViewPager.setCurrentItem(tab.getPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    // 表示点击了中间的那个按钮，在这里处理中间按钮的点击触发事件
                    onCenterButtonPressed();
                }
            }
        });
    }

    private void onCenterButtonPressed() {
        if (Constant.currentUser.getUserId() == 0) {
            // 没登录，不允许操作
            BasePopupView popupView = new XPopup.Builder(this)
                    .asCustom(new SimpleBottomDialog(this, R.drawable.success, "没有登录，不能发视频哦"))
                    .show();
            popupView.delayDismiss(1500);
            return;
        }
        Intent intent = new Intent(this, TakeVideoActivity.class);
        startActivityForResult(intent, Constant.TAKE_VIDEO);
    }

    private void checkPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .requestEach(
                        android.Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(permission -> {
                    if (!permission.granted) {
                        if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                            Constant.isLocationPermissionGiven = false;
                        } else if (permission.name.equals(Manifest.permission.CAMERA)) {
                            Constant.isCameraPermissionGiven = false;
                        } else if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            Constant.isStoragePermissionGiven = false;
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (findFragment.onKeyDown()) {
            return;
        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(
                    getApplication(),
                    "再按一次退出",
                    Toast.LENGTH_SHORT
            ).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.TAKE_VIDEO) {
            if (resultCode == RESULT_OK) {
                // 可能需要对刚刚发的视频做一定的操作
                BasePopupView popupView = new XPopup.Builder(this)
                        .asCustom(new SimpleBottomDialog(this, R.drawable.success, "发布成功"))
                        .show();
                popupView.delayDismiss(1500);
            }
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //super.destroyItem(container, position, object);
        }
    }

    private void tryLogin() {
        SharedPreferences sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        if (username.equals("") || password.equals("")) {
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        UserRestService.verifyUser(user, new UserRestCallback() {
            @Override
            public void onSuccess(int resultCode, User user) {
                if (resultCode == Constant.RESULT_SUCCESS) {
                    Constant.currentUser = user;
                    startWebSocketService();
                    getAddress();
                } else if (resultCode == Constant.USER_NOT_EXISTS) {
                    // 用户不存在
                } else {
                    // 密码错误
                }
            }
        });
    }

    private void getAddress() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Geocoder geocoder = new Geocoder(getApplicationContext());
                Log.e("纬度是:", location.getLatitude() + "");
                Log.e("经度是:", location.getLongitude() + "");
                Log.e("海拔是:", location.getAltitude() + "");
                Log.e("速度", location.getSpeed() + "");
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                Uri.Builder builder = Uri.parse(Constant.BASEURL+"location").buildUpon();
                String locationString = longitude+","+latitude;
                builder.appendQueryParameter("location", locationString);
                final LocationListener listener = this;
                HttpUtil.sendGetRequest(builder.toString(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        JSONObject jsonObject = JSON.parseObject(responseData);
                        String address = jsonObject.getString("data");
                        List<Map<String, String>> table = CommonUtil.addressResolution(address);
                        String city = table.get(0).get("province")+table.get(0).get("city");
                        Constant.currentLocation.postValue(city);
                        Log.e("MainActivity", Constant.currentLocation+"_");
                        manager.removeUpdates(listener);
                    }
                });

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        // 第一个参数 位置提供器 第二个参数 位置变化的事件间隔 第三个参数 位置变化的距离间隔 事件监听LocationListener
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    private class SetWebSocketMessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra("status");
            if (result.equals("stop")) {
                stopWebSocketService();
            }
            else if (result.equals("start")) {
                startWebSocketService();
            }
        }
    }
}