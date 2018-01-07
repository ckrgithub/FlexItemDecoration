package com.ckr.flexitemdecoration.view;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ckr.flexitemdecoration.R;
import com.ckr.flexitemdecoration.adapter.MainAdapter;
import com.ckr.flexitemdecoration.widget.BaseItemDecoration;
import com.ckr.flexitemdecoration.widget.DividerLinearDecoration;

import java.util.Arrays;

import butterknife.BindDimen;
import butterknife.BindView;

import static com.ckr.flexitemdecoration.widget.BaseItemDecoration.HORIZONTAL;
import static com.ckr.flexitemdecoration.widget.BaseItemDecoration.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class TwoMainFragment extends BaseFragment {
    private static final String TAG = "TwoMainFragment";
    public static final String ORIENTATION = "orientation";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindDimen(R.dimen.size10)
    int padding;
    private MainAdapter mainAdapter;
    public int orientation = LinearLayoutManager.VERTICAL;
    public boolean[] is_checked = {true, false, false, false, false};
    private BaseItemDecoration itemDecoration;
    private boolean isInit = false;

    public static TwoMainFragment newInstance(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            orientation = VERTICAL;
        }
        Bundle args = new Bundle();
        args.putInt(ORIENTATION, orientation);
        TwoMainFragment fragment = new TwoMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle arguments = getArguments();
        if (arguments != null) {
            orientation = arguments.getInt(ORIENTATION, VERTICAL);
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        isInit = true;
        setItemDecoration();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), orientation, false));
        recyclerView.setPadding(padding, padding, padding, padding);
        mainAdapter = new MainAdapter(getContext());
        recyclerView.setAdapter(mainAdapter);
    }

    private void setItemDecoration() {
        if (!isInit) {
            return;
        }
        if (itemDecoration != null) {
            recyclerView.removeItemDecoration(itemDecoration);
        }
        DividerLinearDecoration.Builder builder = new DividerLinearDecoration.Builder(getContext(), orientation);
        builder.setDivider(R.drawable.bg_divider_list);
        if (is_checked[0]) {
        } else {
            builder.removeHeaderDivider(is_checked[1])
                    .removeFooterDivider(is_checked[2])
                    .removeLeftDivider(is_checked[3])
                    .removeRightDivider(is_checked[4])
            ;
        }
        itemDecoration = builder.build();
        recyclerView.addItemDecoration(itemDecoration);
    }


    @Override
    public void refreshFragment(boolean... params) {
        Log.d(TAG, "refreshFragment: params:" + Arrays.toString(params));
        boolean forceRefresh = false;
        if (orientation == LinearLayoutManager.VERTICAL) {
            for (int i = 0; i < 3; i++) {
                if (params[i] != is_checked[i]) {
                    forceRefresh = true;
                    break;
                }
            }
            if (forceRefresh) {
                System.arraycopy(params, 0, is_checked, 0, 3);
                Log.d(TAG, "refreshFragment: is_checked:" + Arrays.toString(is_checked));
                setItemDecoration();
            }
        } else {
            if (params[0] != is_checked[0]) {
                forceRefresh = true;
            } else {
                for (int i = 3; i < params.length; i++) {
                    if (params[i] != is_checked[i]) {
                        forceRefresh = true;
                        break;
                    }
                }
            }
            if (forceRefresh) {
                is_checked[0] = params[0];
                is_checked[3] = params[3];
                is_checked[4] = params[4];
                Log.d(TAG, "refreshFragment: is_checked:" + Arrays.toString(is_checked));
                setItemDecoration();
            }
        }
    }
}
