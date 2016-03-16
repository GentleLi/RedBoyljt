package com.taotao.redboy.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Splush_ViewPager_Adapter extends FragmentPagerAdapter {

	private ArrayList<Fragment> Fragmentlist;

	public Splush_ViewPager_Adapter(FragmentManager fm,ArrayList<Fragment> Fragmentlist) {
		super(fm);
		this.Fragmentlist = Fragmentlist;
	}

	@Override
	public Fragment getItem(int position) {
		return Fragmentlist.get(position);
	}

	@Override
	public int getCount() {
		return Fragmentlist.size();
	}
	
}
