package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.bean.SearchShowBean;
import com.taotao.redboy.bean.SearchShowBean.Product;
import com.taotao.redboy.res.HttpRes;

public class SearchShowActivity extends BaseActivity {

	private ArrayList<Product> dataList = new ArrayList<SearchShowBean.Product>();

	public BitmapUtils bitmaputils;

	//private String BASEURL = "http://192.168.2.109/ECServicez8";

	private ProgressDialog pd;

	/**
	 * 获得tab的控件
	 */
	@ViewInject(R.id.tv_tab_name)
	public TextView tv_tab_name;
	@ViewInject(R.id.lv_search_show)
	public ListView lv_search_show;
	@ViewInject(R.id.ll_unshow)
	public LinearLayout ll_unshow;
	@ViewInject(R.id.ll_listview_menu)
	public LinearLayout ll_listview_menu;

	/**
	 * 筛选菜单
	 */
	@ViewInject(R.id.btn_matket)
	private RadioButton btn_matket;
	@ViewInject(R.id.btn_price)
	private RadioButton btn_price;
	@ViewInject(R.id.btn_comment)
	private RadioButton btn_comment;
	@ViewInject(R.id.btn_time)
	private RadioButton btn_time;

	public Context ctx;

	private String input;

	@Override
	public void initView() {

		bitmaputils = new BitmapUtils(this);

		pd = new ProgressDialog(this);

		pd.setMessage("正在拼命加载中，请稍等！！");

		ctx = this;

		// 注册控件
		ViewUtils.inject(this);

		tv_tab_name.setText("搜索结果(0)");

	}

	/**
	 * 初始化娿控件需要显示的部分
	 */
	private void initLayoutShow() {

		pd.dismiss();

		int searchResult = dataList.size();

		tv_tab_name.setText("搜索结果(" + searchResult + ")");

		if (searchResult != 0) {
			ll_unshow.setVisibility(View.GONE);

			lv_search_show.setVisibility(View.VISIBLE);
			ll_listview_menu.setVisibility(View.VISIBLE);
		} else {
			ll_unshow.setVisibility(View.VISIBLE);

			lv_search_show.setVisibility(View.GONE);
			ll_listview_menu.setVisibility(View.GONE);
		}

	}

	@Override
	public void initData() {
		// 获得数据

		input = getIntent().getStringExtra("name");

		// 联网获取数据
		requestData(finalUrl);

		// 设置监听事件

		regListener();

	}

	/**
	 * 各种监听事件
	 */
	private void regListener() {
		btn_matket.setOnClickListener(this);
		btn_price.setOnClickListener(this);
		btn_comment.setOnClickListener(this);
		btn_time.setOnClickListener(this);

	}

	@Override
	public void initListener() {

		// 获得数据后设置adapter

		adapter = new MySearchAdapter();

		lv_search_show.setAdapter(adapter);
		
		//设置监听
		lv_search_show.setOnItemClickListener(new OnItemClickListenerImplementation());

	}

	@Override
	public int getLayoutId() {
		return R.layout.search_show_activity;
	}

	private final class OnItemClickListenerImplementation implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			//获得点击商品的bean
			Product product = dataList.get(position);
			
			
			Intent intent = new Intent();
			
			intent.putExtra("detailName", product.name);
			intent.putExtra("detailCommentcount", 200+"");
			intent.putExtra("detailMarketprice", product.marketprice+"");
			System.out.println("市场价格："+product.marketprice);
			intent.putExtra("detailNumber", 150+"");
			intent.putExtra("detailPic", product.pic);
			intent.putExtra("detailId", product.id+"");
			
			System.out.println("商品id："+product.id);
			intent.setClass(ctx, DetailActivity.class);
			
