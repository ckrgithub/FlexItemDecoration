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

public class HorizontalGridAdapter extends RecyclerView.Adapter<HorizontalGridAdapter.MainHolder> {
    private Context mContext;

    public HorizontalGridAdapter(Context context) {
        mContext = context;
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(LayoutInflater.from(mContext).inflate(R.layout.item_picture_horizontal, parent, false));
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 13;
    }

    class MainHolder extends RecyclerView.ViewHolder {

        public MainHolder(View itemView) {
            super(itemView);
        }
    }

}
