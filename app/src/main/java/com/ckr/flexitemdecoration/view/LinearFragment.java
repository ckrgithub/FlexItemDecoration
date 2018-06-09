package com.ckr.flexitemdecoration.view;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ckr.decoration.DividerLinearItemDecoration;
import com.ckr.flexitemdecoration.R;
import com.ckr.flexitemdecoration.adapter.MainAdapter;
import com.ckr.flexitemdecoration.model.Header;
import com.ckr.flexitemdecoration.model.UserInfo;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

import butterknife.BindDimen;
import butterknife.BindView;

import static com.ckr.decoration.BaseItemDecoration.HORIZONTAL;
import static com.ckr.decoration.BaseItemDecoration.VERTICAL;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinearFragment extends BaseFragment {
	private static final String TAG = "LinearFragment";
	public static final String ORIENTATION = "orientation";
	@BindView(R.id.recyclerView)
	RecyclerView recyclerView;
	@BindDimen(R.dimen.size10)
	int padding;
	@BindDimen(R.dimen.size8)
	int padding8;
	@BindDimen(R.dimen.size5)
	int padding5;
	private MainAdapter mainAdapter;
	public int orientation = LinearLayoutManager.VERTICAL;
	public boolean[] is_checked = {true, false, false, false, false, false, false, false, false, false, false};
	private DividerLinearItemDecoration itemDecoration;
	private boolean isInit = false;

	public static LinearFragment newInstance(int orientation) {
		if (orientation != HORIZONTAL && orientation != VERTICAL) {
			orientation = VERTICAL;
		}
		Bundle args = new Bundle();
		args.putInt(ORIENTATION, orientation);
		LinearFragment fragment = new LinearFragment();
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
		if (orientation == LinearLayoutManager.VERTICAL) {
			new MyTask().execute();
		}
	}

	private void setItemDecoration() {
		if (!isInit) {
			return;
		}
		if (itemDecoration != null) {
			recyclerView.removeItemDecoration(itemDecoration);
		}
		DividerLinearItemDecoration.Builder builder = new DividerLinearItemDecoration.Builder(getContext(), orientation);
		builder.setDivider(R.drawable.bg_divider_list);
		builder.setDividerPadding(padding8, padding5, 0, padding5);
		if (orientation == LinearLayoutManager.VERTICAL) {
			builder.setStickyHeader(true)
					.setStickyHeaderDrawable(R.drawable.bg_decoration)
					.setStickyHeaderHeight(90);
		}
		if (is_checked[0]) {
		} else {
			builder.removeHeaderDivider(is_checked[1])
					.removeFooterDivider(is_checked[2])
					.removeLeftDivider(is_checked[3])
					.removeRightDivider(is_checked[4])
			;
		}
		if (is_checked[5]) {
			builder.subDivider(1, 4)
					.setSubDividerHeight(24)
					.setSubDividerWidth(24)
					.setSubDividerDrawable(R.drawable.bg_divider_offset);
		}
		if (is_checked[6]) {
			builder.redrawDivider(2)
					.redrawDividerHeight(30)
					.redrawDividerDrawable(R.drawable.bg_divider_redraw);
		}
		if (is_checked[7]) {
			builder.redrawHeaderDivider()
					.redrawHeaderDividerHeight(40)
					.redrawHeaderDividerDrawable(R.drawable.bg_divider_offset);
		}
		if (is_checked[8]) {
			builder.redrawFooterDivider()
					.redrawFooterDividerHeight(40)
					.redrawFooterDividerDrawable(R.drawable.bg_divider_offset);
		}
		if (is_checked[9]) {
			builder.redrawLeftDivider().
					redrawLeftDividerWidth(40)
					.redrawLeftDividerDrawable(R.drawable.bg_divider_list);
		}
		if (is_checked[10]) {
			builder.redrawRightDivider()
					.redrawRightDividerWidth(40)
					.redrawRightDividerDrawable(R.drawable.bg_divider_list);
		}
	  /*  builder
				.redrawHeaderDivider().
                redrawHeaderDividerHeight(40)
                .redrawHeaderDividerDrawable(R.drawable.bg_divider_offset)
                .redrawFooterDivider()
                .redrawFooterDividerHeight(40)
                .redrawFooterDividerDrawable(R.drawable.bg_divider_offset)
                .redrawLeftDivider().
                redrawLeftDividerWidth(40)
                .redrawLeftDividerDrawable(R.drawable.bg_divider_list)
                .redrawRightDivider().
                redrawRightDividerWidth(40)
                .redrawRightDividerDrawable(R.drawable.bg_divider_list)
        ;*/
		itemDecoration = builder.build();
		recyclerView.addItemDecoration(itemDecoration);
	   /* itemDecoration = new DividerLinearItemDecoration(getContext(), orientation,R.drawable.bg_divider_list);
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
		boolean forceRefresh = false;
		if (orientation == LinearLayoutManager.VERTICAL) {
			for (int i = 0; i < 3; i++) {
				if (params[i] != is_checked[i]) {
					forceRefresh = true;
					break;
				}
			}
			if (!forceRefresh) {
				for (int i = 5; i < params.length; i++) {
					if (params[i] != is_checked[i]) {
						forceRefresh = true;
						break;
					}
				}
			}
			if (forceRefresh) {
				System.arraycopy(params, 0, is_checked, 0, 3);
				System.arraycopy(params, 5, is_checked, 5, params.length - 5);
				Log.d(TAG, "refreshFragment: is_checked111:" + Arrays.toString(is_checked));
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
				System.arraycopy(params, 3, is_checked, 3, params.length - 3);
				Log.d(TAG, "refreshFragment: is_checked222:" + Arrays.toString(is_checked));
				setItemDecoration();
			}

		}
	}

	class MyTask extends AsyncTask<Void, Void, List<Header>> {

		@Override
		protected List<Header> doInBackground(Void... voids) {
			InputStream inputStream = getResources().openRawResource(R.raw.user);
			Reader reader = new InputStreamReader(inputStream);
			UserInfo userInfo = new Gson().fromJson(reader, UserInfo.class);
			List<Header> data = userInfo.getData();
			return data;
		}

		@Override
		protected void onPostExecute(List<Header> headers) {
			if (mainAdapter != null) {
				mainAdapter.updateAll(headers);
			}
		}
	}
}
