package com.qiao.videoui.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qiao.videoui.BaseFragment;
import com.qiao.videoui.R;
import com.qiao.videoui.adapter.CommentListAdapter;
import com.qiao.videoui.adapter.VideoAdapter;
import com.qiao.videoui.bean.BaseModel;
import com.qiao.videoui.bean.CommentListBean;
import com.qiao.videoui.bean.JsonModel;
import com.qiao.videoui.bean.VideoBean;
import com.qiao.videoui.tools.Constant;
import com.qiao.videoui.tools.NewsTestCallback;
import com.qiao.videoui.tools.utilCode.ScreenUtils;
import com.qiao.videoui.tools.utilCode.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.home_share_btn)
    TextView homeShareBtn;
    @BindView(R.id.home_tags_btn)
    TextView homeTagsBtn;
    @BindView(R.id.home_comment_btn)
    TextView homeCommentBtn;
    @BindView(R.id.home_follow_btn)
    ImageView homeFollowBtn;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private List<CommentListBean.ListBean> commentListBean;
    private CommentListBean commentBean;
    private List<VideoBean> videoBeanList;
    private VideoAdapter videoAdapter;
    public CommentListAdapter commentListAdapter;

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
                        loadCommentListData();
                        loadCollectionData();
                        loadInterestData();
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
        loadData();
        loadCommentListData();
    }

    /**
     * 评论列表
     */
    private void loadCommentListData() {
        String urlr = Constant.url + "comment_list";
        OkGo.<BaseModel<CommentListBean>>post(urlr)
                .params("file_id", "")
                .params("index", 1)
                .params("count", 5)
                .execute(new NewsTestCallback<BaseModel<CommentListBean>>() {
                    @Override
                    public void onSuccess(Response<BaseModel<CommentListBean>> response) {
                        commentBean = response.body().data;
                        if (commentListBean!=null){
                            commentListBean.addAll(commentBean.getList());
                            commentListAdapter.setNewData(commentListBean);
                        }
                    }

                    @Override
                    public void onError(Response<BaseModel<CommentListBean>> response) {

                    }
                });
    }

    /**
     * 收藏
     */
    private void loadCollectionData() {
        String urlr = Constant.url + "collection";
        OkGo.<BaseModel<JsonModel>>post(urlr)
                .params("user_id", "")
                .params("file_id", "")
                .execute(new NewsTestCallback<BaseModel<JsonModel>>() {
                    @Override
                    public void onSuccess(Response<BaseModel<JsonModel>> response) {
                        ToastUtils.showLong(" 收藏成功");
                    }

                    @Override
                    public void onError(Response<BaseModel<JsonModel>> response) {
                        ToastUtils.showLong(" 收藏失败");
                    }
                });
    }

    private void loadInterestData() {
        //关注
        String urlr = Constant.url + "interest";
        OkGo.<BaseModel<JsonModel>>post(urlr)
                .params("userid", "")
                .params("to_userid", "")
                .execute(new NewsTestCallback<BaseModel<JsonModel>>() {
                    @Override
                    public void onSuccess(Response<BaseModel<JsonModel>> response) {
                        //ToastUtils.showLong("关注成功");
                        // CommentListBean results = response.body().data;
                        // newsAdapter.loadComplete();
                    }

                    @Override
                    public void onError(Response<BaseModel<JsonModel>> response) {
                        //ToastUtils.showLong("关注失败");
//                        //显示数据加载失败,点击重试
//                        newsAdapter.showLoadMoreFailedView();
//                        //网络请求失败的回调,一般会弹个Toast
//                        showToast(response.getException().getMessage());
                    }
                });
    }

    private void loadData() {
        String urlr = Constant.url + "commend";
        OkGo.<BaseModel<JsonModel>>post(urlr)
                .params("userid", "")
                .params("file_id", "")
                .execute(new NewsTestCallback<BaseModel<JsonModel>>() {
                    @Override
                    public void onSuccess(Response<BaseModel<JsonModel>> response) {
                        // ToastUtils.showLong("点赞成功");
                        // CommentListBean results = response.body().data;
                        // newsAdapter.loadComplete();
                    }

                    @Override
                    public void onError(Response<BaseModel<JsonModel>> response) {
                        // ToastUtils.showLong("点赞失败");
//                        //显示数据加载失败,点击重试
//                        newsAdapter.showLoadMoreFailedView();
//                        //网络请求失败的回调,一般会弹个Toast
//                        showToast(response.getException().getMessage());
                    }
                });
    }

    public void shareUI() {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_share_layout, null);
        //创建并显示popWindow
        int w = ScreenUtils.getScreenWidth();
        CustomPopWindow filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView).size(w, ScreenUtils.getScreenHeight())
                .create().showAsDropDown(refreshLayout);
    }

    private SmartRefreshLayout commentRefreshLayout;
    public void commentWindowUI() {
        commentListAdapter = new CommentListAdapter();
        commentListBean = new ArrayList<>();
        commentListAdapter.addData(commentListBean);
        if(commentBean!=null){
            commentListAdapter.setNewData(commentListBean);
        }
        //commentBean.getList();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.window_comment_layout, null);
        //创建并显示popWindow
        int w = ScreenUtils.getScreenWidth();
        CustomPopWindow filterPopWindow = new CustomPopWindow.PopupWindowBuilder(getContext())
                .setView(contentView).size(w, ScreenUtils.getScreenHeight())
                .create().showAsDropDown(refreshLayout);
        commentRefreshLayout =  contentView.findViewById(R.id.comment_refreshLayout);
        RecyclerView comment_recyclerView = contentView.findViewById(R.id.comment_recyclerView);
        comment_recyclerView.setAdapter(commentListAdapter);
        comment_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        commentRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                commentRefreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        commentListBean.clear();
                        commentListAdapter.setNewData(commentListBean);
                        loadCommentListData();
                        commentRefreshLayout.finishRefresh();
                        commentRefreshLayout.setNoMoreData(false);
                    }
                });
            }
        });

        commentRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                commentRefreshLayout.getLayout().post(new Runnable() {
                    @Override
                    public void run() {
                        loadCommentListData();
                        commentRefreshLayout.finishRefresh();
                        commentRefreshLayout.finishLoadMore();
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        null.unbind();
    }

    @OnClick({R.id.home_share_btn, R.id.home_tags_btn, R.id.home_comment_btn,R.id.home_follow_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_share_btn:
                shareUI();
                break;
            case R.id.home_tags_btn:
                Drawable drawable = ContextCompat.getDrawable(mContext, R.mipmap.home_un_like_icon);
                drawable.setBounds( 0, 0, drawable.getMinimumWidth(),drawable.getMinimumHeight());
                homeTagsBtn.setCompoundDrawables(null, drawable, null, null);
               // homeTagsBtn.setBackground(ContextCompat.getDrawable(mContext, R.mipmap.home_un_like_icon));
                break;
            case R.id.home_comment_btn:
                commentWindowUI();
                loadCommentListData();
                break;
            case R.id.home_follow_btn:
                homeFollowBtn.setBackground(ContextCompat.getDrawable(mContext,R.mipmap.home_followed_icon));
                break;
        }
    }

}
