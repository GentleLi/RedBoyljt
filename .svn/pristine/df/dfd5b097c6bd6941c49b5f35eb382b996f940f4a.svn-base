package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.taotao.redboy.R;
import com.taotao.redboy.adapter.Splush_ViewPager_Adapter;
import com.taotao.redboy.fragment.Splush_Fragment_1;
import com.taotao.redboy.fragment.Splush_Fragment_2;
import com.taotao.redboy.fragment.Splush_Fragment_3;

public class SplushActivity extends BaseActivity {
	
	private ViewPager viewpager_splush;
	
	private ArrayList<Fragment> PicList;

	@Override
	public void initView() {
		
		viewpager_splush = (ViewPager) findViewById(R.id.Viewpager_splush);
		
	}

	@Override
	public void initData() {
		
		PicList = new ArrayList<Fragment>();

		Splush_Fragment_1 sf1 = new Splush_Fragment_1();
		Splush_Fragment_2 sf2 = new Splush_Fragment_2();
		Splush_Fragment_3 sf3 = new Splush_Fragment_3();
		
		PicList.add(sf1);
		PicList.add(sf2);
		PicList.add(sf3);
		
		adapter = new Splush_ViewPager_Adapter(getSupportFragmentManager(),PicList);
		
		viewpager_splush.setAdapter(adapter);
	}

	/**
	 * adapter
	 */
	private Splush_ViewPager_Adapter adapter;
	
	@Override
	public void initLinstener() {

	}

	@Override
	public int getLayout() {
		return R.layout.activity_splush;
	}
}
