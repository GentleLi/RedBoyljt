package com.taotao.redboy.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.res.HttpRes;

public class LoginActivity extends BaseActivity {

	// 获得控件
	@ViewInject(R.id.et_name)
	private EditText et_name;
	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;
	@ViewInject(R.id.btn_login)
	public Button btn_login;
	
	@ViewInject(R.id.btn_reg)
	private Button btn_reg;

	private SharedPreferences sp;
	private String name;
	@Override
	public void initView() {
		
		// view注入
		ViewUtils.inject(this);
		
		sp=getSharedPreferences("config", MODE_PRIVATE);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		btn_login.setOnClickListener(this);
		btn_reg.setOnClickListener(this);

	}

	@Override
	public int getLayoutId() {
		return R.layout.login_activity;
	}

	@Override
	public void onInntClick(View v) {
		super.onInntClick(v);
		switch (v.getId()) {
		case R.id.btn_login:// 登录
			// 访问网络，获取接货
			requestLogin();

			break;
		case R.id.btn_reg:// 注册
			// 跳转到注册页面

			requestReg();

			break;

		default:
			break;
		}

	}

	/**
	 * 注册
	 */
	private void requestReg() {
		// 跳转到注册页面
		startActivity(new Intent(this, RegisterActivity.class));

	}

	/**
	 * 登录
	 */
	private void requestLogin() {
		
		
		HttpUtils httputils = new HttpUtils();
		// post请求登陆
		RequestParams params = new RequestParams();
		name = et_name.getText().toString().trim();
		params.addBodyParameter("username", name);
		params.addBodyParameter("password", et_pwd.getText().toString().trim());

		httputils.send(HttpMethod.POST,
				HttpRes.base_uRL+"/login", params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						System.out.println("链接错误：" + arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						System.out.println("登录或注册完成" + arg0.result);
						
						praseJson(arg0);
					}
				});

	}

	/**
	 * 解析返回的结果
	 */
	private void praseJson(ResponseInfo<String> arg0) {

		// {"error":{"text":"用户名已存在"},"response":"error"}
		
		try {
			String json = arg0.result;
			JSONObject jsonobject = new JSONObject(json);
			
			String userinfo = jsonobject.getJSONObject("userinfo").getString("userid");

			String result = jsonobject.getString("response");
			
			System.out.println("我的信息："+result);

			if ("error".equals(result)) {
				JSONObject errorJsonObject = jsonobject.getJSONObject("error");
				String text = errorJsonObject.getString("text");
				Toast.makeText(LoginActivity.this, text, 0).show();

				// 清空输入框

				et_name.setText("");
				et_pwd.setText("");

			} else {

				//System.out.println("用户id：" + userinfo);
				Toast.makeText(LoginActivity.this, "用户id：" + userinfo, 0).show();
				// 可以得到用户的id，把id存到sp中
				
				sp.edit().putString("userid", userinfo+"#"+name).commit();
				//跳转到账户中心
				startActivity(new Intent(this,UserCentreActivity.class));
				finish();
				

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
