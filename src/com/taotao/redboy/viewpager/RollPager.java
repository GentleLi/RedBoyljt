package com.taotao.redboy.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

public class RollPager extends ViewPager {

	public RollPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
		initData();
	}

	public RollPager(Context context) {
		super(context);
		initView();
		initData();
	}

	private void initView() {
		
		
		
	}

	private void initData() {
		
		
	}
	
	private void initListener(){
		
	}
	
	
	/**
	 * 自定义ViewPager的Item监听,用于点击ViewPager的Item之后显示相应的名称
	 */
	public  interface OnPagerItemClickLinster{
		
		public void onPagerItem(int positon);
		
	}
	public  OnPagerItemClickLinster onPagerItemClickLinster;
	
	public void setonPagerItemClickLinster(OnPagerItemClickLinster onPagerItemClickLinster){
		
		this.onPagerItemClickLinster = onPagerItemClickLinster;
	}
}














