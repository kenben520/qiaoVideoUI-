package com.qiao.videoui.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.zhouwei.library.CustomPopWindow;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qiao.videoui.R;
import com.qiao.videoui.bean.BaseModel;
import com.qiao.videoui.bean.JsonModel;
import com.qiao.videoui.tools.Constant;
import com.qiao.videoui.tools.NewsTestCallback;
import com.qiao.videoui.tools.utilCode.ScreenUtils;
import com.qiao.videoui.tools.utilCode.ToastUtils;

public class BasePopupWindow extends PopupWindow implements View.OnClickListener{

    TextView shareCloseBtn;
    private Context mContext;
    CustomPopWindow popWindow;

    public BasePopupWindow(Context context, View.OnClickListener listener,View view) {
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.window_share_layout, null);
        //创建并显示popWindow
        int w = ScreenUtils.getScreenWidth();
        popWindow = new CustomPopWindow.PopupWindowBuilder(context)
                .setView(contentView).size(w, ScreenUtils.getScreenHeight())
                .create().showAsDropDown(view);
        contentView.findViewById(R.id.share_collect_btn).setOnClickListener(this);
        contentView.findViewById(R.id.share_close_btn).setOnClickListener(this);
        contentView.findViewById(R.id.share_top_close).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_collect_btn:
                loadCollectionData();
                break;
            case R.id.share_close_btn:
                popWindow.dissmiss();
                break;
        }
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

}