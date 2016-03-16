package com.taotao.redboy.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.res.SpRes;
import com.taotao.redboy.utils.NetWorkUtils;

public class UserCentreActivity extends BaseActivity {
	/**
	 * tab控件
	 */
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;
	@ViewInject(R.id.back)
	private LinearLayout back;
	@ViewInject(R.id.tv_tab_more)
	private TextView tv_tab_more;
	@ViewInject(R.id.tv_tab_logout)
	private TextView tv_tab_logout;

	// 主页所需的控件
	/**
	 * 用户信息的控件
	 */
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	@ViewInject(R.id.tv_level)
	private TextView tv_level;
	@ViewInject(R.id.tv_integral)
	private TextView tv_integral;

	/**
	 * 订单等管理情况的控件
	 */
	@ViewInject(R.id.tv_my_indent)
	private TextView tv_my_indent;// 我的订单

	@ViewInject(R.id.tv_address_manager)
	private TextView tv_address_manager;// 地址管理
	@ViewInject(R.id.tv_gift)
	private TextView tv_gift;// 优惠券
	@ViewInject(R.id.tv_favorite)
	private TextView tv_favorite;// 收藏夹

	private SharedPreferences sp;
	private String[] splits;
	private String bonus;
	private String favoritescount;
	private String level;
	private String ordercount;

	@Override
	public void initView() {

		sp = getSharedPreferences("config", MODE_PRIVATE);
		// 注册所需控件
		ViewUtils.inject(this);

		tv_tab_logout.setVisibility(View.VISIBLE);
	}

	/**
	 * 初始化当前页面需要显示的布局
	 */
	private void initLayout() {
		tv_tab_name.setText("账号中心");
		tv_tab_more.setVisibility(View.VISIBLE);
		back.setVisibility(View.GONE);

		// 初始化界面的数据

		tv_name.setText(splits[1]);

		tv_level.setText(level);

		tv_integral.setText(bonus);

		// 购买信息

		tv_my_indent.setText("我的订单(" + ordercount + ")");

		tv_favorite.setText("收藏夹(" + favoritescount + ")");

		tv_gift.setText("优惠券/礼品卡(" + bonus + ")");
	}

	@Override
	public void initData() {
		// 根据用户id获取用户的一些信息
		String userid = sp.getString("userid", "");

		// System.out.println(userid);

		splits = userid.split("#");

		// 联网获取数据

		NetWorkUtils.requestGetData("/userinfo?userid=" + splits[0],
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// 链接完成
						String userinfo = arg0.result;
						System.out.println("用户信息：" + userinfo);
						// 解析用户信息，动态设置参数
						praserJson(userinfo);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// 链接失败
					}
				});

	}

	@Override
	public void initListener() {

		tv_tab_logout.setOnClickListener(this);
		
		//用户的的4中信息
		tv_my_indent.setOnClickListener(this);
		tv_address_manager.setOnClickListener(this);
		tv_gift.setOnClickListener(this);
		tv_favorite.setOnClickListener(this);
		
		
		
		
		

	}


		


	@Override
	public int getLayoutId() {
		return R.layout.user_centre_activity;
	}

	/**
	 * 解析json数据
	 * 
	 * @param userinfo
	 */
	protected void praserJson(String userinfo) {
		try {
			JSONObject jo = new JSONObject(userinfo);
			JSONObject jsonObject = jo.getJSONObject("userinfo");

			bonus = jsonObject.getString("bonus");

			favoritescount = jsonObject.getString("favoritescount");

			level = jsonObject.getString("level");

			ordercount = jsonObject.getString("ordercount");

			// 更新界面
			initLayout();

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onInntClick(View v) {
		super.onInntClick(v);
		switch (v.getId()) {
		case R.id.tv_tab_logout:
			// 点击退出登录
			// 清除sp，关闭当前页面，跳到登录页面

			//sp.edit().putString(SpRes.SP_USER_KEY, "").commit();

			startActivity(new Intent(this,LogOutActivity.class));
			
			//String userid = sp.getString("userid", "");
			
			
			
			

			
			finish();

			break;
			
		case R.id.tv_my_indent://我的订单
			Intent intent=new Intent(this,MyOrderActivity.class);
			intent.putExtra("userid", splits[0]);
			
			startActivity(intent);
			
			break;
			

		default:
			break;
		}
	}
	
	

}
