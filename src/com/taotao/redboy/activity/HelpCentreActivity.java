package com.taotao.redboy.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.bean.HelpCentreBean;
import com.taotao.redboy.res.HttpRes;

/**
 * 帮助中心的内容
 * 
 * @author zupp
 * 
 */
public class HelpCentreActivity extends BaseActivity {
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;

	@ViewInject(R.id.btn_user_centre)
	private Button btn_user_centre;
	@ViewInject(R.id.btn_browse)
	private Button btn_browse;
	@ViewInject(R.id.btn_help_centre)
	private Button btn_help_centre;
	@ViewInject(R.id.user_feedback)
	private Button user_feedback;
	@ViewInject(R.id.btn_about)
	private Button btn_about;
	@ViewInject(R.id.scrollView2)
	private ScrollView scrollView2;
	@ViewInject(R.id.lv)
	private ListView lv;

	/**
	 * sp中存储的是version版本
	 */
	private SharedPreferences sp;

	private List<HelpCentreBean> helpList = new ArrayList<HelpCentreBean>();

	private MyHelpAdapter adapter;

	/**
	 * 缓存使用的对象
	 */
	private DbUtils dbutils;

	@Override
	public void initView() {
		// 修改tab标签
		// 注册控件
		ViewUtils.inject(this);

		sp = getSharedPreferences("config", MODE_PRIVATE);

		dbutils = DbUtils.create(this, "helpcentre.db");

		initLayout();

		// 获取之前的版本
		int lastVersion = sp.getInt("version", 0);
		
		adapter = new MyHelpAdapter();
		lv.setAdapter(adapter);

		if (lastVersion == 0) {
			// 第一次访问只能连接网络
			requestHelpData(lastVersion);

		} else {
			// 不等于0的时候，先读取本地，然后再在联网获取数据
			try {
				List<HelpCentreBean> localData = dbutils.findAll(HelpCentreBean.class);
				if(localData!=null){
					helpList.addAll(localData);
					updateUi();
				}
				// 加到已有的集合中
				// 跟新界面
				// 然后再联网获取数据
				requestHelpData(lastVersion);
				
				
			} catch (DbException e) {
				e.printStackTrace();
			}

			
		}
	}

	/**
	 * 初始化当前页面需要显示的控件
	 */
	private void initLayout() {
		tv_tab_name.setText("帮助中心");
		user_feedback.setVisibility(View.GONE);
		btn_about.setVisibility(View.GONE);
		scrollView2.setVisibility(View.GONE);
		lv.setVisibility(View.VISIBLE);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initListener() {
		

	}

	@Override
	public int getLayoutId() {
		return R.layout.fragment_more;
	}

	/**
	 * 联网获取help列表的信息
	 * 
	 * @param lastVersion
	 */
	private void requestHelpData(int lastVersion) {

		HttpUtils httputils = new HttpUtils();

		httputils.send(HttpMethod.GET, HttpRes.base_uRL + "/help?version="
				+ lastVersion, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {

			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {

				/*
				 * 如果没有更靠前的版本获得集合为空，如果有就加到集合汇总并换从到 数据库
				 */
				System.out.println("我是help中心的列表信息：" + arg0.result);

				// 判断是否需要解析

				praserJson(arg0.result);

				// 跟新界面
				updateUi();

				// 保存获取的数据到本地数据库

				saveDbGoLocal();

			}

		});
	}

	/**
	 * 解析json
	 * 
	 * @param result
	 */
	protected void praserJson(String result) {

		try {
			JSONObject jb = new JSONObject(result);
			JSONArray helpArray = jb.getJSONArray("helpList");

			// System.out.println("这是多少：："+helpArray.length());
			if (helpArray.length() == 0) {
				return;
			}
			// 如果有东西就先获得数据，储存version版本号

			for (int i = 0; i < helpArray.length(); i++) {

				JSONObject jsonObject = helpArray.getJSONObject(i);
				long id = jsonObject.getLong("id");
				String title = jsonObject.getString("title");
				helpList.add(new HelpCentreBean(id, title));
			}

			// 保存当前的version
			// 保存sp
			int version = jb.getInt("version");
			System.out.println("版本：" + version);
			sp.edit().putInt("version", version).commit();

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	class MyHelpAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return helpList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder vh = null;

			if (convertView == null) {
				vh = new ViewHolder();
				convertView = View.inflate(HelpCentreActivity.this,
						R.layout.helpcentre_itme, null);
				vh.ll_help = (LinearLayout) convertView
						.findViewById(R.id.ll_help_centre);
				vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			// 赋值
			vh.tv_name.setText(helpList.get(position).title);

			return convertView;
		}

	}

	class ViewHolder {

		public TextView tv_name;
		public LinearLayout ll_help;

	}

	private void updateUi() {
		// 刷新adapter展示数据
		adapter.notifyDataSetChanged();
	}

	/**
	 * 保存信息到本地数据库
	 */
	private void saveDbGoLocal() {
		try {
			dbutils.saveAll(helpList);
		} catch (DbException e) {
			e.printStackTrace();
		}
	}

}
