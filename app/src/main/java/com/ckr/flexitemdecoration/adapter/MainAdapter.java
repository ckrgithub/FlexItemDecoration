package com.ckr.flexitemdecoration.adapter;

import android.content.Context;
import android.view.View;

import com.ckr.flexitemdecoration.R;

/**
 * Created by PC大佬 on 2018/1/4.
 */

public class MainAdapter extends BaseAdpater<String>{
    public MainAdapter(Context context) {
        super(context);
    }


    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_picture;
    }

    @Override
    protected BaseViewHorld getViewHorld(View itemView, int viewType) {
        return new MainHorld(itemView);
    }

    @Override
    protected void convert(BaseViewHorld horld, int position, String s) {

    }

    class MainHorld extends BaseViewHorld{

        public MainHorld(View itemView) {
            super(itemView);
        }
    }

}
