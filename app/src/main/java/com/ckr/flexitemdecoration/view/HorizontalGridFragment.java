package com.ckr.flexitemdecoration.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ckr.decoration.BaseItemDecoration;
import com.ckr.decoration.DividerGridItemDecoration;
import com.ckr.flexitemdecoration.R;
import com.ckr.flexitemdecoration.adapter.HorizontalGridAdapter;

import java.util.Arrays;

import butterknife.BindDimen;
import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalGridFragment extends BaseFragment {
    private static final String TAG = "HorizontalGridFragment";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindDimen(R.dimen.size10)
    int padding;
    private HorizontalGridAdapter mainAdapter;
    public static final int SPAN_COUNT = 2;
    public static final int ORIENTATION = LinearLayoutManager.HORIZONTAL;
    private BaseItemDecoration itemDecoration;
    public boolean[] is_checked = {true, false, false, false, false, false, false, false, false, false, false};
    private boolean isInit = false;

    public static HorizontalGridFragment newInstance() {
        Bundle args = new Bundle();
        HorizontalGridFragment fragment = new HorizontalGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_horizontal_grid;
    }

    @Override
    protected void init() {
        isInit = true;
//        int dimension = (int) getResources().getDimension(R.dimen.size12);
        setItemDecoration();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT, ORIENTATION, false));
        recyclerView.setPadding(padding, padding, padding, padding);
        mainAdapter = new HorizontalGridAdapter(getContext());
        recyclerView.setAdapter(mainAdapter);
    }

    private void setItemDecoration() {
        if (!isInit) {
            return;
        }
        if (itemDecoration != null) {
            recyclerView.removeItemDecoration(itemDecoration);
        }
        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(),ORIENTATION, SPAN_COUNT);
//        DividerGridItemDecoration.Builder builder = new DividerGridItemDecoration.Builder(getContext(), SPAN_COUNT);
        builder.setDivider(R.drawable.bg_divider_list);
        if (is_checked[0]) {
        } else {
            builder.removeHeaderDivider(is_checked[1])
                    .removeFooterDivider(is_checked[2])
                    .removeLeftDivider(is_checked[3])
                    .removeRightDivider(is_checked[4])
            ;
        }
        if (is_checked[5]) {
            builder.subDivider(3, 7)
                    .setSubDividerHeight(24)
                    .setSubDividerWidth(24)
                    .setSubDividerDrawable(R.drawable.bg_divider_offset_grid);
        }
        if (is_checked[6]) {
            builder.redrawDivider(1)
                    .redrawDividerWidth(30)
                    .redrawDividerDrawable(R.drawable.bg_divider_redraw_grid);
        }
        if (is_checked[7]) {
            builder.redrawHeaderDivider()
                    .redrawHeaderDividerHeight(40)
                    .redrawHeaderDividerDrawable(R.drawable.bg_divider_offset_grid);
        }
        if (is_checked[8]) {
            builder.redrawFooterDivider()
                    .redrawFooterDividerHeight(40)
                    .redrawFooterDividerDrawable(R.drawable.bg_divider_offset_grid);
        }
        if (is_checked[9]) {
            builder.redrawLeftDivider().
                    redrawLeftDividerWidth(40)
                    .redrawLeftDividerDrawable(R.drawable.bg_divider_offset_grid);
        }
        if (is_checked[10]) {
            builder.redrawRightDivider()
                    .redrawRightDividerWidth(40)
                    .redrawRightDividerDrawable(R.drawable.bg_divider_offset_grid);
        }
        itemDecoration = builder.build();
        recyclerView.addItemDecoration(itemDecoration);
       /* itemDecoration = new DividerGridItemDecoration(getContext(), BaseItemDecoration.HORIZONTAL,SPAN_COUNT);
        itemDecoration.setDivider(R.drawable.bg_divider_list);
        if (is_checked[0]) {
        } else {
            itemDecoration.removeHeaderDivider(is_checked[1])
                    .removeFooterDivider(is_checked[2])
                    .removeLeftDivider(is_checked[3])
                    .removeRightDivider(is_checked[4])
            ;
        }
        recyclerView.addItemDecoration(itemDecoration);*/
    }

    @Override
    public void refreshFragment(boolean... params) {
        Log.d(TAG, "refreshFragment: params:" + Arrays.toString(params));
        if (!Arrays.equals(params, is_checked)) {
            System.arraycopy(params, 0, is_checked, 0, params.length);
            Log.d(TAG, "refreshFragment: is_checked:" + Arrays.toString(is_checked));
            setItemDecoration();
        }
    }
}
