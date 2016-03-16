package com.taotao.redboy.activity;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.taotao.redboy.utils.APPRes;

public class LjtRegisterActivity extends BaseActivity {

	@ViewInject(R.id.et_pwd1)
	private EditText etPwd1;
	@ViewInject(R.id.et_pwd2)
	private EditText etPwd2;
	@ViewInject(R.id.et_email)
	private EditText etEmail;
	private HttpUtils httpUtils;
	@ViewInject(R.id.btn_register)
	private Button btnRegister;

	@Override
	public void initView() {

		ViewUtils.inject(this);
	}

	@Override
	public void initData() {
		
		httpUtils = new HttpUtils();
	
		
		
	}

	private void doRegister(String email,String pwd1,String pwd2) {
		
		String emailReg="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		if (!email.matches(emailReg)) {
			
			Toast.makeText(context, "邮箱格式不合法", 0).show();
			return;
		}
		if (TextUtils.isEmpty(pwd1)) {
			
			Toast.makeText(context, "密码不能为空", 0).show();
			return;
		}
		if (!pwd1.equals(pwd2)) {
			
			Toast.makeText(context, "两次密码不一致", 0).show();
			return;
		}
		
		
		RequestParams params=new RequestParams();
		
		params.addBodyParameter("username", email);
		params.addBodyParameter("password", pwd1);
		httpUtils.send(HttpMethod.POST, APPRes.BASE_URL+APPRes.register_url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
//				

				System.out.println(responseInfo.result);
				parseJson(responseInfo.result);
			}


			@Override
			public void onFailure(HttpException error, String msg) {
				
				System.out.println("联网失败！"+msg);
			}
		});
		
	}

	/**
	 * 解析json数据
	 */
	private void parseJson(String json) {
		
//		{"response":"register","userinfo":{"userid":24}}
		try {
			
			JSONObject jsonObject=new JSONObject(json);
			String userId = jsonObject.getString("userid");
			//TODO
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		
		switch (v.getId()) {
		case R.id.btn_register:
			String email = etEmail.getText().toString().trim();
			String pwd1= etPwd1.getText().toString().trim();
			String pwd2 = etPwd2.getText().toString().trim();
			System.out.println(email+pwd1+pwd2);
			doRegister(email,pwd1,pwd2);
			break;
		}
		
	}
	
	@Override
	public void initLinstener() {

		btnRegister.setOnClickListener(this);
	}

	
	@Override
	public int getLayout() {
		return R.layout.layout_register;
	}

}
