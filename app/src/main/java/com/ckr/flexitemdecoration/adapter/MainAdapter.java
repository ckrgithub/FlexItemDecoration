package com.ckr.flexitemdecoration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ckr.flexitemdecoration.R;

/**
 * Created by PC大佬 on 2018/1/4.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHorld> {
    private Context mContext;

    public MainAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MainHorld onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHorld(LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(MainHorld holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 13;
    }

    class MainHorld extends RecyclerView.ViewHolder {

        public MainHorld(View itemView) {
            super(itemView);
        }
    }

}
