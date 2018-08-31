package com.qiao.videoui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiao.videoui.R;
import com.qiao.videoui.bean.VideoBean;

public class VideoAdapter extends BaseQuickAdapter<VideoBean, BaseViewHolder> {

    public VideoAdapter() {
        super(R.layout.adapter_video);
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final VideoBean item) {
        TextView video_title_view = viewHolder.getView(R.id.video_title_view);
        video_title_view.setText(item.getTitle());
    }
}