			startActivity(intent);
			
			
			
		}
	}

	/**
	 * listview的是适配器
	 */
	public class MySearchAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder vh = null;
			if (convertView == null) {
				vh = new ViewHolder();
				convertView = View.inflate(ctx,
						R.layout.search_show_activity_item, null);

				vh.icon = (ImageView) convertView
						.findViewById(R.id.iv_product_icon);
				vh.name = (TextView) convertView
						.findViewById(R.id.tv_product_name);
				vh.price = (TextView) convertView
						.findViewById(R.id.tv_product_price);
				vh.comment = (TextView) convertView
						.findViewById(R.id.tv_product_comment);
				vh.price2 = (TextView) convertView
						.findViewById(R.id.tv_product_prices);

				convertView.setTag(vh);

			} else {
				vh = (ViewHolder) convertView.getTag();
			}
			// 为控件赋值
			Product productbean = dataList.get(position);

			vh.name.setText(productbean.name);

			vh.price.setText("￥" + productbean.marketprice);

			vh.price2.setText("￥" + productbean.price);

			vh.comment.setText("已有300人评论");

			bitmaputils.display(vh.icon, HttpRes.base_uRL + productbean.pic);

			return convertView;
		}

	}

	class ViewHolder {

		public TextView price2;
		public TextView comment;
		public TextView price;
		public TextView name;
		public ImageView icon;

	}

	/**
	 * 处理点击事件的方法
	 * 
	 */
	private boolean isClick_btn_price = true;

	private String orderbyStr = "saleDown";

	private String finalUrl = HttpRes.base_uRL + "/search?keyword=" + input
			+ "&page=1&pageNum=10&orderby=" + orderbyStr;

	@Override
	public void onInntClick(View v) {
		super.onInntClick(v);

		switch (v.getId()) {
		// 拼接不同的url，并刷新这好像是个耗时的操作
		case R.id.btn_matket:// 销量
			orderbyStr = "saleDown";
			break;
		case R.id.btn_price:// 价格
			if (isClick_btn_price) {

				orderbyStr = "priceUp";
			} else {
				orderbyStr = "priceDown";
			}
			isClick_btn_price = !isClick_btn_price;
			// priceDown
			break;
		case R.id.btn_comment:// 好评降序
			orderbyStr = "commentDown";
			break;
		case R.id.btn_time:// 事件
			orderbyStr = "shelvesDown";
			break;

		default:
			break;
		}

		// 需要查询的url

		// System.out.println("删选条件 ："+orderbyStr);

		// 联网获取数据
		requestData(finalUrl);
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String json = (String) msg.obj;
			// System.out.println("我是筛选点击的"+json);
			parserJson(json);

			// 初始化界面布局的现实
			initLayoutShow();

		}

	};

	private ArrayList<Product> showList;

	private MySearchAdapter adapter;

	/**
	 * 联网强求数据
	 * 
	 * @param finalUrl
	 */
	private void requestData(final String finalUrl) {

		// 开始请求的时候开启一个进度
		pd.show();

		new Thread() {
			public void run() {
				// 开一个子线程联网操作获取数据

				HttpUtils httputils = new HttpUtils();

				httputils.send(HttpMethod.GET, finalUrl,
						new RequestCallBack<String>() {

							@Override
							public void onFailure(HttpException arg0,
									String arg1) {

								// 初始化界面布局的现实
								initLayoutShow();

							}

							@Override
							public void onSuccess(ResponseInfo<String> arg0) {
								// 解析数据封装为bean更新数据
								String json = arg0.result;

								// 解析json

								// 获取数据后发送
								Message message = handler.obtainMessage();
								message.obj = json;
								handler.sendMessage(message);

							}
						});
			}
		}.start();
	}

	/**
	 * 解析json数据
	 * 
	 * @param json
	 */
	private void parserJson(String json) {

		dataList.clear();

		Gson gson = new Gson();

		SearchShowBean searchShowBean = gson.fromJson(json,
				SearchShowBean.class);

		showList = searchShowBean.productlist;

		dataList.addAll(showList);

		adapter.notifyDataSetChanged();

	}

}
