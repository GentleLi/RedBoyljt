package com.taotao.redboy.activity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.utils.UrlUtils;

public class SerachActivity extends BaseActivity {

	@ViewInject(R.id.back)
	private ImageView back;
	
	
	/**
	 * 上一个界面输入的搜索的关键字
	 */
	private String serachString;

	@Override
	public void initView() {

		ViewUtils.inject(this);
		
	}

	private ProgressDialog pd;
	
	@Override
	public void initData() {

		serachString = getIntent().getStringExtra("serachString");
		
//		pd = new ProgressDialog(context);
//		
//		pd.setMessage("正在努力加载中!");
//		
		new Thread(){
			
			public void run() {
				
//				pd.show();
				
				getDataFromServer();
				
			}
			
		}.start();
		
	}

	@Override
	public void initLinstener() {

		back.setOnClickListener(this);		
		
	}

	@Override
	public int getLayout() {
		return R.layout.activity_serach;
	}
	
	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 联网获取搜索数据
	 */
	private void getDataFromServer() {
		
		HttpUtils httpUtils = new HttpUtils();
		
		httpUtils.send(HttpMethod.GET, UrlUtils.BaseUrl+"/product?keyword=玩具&page=1&pageNum=10", new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				String FrmoServerData = responseInfo.result;
				
				System.out.println("FrmoServerData         "+FrmoServerData); 
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
			}
		});
	};
}
























