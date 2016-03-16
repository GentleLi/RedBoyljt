package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.fragment.BaseFragment;
import com.taotao.redboy.fragment.BrandFragment;
import com.taotao.redboy.fragment.CartFragment;
import com.taotao.redboy.fragment.HomeFragment;
import com.taotao.redboy.fragment.MoreFragment;
import com.taotao.redboy.fragment.SearchFragment;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener {

	@ViewInject(R.id.radio_group)
	private RadioGroup radioGroup;
	@ViewInject(R.id.frags_content)
	private FrameLayout frags_content;

	private ArrayList<BaseFragment> fragments;

	private FragmentManager fragmentManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initView();
		initData();
	}

	private void initView() {
		ViewUtils.inject(this);
		
		fragmentManager = getSupportFragmentManager();
		regListener();
	}
	
	private void initData(){
		fragments=new ArrayList<BaseFragment>();
		HomeFragment homeFragment=new HomeFragment();
		SearchFragment searchFragment=new SearchFragment();
		BrandFragment brandFragment=new BrandFragment();
		CartFragment cartFragment=new CartFragment();
		MoreFragment moreFragment=new MoreFragment();
		fragments.add(homeFragment);
		fragments.add(searchFragment);
		fragments.add(brandFragment);
		fragments.add(cartFragment);
		fragments.add(moreFragment);
		fragmentManager.beginTransaction().replace(R.id.frags_content, fragments.get(0)).commit();
	}

	private void regListener() {

		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		int index=0;
		switch (checkedId) {
		case R.id.rb_home://首页
			index=0;
			break;
		case R.id.rb_search://搜索
			index=1;
			break;
		case R.id.rb_brand://品牌
			index=2;
			break;
		case R.id.rb_cart://购物车
			index=3;
			break;
		case R.id.rb_more://更多
			index=4;
			break;
		}
		transaction.replace(R.id.frags_content, fragments.get(index)).commit();
	}

}
