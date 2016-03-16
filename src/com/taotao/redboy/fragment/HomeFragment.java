package com.taotao.redboy.fragment;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.taotao.redboy.R;
import com.taotao.redboy.activity.LogOutActivity;
import com.taotao.redboy.activity.NewproductActivity;
import com.taotao.redboy.activity.PersonCenterActivity;
import com.taotao.redboy.activity.SearchShowActivity;
import com.taotao.redboy.activity.SerachActivity;
import com.taotao.redboy.activity.UserCentreActivity;
import com.taotao.redboy.bean.HomeItemm;
import com.taotao.redboy.bean.RollItemBean;
import com.taotao.redboy.bean.RollItemBean.Itemdata;
import com.taotao.redboy.utils.UrlUtils;
import com.taotao.redboy.viewpager.RollPager;

public class HomeFragment extends BaseFragment {

	private static final int NEXTPIC = 0;

	@Override
	public View initView() {
		
		sp = getActivity().getSharedPreferences("config", 0);  
		
		isLog = sp.getBoolean(PersonCenterActivity.ISLOG, true);  
		
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);

		fragment_home_viewpager = (RollPager) view.findViewById(R.id.fragment_home_viewpager);

		fragment_home_list_view = (ListView) view
				.findViewById(R.id.fragment_home_list_view);

		fragment_home_btn_person = (Button) view
				.findViewById(R.id.fragment_home_btn_person);
		
		button_serach = (ImageView) view.findViewById(R.id.iv_search);  
		
		editText1 = (EditText) view.findViewById(R.id.et_search_input);  
		
		if(!isLog){
			
			fragment_home_btn_person.setText("个人中心");
			
		}else{
			
			fragment_home_btn_person.setText("登陆/注册");
			
		}

		initLinstener();
		
		mReceiver = new MyBroadCastReceiver();
		
		IntentFilter filter = new IntentFilter();
		
		filter.addAction("CHANGEUI");
		
		getActivity().registerReceiver(mReceiver, filter);
		
