package com.qiao.videoui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiao.videoui.R;
import com.qiao.videoui.bean.CommentListBean;
import com.qiao.videoui.bean.VideoBean;

import java.util.List;

public class CommentListAdapter extends BaseQuickAdapter<CommentListBean.ListBean, BaseViewHolder> {

    public CommentListAdapter() {
        super(R.layout.adapter_comment_list);
    }

    @Override
    protected void convert(final BaseViewHolder viewHolder, final CommentListBean.ListBean item) {
        TextView comment_name_tx = viewHolder.getView(R.id.comment_name_tx);
        comment_name_tx.setText(item.getNickname());

        TextView comment_conntex_tx = viewHolder.getView(R.id.comment_conntex_tx);
        comment_conntex_tx.setText(item.getContent());

        TextView comment_time_tx = viewHolder.getView(R.id.comment_time_tx);
        comment_time_tx.setText(item.getAdd_time()+"");

        TextView comment_tags_tx = viewHolder.getView(R.id.comment_tags_tx);
        comment_tags_tx.setText(item.getAvatar());


    }
}