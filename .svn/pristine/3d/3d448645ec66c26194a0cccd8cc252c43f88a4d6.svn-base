package com.taotao.redboy.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;

public class AboutActivity extends BaseActivity {

	/**
	 * tab的所有控件
	 * 
	 */
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;
	@ViewInject(R.id.back)
	private LinearLayout back;
	@ViewInject(R.id.tv_tab_more)
	private TextView tv_tab_more;
	
	
	@Override
	public void initView() {
		
		//注册控件
		ViewUtils.inject(this);
		
		initLayout();

	}
	/**
	 * 初始化当前显示的控件
	 */
	private void initLayout() {
		tv_tab_name.setText("关于");
		back.setVisibility(View.GONE);
		tv_tab_more.setVisibility(View.VISIBLE);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		
		tv_tab_more.setOnClickListener(this);

	}

	@Override
	public int getLayoutId() {
		return R.layout.about_activity;
	}
	
	@Override
	public void onInntClick(View v) {
		super.onInntClick(v);
		
		switch (v.getId()) {
		case R.id.tv_tab_more://点击了更多
			
			Toast.makeText(this, "我点击了更多", 0).show();
			
			break;

		default:
			break;
		}
	}

}
