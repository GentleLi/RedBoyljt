package com.taotao.redboy.fragment;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.activity.SearchShowActivity;
import com.taotao.redboy.bean.SearchRecordBean;
import com.taotao.redboy.res.HttpRes;

public class SearchFragment extends BaseFragment implements OnClickListener {
	@ViewInject(R.id.iv_search)
	private ImageView iv_search;
	@ViewInject(R.id.et_search_input)
	private EditText et_search_input;
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;
	@ViewInject(R.id.back)
	private LinearLayout back;
	private String input;

	@ViewInject(R.id.elv_list)
	private ExpandableListView elv_list;
	private JSONArray searchArray;

	private String[] titles = new String[] { "热门搜索", "搜索历史" };
	
	private DbUtils dbutils;
	private List<SearchRecordBean> historyList;
	private MyAdapter adapter;

	@Override
	public View initView() {
		/**
		 * 创建存储db数据的用具
		 */
		dbutils=DbUtils.create(getActivity(), "searchRecord.db");
		
		View view = View.inflate(getActivity(), R.layout.fragment_search, null);

		// 注册
		ViewUtils.inject(this, view);

		tv_tab_name.setText("搜索");

		back.setVisibility(View.GONE);

		regListener();

		return view;
	}

	/**
	 * 注册监听
	 */
	private void regListener() {
		iv_search.setOnClickListener(this);
		
		elv_list.setOnChildClickListener(new OnChildClickListenerImplementation());
		
		
		adapter = new MyAdapter();

		elv_list.setAdapter(adapter);
		
		
		dbCache();
	}

	private void dbCache() {
		try {
			historyList = dbutils.findAll(SearchRecordBean.class);
		} catch (DbException e) {
			e.printStackTrace();
		}
		
		if(historyList!=null){
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initData() {
		
		

		netWorkData();

	}
	@Override
	public void onClick(View v) {
		// 点击的时候获取数据库的信息

		// http://192.168.2.109/ECServicez8/search?keyword=玩具&page=1&pageNum=10&orderby=saleDown
		switch (v.getId()) {
		case R.id.iv_search:
			input = et_search_input.getText().toString().trim();

			// System.out.println(input);
			gotoShowPager(input);
			// 根据关键字筛选

			break;

		default:
			break;
		}

	}

	/**
	 * 联网获取数据
	 * 
	 * @param input
	 */
	private void netWorkData() {
		HttpUtils httpUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		httpUtils.send(HttpMethod.GET, HttpRes.base_uRL + "/search/recommend",
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						arg0.printStackTrace();
						System.out.println("异常：" + arg1);

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						System.out.println(arg0.result);

						String searchJson = arg0.result;

						praserJson(searchJson);

					}

				});

	}

	/**
	 * 推荐解析
	 * 
	 * @param searchJson
	 */
	private void praserJson(String searchJson) {
		// {"response":"searchrecommend","search_keywords":["123","360","啤酒","联想电脑","惠普电脑","傻逼","这是什么"]}

		try {
			JSONObject jo = new JSONObject(searchJson);

			searchArray = jo.getJSONArray("search_keywords");
			
			
			adapter.notifyDataSetChanged();
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(searchShowBean.productlist.get(3).name);

	}

	private void gotoShowPager(String input) {
		goToShow(input);
	}
	/**
	 * 携带需要查询的数据类型到展示页面
	 */
	private void goToShow(String input) {
		// 获取数据后跳转到数据展示信息
		Intent intent = new Intent(getActivity(), SearchShowActivity.class);
		intent.putExtra("name", input);
		getActivity().startActivity(intent);
		//保存历史记录到数据库中
		try {
			
			dbutils.save(new SearchRecordBean(input));
			
		} catch (DbException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 条目的点击事件
	 * @author zupp
	 *
	 */
	private final class OnChildClickListenerImplementation implements
			OnChildClickListener {
		@Override
		public boolean onChildClick(ExpandableListView parent, View v,
				int groupPosition, int childPosition, long id) {
			
			try {
				String itemStr=searchArray.getString(childPosition);
				
				goToShow(itemStr);
				
				//System.out.println("itemStr :"+itemStr);
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return true;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		
		dbCache();
		
	}
	/**
	 * listview的适配器
	 */
	class MyAdapter extends BaseExpandableListAdapter {

		@Override
		public int getGroupCount() {
			return titles.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			if(groupPosition==0){
				return searchArray==null?1:searchArray.length();
			}else{

				return(historyList==null?0:historyList.size());
			}
		}

		@Override
		/**
		 * 主布局
		 */
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.fragment_search_item, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.tv_title);

			title.setText(titles[groupPosition]);

			return convertView;
		}

		@Override
		/**
		 * 子布局
		 */
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(getActivity(),
						R.layout.fragment_search_item2, null);
			}
			
			TextView name = (TextView) convertView.findViewById(R.id.tv_pro_name);
			System.out.println(groupPosition+"==="+childPosition);
			
			
			try {
				 		
					String content =(String) getChild(groupPosition, childPosition);
					name.setText(content);
			
			} catch (Exception e) {

				e.printStackTrace();
			}

			return convertView;

		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return titles[groupPosition];
		}


		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			// TODO Auto-generated method stub
			if(groupPosition==0){
				
				String sa=null;
				try {
					sa = searchArray.getString(childPosition);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return sa;
			}else{
				
				return historyList.get(childPosition).content;
			}
			
		}

	}

}
