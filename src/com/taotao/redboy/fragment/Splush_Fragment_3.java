package com.taotao.redboy.fragment;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.taotao.redboy.R;
import com.taotao.redboy.activity.MainActivity;
import com.taotao.redboy.utils.VersionUtils;

public class Splush_Fragment_3 extends Fragment implements OnClickListener{

	private static final int GOMAIN = 0;
	/**
	 * 立即登录按钮
	 */
	private Button button_in_splush;
	private View view;
	/**
	 * 检查更新按钮
	 */
	private Button button_update_splush;
	/**
	 * 显示版本信息
	 */
	private TextView textView1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = View.inflate(getActivity(), R.layout.fragment_splush_3, null);
		
		button_in_splush = (Button) view.findViewById(R.id.button_in_splush);
		button_update_splush = (Button) view.findViewById(R.id.button_update_splush);
		textView1 = (TextView) view.findViewById(R.id.textView1); 
		
		button_in_splush.setOnClickListener(this);
		button_update_splush.setOnClickListener(this);
		
		return view;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		
		case R.id.button_in_splush://立即使用的按钮
			
			Intent intent = new Intent(getActivity(), MainActivity.class);
			
			startActivity(intent);
			
			getActivity().finish();
			
			break;
			
		case R.id.button_update_splush:
			
			updateVersioin();

		default:
			break;
		}
	}
	/**
	 * 联网更新软件版本
	 */
	private void updateVersioin() {
		
		HttpUtils hpptUtils = new HttpUtils();
		
		hpptUtils.send(HttpMethod.GET, "http://169.254.221.197:8080/ECServicez8/category", new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				ParserJson(responseInfo.result);
				
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
				Toast.makeText(getActivity(), "连接失败了,2秒钟之后自动跳转,请稍等...", 0).show();
				
				handler.sendEmptyMessageDelayed(GOMAIN, 2000); 
				
			}
		});
	}
	/**
	 * 解析Json
	 * @param result 输入需要解析的字符串
	 */
	private void ParserJson(String result) {
		
		try {
			JSONObject jb = new JSONObject(result);
			
			String ServiceVersionName = jb.getString("version");//服务器中获取的本版号1.1
			
			String versionName = VersionUtils.getVersionName(getActivity());//1.1
			
			if(ServiceVersionName.equals(versionName)){//如果版本号一样的话,跳出对话框
				
				Toast.makeText(getActivity(),"无需更新,两秒钟后自动进入应用!", 0).show();
				
				handler.sendEmptyMessageDelayed(GOMAIN, 2000); 
				
			}else{//如果不一样的话,跳转到下载页
				
				Toast.makeText(getActivity(),"需要下载", 0).show();
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
			case GOMAIN:
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
				getActivity().finish();
				break;

			default:
				break;
			}
		}
	};
}














