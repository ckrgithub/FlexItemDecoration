package com.ckr.flexitemdecoration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ckr.flexitemdecoration.R;
import com.ckr.flexitemdecoration.model.Header;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC大佬 on 2018/1/4.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {
	private Context mContext;
	private List<Header> headerList;

	public MainAdapter(Context context) {
		mContext = context;
	}

	public void updateAll(List<Header> headers){
		if (headers == null) {
			return;
		}
		if (headerList == null) {
			headerList=new ArrayList<>(headers.size());
		}
		headerList.clear();
		headerList.addAll(headers);
		notifyDataSetChanged();
	}

	@Override
	public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new MainHolder(LayoutInflater.from(mContext).inflate(R.layout.item_picture, parent, false));
	}

	@Override
	public void onBindViewHolder(MainHolder holder, int position) {
		holder.titleView.setText("item " + position);
		if (headerList != null&&headerList.size()!=0) {
			Header header = headerList.get(position);
			holder.userName.setText(header.getUserName());
		}
	}

	@Override
	public int getItemCount() {
		if (headerList == null||headerList.size()==0) {
			return 13;
		}
		return headerList.size();
	}

	class MainHolder extends RecyclerView.ViewHolder {

		private TextView titleView;
		private TextView userName;

		public MainHolder(View itemView) {
			super(itemView);
			titleView = (TextView) itemView.findViewById(R.id.title);
			userName = (TextView) itemView.findViewById(R.id.userName);
		}
	}

}
