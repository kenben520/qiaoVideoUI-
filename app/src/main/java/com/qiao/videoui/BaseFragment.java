package com.qiao.videoui;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;


/**
 * Created by zhangwei on 2018/4/18.
 */

public class BaseFragment extends Fragment {

    public Activity mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

}
