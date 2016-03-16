package com.taotao.redboy.activity;
import java.util.HashMap;
import java.util.Map;

import android.view.View;
import android.webkit.URLUtil;
import android.widget.ImageView;

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
public class TopicActivity extends BaseActivity {
	
	@ViewInject(R.id.back)
	private ImageView back;

	@Override
	public void initView() {
		
		ViewUtils.inject(this);
		
	}

	@Override
	public void initData() {

		new Thread(){
			
			public void run() {
				
				HttpUtils httpUtils = new HttpUtils();
				
				httpUtils.send(HttpMethod.GET, UrlUtils.BaseUrl+"/topic?pager=1&pageNum=2",new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
						String str = responseInfo.result;
						
						System.out.println("strstrstrstrstr   "+str);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
			};
		}.start();
	}

	@Override
	public void initLinstener() {

		back.setOnClickListener(this);
		
	}

	@Override
	public int getLayout() {
		return R.layout.activity_topic;
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
}





































