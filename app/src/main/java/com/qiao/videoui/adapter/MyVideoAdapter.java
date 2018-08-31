package com.qiao.videoui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiao.videoui.R;
import com.qiao.videoui.bean.VideoBean;

public class MyVideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

        public MyVideoAdapter() {
            super(R.layout.adapter_my_video);
        }

        @Override
        protected void convert(final BaseViewHolder viewHolder, final VideoBean item) {
            TextView video_title_view = viewHolder.getView(R.id.video_title_view);
            video_title_view.setText(item.getTitle());
        }
    }