		return view;
	}

	private BitmapUtils bitMapUtils;

	@Override
	public void initData() {
		
		bitMapUtils = new BitmapUtils(getActivity());

		getDataFrmoServer();

		ListViewadapter = new MyListAdapter();

		ItemBeanList = new ArrayList<HomeItemm>();

		for (int i = 0; i < title.length; i++) {

			HomeItemm hm = new HomeItemm();

			hm.iv = ItemRe[i];

			hm.tv = title[i];

			ItemBeanList.add(hm);
			
		}

		ListViewadapter = new MyListAdapter();

		fragment_home_list_view.setAdapter(ListViewadapter);
		
	}
	/**
	 * ListView的条目名称
	 */
	private String[] title = { "限时抢购", "促销快报", "新品上架", "热门单品", "推荐品牌" };
	/**
	 * ListView的条目图片的资源
	 */
	private int[] ItemRe = { R.drawable.home_classify_01,
			R.drawable.home_classify_02, R.drawable.home_classify_03,
			R.drawable.home_classify_04, R.drawable.home_classify_05 };

	private MyListAdapter ListViewadapter;

	private ArrayList<HomeItemm> ItemBeanList;

	private class MyListAdapter extends BaseAdapter {

		private ViewHolder holder;

		@Override
		public int getCount() {
			return ItemBeanList.size();
		}

		@Override
		public Object getItem(int position) {
			return ItemBeanList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			HomeItemm homeItemm = ItemBeanList.get(position);

			if (convertView == null) {

				convertView = View.inflate(getActivity(), R.layout.home_item,
						null);

				holder = new ViewHolder();

				holder.tv = (TextView) convertView.findViewById(R.id.textView1);

				holder.im = (ImageView) convertView
						.findViewById(R.id.imageView1);

				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();

			}

			holder.im.setBackgroundResource(homeItemm.iv);

			holder.tv.setText(homeItemm.tv);

			return convertView;
		}
	}

	private class ViewHolder {

		public TextView tv;

		public ImageView im;
	}

	/**
	 * 从服务器获取数据
	 */
	private void getDataFrmoServer() {

		HttpUtils httpUtils = new HttpUtils();

		RequestParams params = new RequestParams();

		httpUtils.send(HttpMethod.GET,
				UrlUtils.BaseUrl+"/home", params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String data = responseInfo.result;

						ParserJson(data);

					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	};

	/**
	 * 轮播图片的URL集合
	 */
	private ArrayList<String> rollPicUrlList;
	/**
	 * ViewPager
	 */
	private RollPager fragment_home_viewpager;
	/**
	 * 解析Json
	 * 
	 * @param data
	 */
	private void ParserJson(String result) {

		rollPicUrlList = new ArrayList<String>();

		Gson gson = new Gson();

		RollItemBean ItemBean = gson.fromJson(result, RollItemBean.class);

		ArrayList<Itemdata> rollList = (ArrayList<Itemdata>) ItemBean.home_banner;

		for (int i = 0; i < rollList.size(); i++) {

			String url = rollList.get(i).pic;

			rollPicUrlList.add(UrlUtils.BaseUrl + url);

		}

		adapter = new Myadapter();

		fragment_home_viewpager.setAdapter(adapter);

		handler.sendEmptyMessageDelayed(NEXTPIC, 2000);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case NEXTPIC:

				fragment_home_viewpager.setCurrentItem((fragment_home_viewpager
						.getCurrentItem() + 1) % rollPicUrlList.size());

				handler.removeCallbacksAndMessages(null);

				handler.sendEmptyMessageDelayed(NEXTPIC, 2000);

				break;

			default:
				break;
			}
		}
	};

	private Myadapter adapter;

	private ListView fragment_home_list_view;
	/**
	 * 个人中心按钮
	 */
	private Button fragment_home_btn_person;
	private SharedPreferences sp;
	private ImageView button_serach;
	private EditText editText1;

	/**
	 * 开始转轮
	 */
	private class Myadapter extends PagerAdapter {

		@Override
		public int getCount() {
			return rollPicUrlList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView imageview = new ImageView(getActivity());

			container.addView(imageview);

			String string = rollPicUrlList.get(position);

			bitMapUtils.display(imageview, string);
			
			return imageview;

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {

			container.removeView((View) object);

		}
	}

	public void initLinstener() {

		fragment_home_list_view
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {

						switch (position) {
						case 0://限时抢购
							Intent intent2 = new Intent(getActivity(),NewproductActivity.class);
							startActivity(intent2);
							break;
						case 1://促销快报
							Intent intent5 = new Intent(getActivity(),NewproductActivity.class);
							startActivity(intent5);
//							Intent intent1 = new Intent(getActivity(),TopicActivity.class);
//							startActivity(intent1);
							break;
						case 2://新品上架
							Intent intent6 = new Intent(getActivity(),NewproductActivity.class);
							startActivity(intent6);
							break;
						case 3://热门单品
							Intent intent3 = new Intent(getActivity(),NewproductActivity.class);
							startActivity(intent3);
							break;
						case 4://推荐品牌
							Intent intent4 = new Intent(getActivity(),NewproductActivity.class);
							startActivity(intent4);
							break;
						default:
							break;
						}

					}
				});
		fragment_home_btn_person.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.fragment_home_btn_person:
					boolean isLog = sp.getBoolean(PersonCenterActivity.ISLOG, true);
					if(isLog){//说明还未登陆,所以直接进入登陆界面
						Intent intent = new Intent(getActivity(), PersonCenterActivity.class);
						startActivity(intent);
						sp.edit().putBoolean(PersonCenterActivity.ISLOG, false).commit();
					}else{//如果是已经登陆,那么直接跳到另一个Activity,显示注销
						Toast.makeText(getActivity(), "已经登录", 0).show();
						Intent intent = new Intent(getActivity(), UserCentreActivity.class);
						startActivity(intent);
					} 
					break;
				default:
					break;
				}
			}
		});
		button_serach.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				serachString = editText1.getText().toString().trim();
				
				if(!TextUtils.isEmpty(serachString)){
					
					Intent intent = new Intent(getActivity(),SearchShowActivity.class);
					
					intent.putExtra("serachString", serachString);
					
					startActivity(intent);
				}else{
					
					Toast.makeText(getActivity(), "搜索框不能为空哦!", 0).show();
					
				}
			}
		});
	}
	
	private MyBroadCastReceiver mReceiver;
	
	@Override
	public void onDestroy() {
		
		getActivity().unregisterReceiver(mReceiver);
		super.onDestroy();
	}
	
	private class MyBroadCastReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			
//			String username = intent.getStringExtra("username");//注册时候的用户名
//			
//			String userid = intent.getStringExtra("userid");//注册时候的ID
			
//			if(!isLog){
			
				System.out.println("收到广播"); 
				
				fragment_home_btn_person.setText("注销");
				
//			}else{
				
//				fragment_home_btn_person.setText("登陆/注册");
				
//			}
			
		}
	}
	
	
	private ProgressDialog pd;
	/**
	 * 搜索框填写的搜索关键字
	 */
	private String serachString;
	private boolean isLog;
}





