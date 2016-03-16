package com.taotao.redboy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.utils.UrlUtils;

public class LogOutActivity extends BaseActivity {

	protected static final int GOTOHOME = 0;

	@ViewInject(R.id.backup)
	private ImageView back;
	
	@ViewInject(R.id.logout_tv)
	private TextView logout_tv;
	
	@ViewInject(R.id.logout_button)
	private Button logout_button;

	private SharedPreferences sp;

	private String userId;

	private String userName;
	
	@Override
	public void initView() {
		
		sp = getSharedPreferences("config", 0);  
		
		ViewUtils.inject(this);
		
	}

	@Override
	public void initData() {
		
		/**
				
				sp.edit().putString(USERID, ID); 
				
				sp.edit().putString(USERNAME, Username); 
		 */
		
		//userId = sp.getString(PersonCenterActivity.USERID, ""); 
		
		//userName = sp.getString(PersonCenterActivity.USERNAME, "");
		String userids = sp.getString("userid", "");
		
		splits = userids.split("#");
		
		logout_tv.setText("用户"+splits[1]+"已登录!");

	}

	@Override
	public void initLinstener() {

		back.setOnClickListener(this);
		
		logout_button.setOnClickListener(this);
		
	}

	@Override
	public int getLayout() {
		return R.layout.activity_logout;
	}
	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.logout_button:
			LogOut();
			break;
		default:
			break;
		}
	}
	/**
	 * 注销登录的方法
	 */
	private void LogOut() {

		HttpUtils httpUtils = new HttpUtils();
		
		RequestParams params = new RequestParams();
		
		params.addBodyParameter("userId", splits[0]);
		
		httpUtils.send(HttpMethod.POST, UrlUtils.BaseUrl+"/logout", params, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				sp.edit().putBoolean(PersonCenterActivity.ISLOG, true).commit();
				
				Toast.makeText(context, "注销登陆成功!2秒之后进入首页", 0).show();
				
				handler.sendEmptyMessageDelayed(GOTOHOME, 2000); 
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) { 
			case GOTOHOME:
				Intent intent = new Intent(LogOutActivity.this, MainActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};

	private String[] splits;
}
















