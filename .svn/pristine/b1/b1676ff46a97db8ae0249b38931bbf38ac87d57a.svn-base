package com.taotao.redboy.fragment;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.activity.AboutActivity;
import com.taotao.redboy.activity.HelpCentreActivity;
import com.taotao.redboy.activity.LoginActivity;
import com.taotao.redboy.activity.UserCentreActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import android.widget.TextView;


public class MoreFragment extends BaseFragment implements OnClickListener {
	@ViewInject(R.id.btn_user_centre)
	private Button btn_user_centre;
	@ViewInject(R.id.btn_browse)
	private Button btn_browse;
	@ViewInject(R.id.btn_help_centre)
	private Button btn_help_centre;
	@ViewInject(R.id.user_feedback)
	private Button user_feedback;
	@ViewInject(R.id.btn_about)
	private Button btn_about;
	@ViewInject(R.id.back)
	private LinearLayout ll_tab_back;
	
	private SharedPreferences sp;
	
	
	@Override
	public View initView() {
		
		sp=getActivity().getSharedPreferences("config", getActivity().MODE_PRIVATE);
		
	 	View view=View.inflate(getActivity(), R.layout.fragment_more, null);
	 	/**
	 	 * 注册控件
	 	 */
	 	ViewUtils.inject(this, view);
	 	
	 	ll_tab_back.setVisibility(View.GONE);
	 	
	 	regListener();
	 	
		return view;
	}
	/**
	 * 注册点击事件
	 */
	private void regListener() {
		btn_user_centre.setOnClickListener(this);
		btn_browse.setOnClickListener(this);
		btn_help_centre.setOnClickListener(this);
		user_feedback.setOnClickListener(this);
		btn_about.setOnClickListener(this);
		
		
	}

	@Override
	public void initData() {
	
	}
	
	
	
	@Override
	/**
	 * 点击更多一级条目事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_user_centre:
			String userid=sp.getString("userid", "");
			
			if(TextUtils.equals(userid, "")){
				//用户中心判断是否登录，假设未登录
				startActivity(new Intent(getActivity(),LoginActivity.class));
			}else{
				//如果有用户就跳转到用户信息
				Intent intent =new Intent(getActivity(),UserCentreActivity.class);
				intent.putExtra("userid", userid);
				startActivity(intent);
			}
			
			
			
			break;
		case R.id.btn_browse:
			
			break;
		case R.id.btn_help_centre:
			//跳转到帮助中心
			Intent intent=new Intent(getActivity(),HelpCentreActivity.class);
			startActivity(intent);
			break;
		case R.id.user_feedback:
			
			break;
		case R.id.btn_about://关于
			startActivity(new Intent(getActivity(),AboutActivity.class));
			
			break;

		default:
			break;
		}
		
	}
	
	
	

}
