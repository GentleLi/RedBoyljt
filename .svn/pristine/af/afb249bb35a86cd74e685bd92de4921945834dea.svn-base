package com.taotao.redboy.fragment;

import com.lidroid.xutils.DbUtils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public DbUtils dbUtils;
	public Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		context = getActivity();
		dbUtils = DbUtils.create(getActivity(), "goods.db");
		View view= initView();
		initData();
		initListener();
		return view;
	}
	
	public void initListener() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//initData();
	}
	
	public abstract View initView();
	
	public abstract void initData();
	
	public int getLayoutId(){
		
		return 0;
	}
}
