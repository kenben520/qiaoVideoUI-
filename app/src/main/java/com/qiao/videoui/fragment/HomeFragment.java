package com.qiao.videoui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.qiao.videoui.BaseFragment;
import com.qiao.videoui.R;
import com.qiao.videoui.adapter.VideoAdapter;
import com.qiao.videoui.bean.BaseModel;
import com.qiao.videoui.bean.JsonModel;
import com.qiao.videoui.bean.VideoBean;
import com.qiao.videoui.tools.NewsTestCallback;
import com.qiao.videoui.tools.utilCode.ScreenUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private List<VideoBean> videoBeanList;
    private VideoAdapter videoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoBeanList = new ArrayList<>();
        videoAdapter = new VideoAdapter();
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.addData(videoBeanList);

//        //触发自动刷新
//        refreshLayout.autoRefresh();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(false);
                    }
                });
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                        VideoBean bean = new VideoBean();
                        bean.setTitle("title==" + videoBeanList.size());
                        videoBeanList.add(bean);
                        videoAdapter.setNewData(videoBeanList);
                        refreshLayout.finishRefresh();
                        refreshLayout.finishLoadMore();
                    }
                });
            }
        });
        //触发自动刷新
      //  refreshLayout.autoRefresh();
//        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
        videoAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
//        recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                shareUI();
//            }
//        },1000);
//        loadData();
    }

    private void loadData(){
        String urlr = "http://test.msd24.com:8080/index.php?app=notice&act=app_notice_list&api_token=5af35467a0f597015c4a5fcf159d2ccd&version=1.0&client_id=257af641-29e2-38d7-841f-74213e27e124";
        OkGo.<BaseModel<JsonModel>>get(urlr)//
                .cacheMode(CacheMode.NO_CACHE)       //上拉不需要缓存
                .execute(new NewsTestCallback<BaseModel<JsonModel>>() {
                    @Override
                    public void onSuccess(Response<BaseModel<JsonModel>> response) {
                        JsonModel results = response.body().data;
                        // newsAdapter.loadComplete();
                    }

                    @Override
                    public void onError(Response<BaseModel<JsonModel>> response) {
//                        //显示数据加载失败,点击重试
//                        newsAdapter.showLoadMoreFailedView();
//                        //网络请求失败的回调,一般会弹个Toast
//                        showToast(response.getException().getMessage());
                    }
                });
    }

    public void shareUI(){
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_share_layout, null);
        //创建并显示popWindow
        int w = ScreenUtils.getScreenWidth();
        CustomPopWindow  filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView)
                .create().showAsDropDown(refreshLayout);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
