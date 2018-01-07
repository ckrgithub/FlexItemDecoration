package com.ckr.flexitemdecoration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC大佬 on 2017/8/5.
 */

public abstract class BaseAdpater<T> extends RecyclerView.Adapter<BaseViewHorld> {
    private static final String TAG = "BaseAdpater";
    private Context mContext;
    protected List<T> data;

    public BaseAdpater(Context context) {
        mContext = context;
        data = new ArrayList<>();
    }

    public BaseAdpater(Context context, List<T> data) {
        mContext = context;
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void updateAll(List newData) {
        if (newData == null || newData.size() == 0) {
            Log.d(TAG, "updateAll: newData == null || newData.size() == 0");
            return;
        }
        clearState();
        data.clear();
        data.addAll(newData);
        notifyDataSetChanged();
    }

    public void updateAll(int start, List newData) {
        if (newData == null || newData.size() == 0) {
            Log.d(TAG, "updateAll: newData == null || newData.size() == 0");
            return;
        }
        if (start < 0 && start > data.size()) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        clearState();
        T t = data.get(0);
        data.clear();
        data.add(t);
        data.addAll(newData);
        notifyItemRangeChanged(start, newData.size());
    }

    protected void clearState() {

    }

    public void updateItem(int start, List<T> newData) {
        if (newData == null || newData.size() == 0) {
            Log.d(TAG, "updateAll: newData == null || newData.size() == 0");
            return;
        }
        if (start < 0 && start > data.size()) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        data.addAll(start, newData);
        notifyItemRangeChanged(start, newData.size());
    }


    public void updateItem(List<T> newData) {
        if (newData == null || newData.size() == 0) {
            Log.d(TAG, "updateAll: newData == null || newData.size() == 0");
            return;
        }
        int size = data.size();
        data.addAll(newData);
        notifyItemRangeChanged(size, newData.size());
    }

    public void updateItem(int start, T t) {
        if (t == null) {
            return;
        }
        if (start < 0 && start > data.size()) {
            throw new ArrayIndexOutOfBoundsException(start);
        }
        data.add(start, t);
        int len = data.size() - start;
        notifyItemRangeChanged(start, len);
    }

    public void updateItem(T t) {
        if (t == null) {
            return;
        }
        int len = data.size();
        data.add(t);
        notifyItemRangeChanged(len, 1);
    }

    public void removeItem(int position) {
        Log.d(TAG, "removeItem: pos:" + position);
        if (position < 0 && position >= data.size()) {
            throw new ArrayIndexOutOfBoundsException(position);
        }
        int size = data.size();
        data.remove(position);
        int len = size - position;
        notifyItemRangeChanged(position, len);
    }

    // TODO: 2017/10/23 防止Inconsistency detected 
    public void removeMineItem(int position) {
        Log.d(TAG, "removeItem: pos:" + position);
        if (position < 0 && position >= data.size()) {
            throw new ArrayIndexOutOfBoundsException(position);
        }
//        int size = data.size();
//        Log.d(TAG, "removeItem: size:"+size);
        data.remove(position);
        notifyDataSetChanged();
//        int len = size - position;
//        if (position >= size - 2) {
//            Log.d(TAG, "removeItem: notifyDataSetChanged");
//            notifyDataSetChanged();
//        } else {
//            Log.d(TAG, "removeItem: notifyItemRangeChanged");
//            notifyItemRangeChanged(position, len);
//        }
    }

    protected Context getContext() {
        return mContext;
    }

    @Override
    public BaseViewHorld onCreateViewHolder(ViewGroup parent, int viewType) {
        return getViewHorld(LayoutInflater.from(mContext).inflate(getLayoutId(viewType), parent, false), viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHorld holder, int position) {
        convert(holder, position, data.size()==0?null:data.get(position));
    }

    @Override
    public int getItemCount() {
        return 13;
    }

    protected abstract int getLayoutId(int viewType);

    protected abstract BaseViewHorld getViewHorld(View itemView, int viewType);

    protected abstract void convert(BaseViewHorld horld, int position, T t);

}
