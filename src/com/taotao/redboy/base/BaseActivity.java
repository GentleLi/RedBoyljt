package com.taotao.redboy.base;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.taotao.redboy.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());
		
		initView();
		initData();
		initListener();
		
		//实现相同的逻辑
		initSameListener();
		
	}
	


	public abstract void initView();
	
	
	public abstract void initData();
	
	
	public abstract void initListener();
	
	public abstract int getLayoutId();
	
	/**
	 * 处理相同的点击事件
	 */
	private void initSameListener() {
		View back = findViewById(R.id.back);
		if(back!=null){
			back.setOnClickListener(this);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			
			finish();
			break;

		default:
			onInntClick(v);
			
			break;
		}
		
	}

	
	/**
	 * 处理其他的点击事件
	 * @param v
	 */
	public void onInntClick(View v) {
		
	}
	
	/**
	 * 联网获取数据
	 * @param url 查询的url地址
	 * @param method 请求方式
	 * @param params 传输的参数
	 * @param callBack 请求完成回调的方法
	 */
	public void getRequestData(String url,HttpMethod method,RequestParams params,RequestCallBack<String> callBack){
		
		HttpUtils httputils = new HttpUtils();

		httputils.send(method, url, params, callBack);
	};
	
	
	
	
	
	

}
