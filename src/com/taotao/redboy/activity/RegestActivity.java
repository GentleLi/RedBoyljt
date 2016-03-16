package com.taotao.redboy.activity;

import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.fragment.HomeFragment;
import com.taotao.redboy.utils.UrlUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegestActivity extends BaseActivity {

	private static final int GOTOHOME = 0;

	@ViewInject(R.id.back)
	private ImageView back;

	@ViewInject(R.id.regest_username)
	private EditText regest_username;

	@ViewInject(R.id.regest_password_1)
	private EditText regest_password_1;

	@ViewInject(R.id.regest_password_2)
	private EditText regest_password_2;

	@ViewInject(R.id.regest_button)
	private Button regest_button;

	private String username;

	private String password_1;

	private String password_2;

	@Override
	public void initView() {
		ViewUtils.inject(this);// 注册组件
		sp = getSharedPreferences("config", 0); 
	}

	@Override
	public void initData() {

	}

	@Override
	public void initLinstener() {
		back.setOnClickListener(this);
		regest_button.setOnClickListener(this);
	}

	@Override
	public int getLayout() {
		return R.layout.activity_regest;
	}

	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		switch (v.getId()) {
		case R.id.regest_button:// 提交按钮
			regestUserName();
			break;
		case R.id.back:// 返回按钮
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 联网注册
	 */
	private void regestUserName() {
		
		username = regest_username.getText().toString().trim();
		password_1 = regest_password_1.getText().toString().trim();
		password_2 = regest_password_2.getText().toString().trim();
		
		if (TextUtils.isEmpty(username)) {
			
			System.out.println("username:::"+username); 

			Toast.makeText(context, "用户名不能为空!", 0).show();
			
			return;
		}
		
		if (TextUtils.isEmpty(password_2) || TextUtils.isEmpty(password_1)) {

			Toast.makeText(context, "密码不能为空!", 0).show();
			
			return;
			
		}
		
		if(!password_2.equals(password_1)){
			
			Toast.makeText(context, "两次密码不相同,请重新输入!", 0).show();
			
			password_2 = "";
			
			password_1 = "";
			
			return;
		}

		HttpUtils httpUtils = new HttpUtils();

		RequestParams params = new RequestParams();

		params.addBodyParameter("username", username);

		params.addBodyParameter("password", password_2);

		httpUtils.send(HttpMethod.POST,
				
				UrlUtils.BaseUrl+"/register", params,
				
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						
						System.out.println("resultresultresult   "+result);

						ParserJson(result);
					}
					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}
	/**
	 * 解析Json
	 * @param result 需要解析的字符串
	 */
	private void ParserJson(String result) {
		
try {
			
			JSONObject jsonObject = new JSONObject(result);
			
			String responseString = jsonObject.getString("response");
			
			if("error".equals(responseString)){//说明登陆失败.需要获取返回信息,然后让用户知道
				
				JSONObject jsonObject2 = jsonObject.getJSONObject("error");//再次获取一个JSONObject对象,取出来text下面的信息提示
				
				String string = jsonObject2.getString("text");//注册失败后的返回的错误
				
				Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
				
			}else{
				
				JSONObject jsonObject2 = jsonObject.getJSONObject("userinfo"); 
				
				String userid = jsonObject2.getString("userid");//注册成功后的ID
				
				Toast.makeText(context, "注册成功!你的ID是:"+userid+",2秒钟之后跳转到首页,请稍等..", 0).show();
				
				/**
				 * 
				 * 	/**
				
				sp.edit().putString(USERID, ID); 
				
				sp.edit().putString(USERNAME, Username); 
		 */
		
				sp.edit().putString(PersonCenterActivity.USERID, userid).commit();
				
				sp.edit().putString(PersonCenterActivity.USERNAME, username).commit();
				
				sp.edit().putBoolean(PersonCenterActivity.ISLOG, false).commit();
				
				handler.sendEmptyMessageDelayed(GOTOHOME, 2000);
				
				Intent intent = new Intent("CHANGEUI");
				
				intent.putExtra("userid", userid);
				
				intent.putExtra("username", username);
				
				sendBroadcast(intent);//注册完成之后发送一个广播,通知HomeFragment更改登陆状态
				
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	}
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			
			switch (msg.what) {
			case GOTOHOME://注册成功之后,显示一个activity,去登陆页面
				Intent intent = new Intent(RegestActivity.this,MainActivity.class);
				startActivity(intent);
			
				break;
			default:
				break;
			}
		}
	};

	private SharedPreferences sp;
}






















