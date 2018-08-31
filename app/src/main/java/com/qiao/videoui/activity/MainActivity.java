package com.qiao.videoui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qiao.videoui.BaseActivity;
import com.qiao.videoui.R;
import com.qiao.videoui.bean.VideoBean;
import com.qiao.videoui.callback.DialogCallback;
import com.qiao.videoui.fragment.CollectFragment;
import com.qiao.videoui.fragment.HomeFragment;
import com.qiao.videoui.fragment.MineFragment;
import com.qiao.videoui.tools.TabEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private CommonTabLayout mTabLayout_2;
    private String[] mTitles = {"首页", "收藏", "","发现", "我"};
    private int[] mIconSelectIds = {
            0, 0,
            R.mipmap.home_photo,0, 0};
    private int[] mIconUnselectIds = {
            0,  0,
            R.mipmap.home_photo, 0, 0};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Fragment> fragments = new ArrayList<>();
//        fragments.add(TradeUserInfoFragment.newInstance());
//        fragments.add(HomeFragment2.newInstance("首页"));
        fragments.add(HomeFragment.newInstance());
        fragments.add(CollectFragment.newInstance());
        fragments.add(CollectFragment.newInstance());
        fragments.add(CollectFragment.newInstance());
//        fragments.add(KMarketFragment.newInstance("行情"));
//        fragments.add(HomeFragment.newInstance());
        fragments.add(MineFragment.newInstance());
//        TradeUserInfoActivity.startMyActivity(this);
        vp = findViewById(R.id.vp);
        vp.setOffscreenPageLimit(5);
        VpAdapter adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTabLayout_2 = findViewById(R.id.homeTbaLayout);
        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            int topIndex;
            @Override
            public void onTabSelect(int position) {
                if (position==2){
                    mTabLayout_2.setCurrentTab(topIndex);
                   return;
                }
                topIndex = position;
                vp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp.setCurrentItem(0);
        //两位数
//        mTabLayout_2.showMsg(0, 55);
//        mTabLayout_2.setMsgMargin(0, -5, 5);
//        //三位数
//        mTabLayout_2.showMsg(1, 100);

        mTabLayout_2.setTextSelectColor(ContextCompat.getColor(this,R.color.mind));
        mTabLayout_2.setTextUnselectColor(ContextCompat.getColor(this,R.color.whiteColor));
        mTabLayout_2.setUnderlineHeight(0);

//        OkGo.<LzyResponse<VideoBean>>get("")//
////                .tag(this)//
////                .headers("header1", "headerValue1")//
////                .params("param1", "paramValue1")//
////                .execute(new DialogCallback<LzyResponse<VideoBean>>(this) {
////
////                    @Override
////                    public void onSuccess(Response<LzyResponse<VideoBean>> response) {
////                      //  handleResponse(response);
////                    }
////
////                    @Override
////                    public void onError(Response<LzyResponse<VideoBean>> response) {
////                      //  handleError(response);
////                    }
////                });

    }

    private class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }

}
