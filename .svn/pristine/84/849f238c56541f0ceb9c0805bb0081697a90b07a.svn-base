package com.taotao.redboy.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.text.TextUtils;
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

public class RegisterActivity extends BaseActivity {

	// 获得控件
	@ViewInject(R.id.et_name)
	private EditText et_name;
	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;
	@ViewInject(R.id.btn_reg)
	private Button btn_reg;
	@ViewInject(R.id.et_affirm_pwd)
	private EditText et_affirm_pwd;

	@Override
	public void initView() {

		// view注入
		ViewUtils.inject(this);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		btn_reg.setOnClickListener(this);

	}

	@Override
	public int getLayoutId() {
		return R.layout.register_activity;
	}

	@Override
	public void onInntClick(View v) {
		super.onInntClick(v);
		switch (v.getId()) {
		case R.id.btn_reg:// 注册
			// 这里是注册页面，点击注册了，就提交数据

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
		//获取界面输入的数据
		
		String name=et_name.getText().toString().trim();
		
		String pwd=et_pwd.getText().toString().trim();
		
		String pwd2=et_affirm_pwd.getText().toString().trim();
		
		//判断
		if(TextUtils.isEmpty(name)){
			Toast.makeText(this, "请填写账号", 0).show();
			return;
		}
		if(TextUtils.isEmpty(pwd)){
			Toast.makeText(this, "请填写密码", 0).show();
			return;
		}
		if(TextUtils.isEmpty(pwd2)){
			Toast.makeText(this, "请填写确认密码", 0).show();
			return;
		}
		if(!TextUtils.equals(pwd2, pwd)){
			Toast.makeText(this, "两次密码输入不一致", 0).show();
			return;	
		}
		// 跳转到注册页面
		HttpUtils httputils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", name);
		params.addBodyParameter("password", pwd);

		httputils.send(HttpMethod.POST,
				"http://169.254.224.51/ECServicez8/register", params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(RegisterActivity.this, arg1, 0).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

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

			String result = jsonobject.getString("response");

			if ("error".equals(result)) {
				JSONObject errorJsonObject = jsonobject.getJSONObject("error");
				String text = errorJsonObject.getString("text");
				Toast.makeText(RegisterActivity.this, text, 0).show();

				// 清空输入框

				et_name.setText("");
				et_pwd.setText("");

			} else {
				Toast.makeText(RegisterActivity.this, "注册完成完成", 0).show();
				//先跳转到登陆页面
				startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
				finish();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
