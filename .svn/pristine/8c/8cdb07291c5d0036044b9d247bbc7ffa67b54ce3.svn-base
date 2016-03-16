package com.taotao.redboy.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class PersonCenterActivity extends BaseActivity{

	protected static final int GOTOHOME = 0;

	public static final String ISLOG = "ISLOG"; 

	public static final String USERID = "userid";

	public static final String USERNAME = "USERNAME";

	/**
	 * 返回图片
	 */
	@ViewInject(R.id.back)
	private ImageView back;
	
	@ViewInject(R.id.username)
	private EditText username;
	
	@ViewInject(R.id.password)
	private EditText password;
	
	@ViewInject(R.id.button_login)
	private Button button_login;
	
	@ViewInject(R.id.button_regs)
	private Button button_regs;

	/**
	 * 用户填写的用户名
	 */
	private String Username;

	/**
	 * 用户填写的密码
	 */
	private String Password;

	@Override
	public void initView() {

		ViewUtils.inject(this);//注册组件
		
		sp = getSharedPreferences("config", MODE_PRIVATE); 
	} 

	@Override 
	public void initData() {
 
		
		
	}

	@Override
	public void initLinstener() {

		back.setOnClickListener(this);
		
		button_login.setOnClickListener(this);
		
		button_regs.setOnClickListener(this);
	}

	@Override
	public int getLayout() {
		return R.layout.activity_personcenter;
	}
	  
	@Override
	public void initOtherButton(View v) {
		
		super.initOtherButton(v);
		
		switch (v.getId()) {
		case R.id.back://返回按钮
			finish();
			break;
		case R.id.button_login://登陆按钮
			LogIn();
			break;
		case R.id.button_regs://跳转注册页面
			Intent intent = new Intent(PersonCenterActivity.this,RegestActivity.class);
			
			username.setText("");
			
			password.setText("");
			
			startActivity(intent); 
			
		default:
			
			break;
		}
	}

	/**
	 * 登陆方法
	 */
	private void LogIn() {
		
		Username = username.getText().toString().trim();
		
		Password = password.getText().toString().trim();
		
		if (TextUtils.isEmpty(Username) || TextUtils.isEmpty(Password)) {
			
			System.out.println("username:::"+username); 

			Toast.makeText(context, "用户名或密码不能为空!", 0).show();
			
			return;
		}
		
		new Thread(){
			
			public void run() {
				
				HttpUtils utils = new HttpUtils();
				
				RequestParams params = new RequestParams();
				
				params.addBodyParameter("username", Username);
				
				params.addBodyParameter("password", Password);
				
				System.out.println("用户名:"+Username+"密码:"+Password); 
				
				utils.send(HttpMethod.POST, UrlUtils.BaseUrl+"/login", params, new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
						String str = responseInfo.result;
						
						ParserJson(str);
						
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
			};
		}.start();  
	}

	/**
	 * 解析从服务器获取的返回值
	 * @param str
	 */
	private void ParserJson(String str) {
		
		try {
			
			JSONObject jsonObject = new JSONObject(str);
			
			String responseString = jsonObject.getString("response");
			
			if("error".equals(responseString)){//说明登陆失败.需要获取返回信息,然后让用户知道
				
				JSONObject jsonObject2 = jsonObject.getJSONObject("error");//再次获取一个JSONObject对象,取出来text下面的信息提示
				
				String string = jsonObject2.getString("text");
				
				Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
				
			}else{
				
				JSONObject jsonObject2 = jsonObject.getJSONObject("userinfo"); 
				
				String ID = jsonObject2.getString("userid");//注册成功后的ID
				
				Toast.makeText(context, "ID:"+ID+"登陆成功!", 0).show();
				
				sp.edit().putBoolean(ISLOG, false).commit();
				
				sp.edit().putString(USERID, ID+"#"+Username).commit();
				
				sp.edit().putString(USERNAME, Username).commit(); 
				
				handler.sendEmptyMessageDelayed(GOTOHOME, 2000); 
				
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
			
			case GOTOHOME:
				
				Intent intent = new Intent(PersonCenterActivity.this,MainActivity.class);
				
				intent.putExtra("ISLOG", ISLOG);
				
				startActivity(intent);
				
				finish();
				
				break;

			default:
				break;
			}
			
		}
	};

	private SharedPreferences sp;
	
}





















