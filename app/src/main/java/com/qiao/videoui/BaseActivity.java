package com.qiao.videoui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  StatusBarUtil.setTranslucentForImageViewInFragment(this, null);
    }

}
