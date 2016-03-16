package com.taotao.redboy.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.bean.NewProductBean;
import com.taotao.redboy.bean.NewProductBean.Product;
import com.taotao.redboy.bean.NewProductItemBean;
import com.taotao.redboy.utils.UrlUtils;

public class NewproductActivity extends BaseActivity {
	
	protected static final int TEST = 0;

	@ViewInject(R.id.back)
	private ImageView back;

	@ViewInject(R.id.button_price)
	private Button button_price;
	
	@ViewInject(R.id.newproduct_lv)
	private ListView newproduct_lv;
	
	@Override
	public void initView() {
		
		ViewUtils.inject(this);
		
	}

	@Override
	public void initData() {
		
		//联网获取数据
		GetDataFromServer();
		
	}

	@Override
	public void initLinstener() {
		
		back.setOnClickListener(this);
		
		button_price.setOnClickListener(this);
		
		newproduct_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				//TODO 跳转到商品详情,加上需要的东西并且加上提交订单的按钮
				
				NewProductItemBean newProductItemBean = productList.get(position);
				
				String detailName = newProductItemBean.name;//名称
				String detailCommentcount = newProductItemBean.commentcount;//评论数量
				String detailMarketprice = newProductItemBean.marketprice;//市场价
				String detailNumber = newProductItemBean.number;//剩余数量
				String detailPic = newProductItemBean.pic;//图片路径
				String detailId = newProductItemBean.id;
				
				Intent intent = new Intent();
				
				intent.putExtra("detailName", detailName);
				intent.putExtra("detailCommentcount", detailCommentcount);
				intent.putExtra("detailMarketprice", detailMarketprice);
				intent.putExtra("detailNumber", detailNumber);
				intent.putExtra("detailPic", detailPic);
				intent.putExtra("detailId", detailId);
				
				intent.setClass(context, DetailActivity.class);
				
				startActivity(intent);
				
			}
		});
		
	}

	@Override
	public int getLayout() {
		
		return R.layout.activity_newproduct;
		
	}
	
	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		switch (v.getId()) {
		case R.id.button_price://按照价格排序
			SortByPrice();
			break;
		case R.id.back://返回
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * 按照价格排序的方法
	 */
	private void SortByPrice() {
		
	}

	private ProgressDialog pd;
	
	/**
	 * 从服务器获取数据
	 */
	private void GetDataFromServer() {
		pd = new ProgressDialog(context);
		pd.setMessage("正在努力加载中!");
		pd.show();
		new Thread(){
			public void run() {
				
				HttpUtils httpUtils = new HttpUtils();
				
				httpUtils.send(HttpMethod.GET, UrlUtils.BaseUrl+"/newproduct?pager=1&pageNum=2&orderby=priceDown&searchkey=玩具",  new RequestCallBack<String>() {

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
		handler.sendEmptyMessageDelayed(TEST, 2000);
	}
	/**
	 * Handler
	 */
	private Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case TEST:
				pd.dismiss();
				break;

			default:
				break;
			}
		}
	};
	
	/**
	 * 解析Json
	 * @param str
	 */
	private void ParserJson(String str) {
		
		System.out.println("已经进入ParserJson");
		
		Gson gson = new Gson();
		
		NewProductBean newsProductBean = gson.fromJson(str, NewProductBean.class); 
		
		productList = new ArrayList<NewProductItemBean>();
		
		for (int i = 0; i < newsProductBean.productlist.size(); i++) {
			
			NewProductItemBean ProductItemBean = new NewProductItemBean();
			
			ProductItemBean.marketprice =  newsProductBean.productlist.get(i).marketprice;
			ProductItemBean.id =  newsProductBean.productlist.get(i).id;
			ProductItemBean.name =  newsProductBean.productlist.get(i).name;
			ProductItemBean.pic =  newsProductBean.productlist.get(i).pic;
			ProductItemBean.sales =  newsProductBean.productlist.get(i).sales;
			ProductItemBean.price =  newsProductBean.productlist.get(i).price;
			productList.add(ProductItemBean);
			
			adapter = new MyAdapter();
			
			newproduct_lv.setAdapter(adapter);
			
		}
	}
	
	private MyAdapter adapter;
	
	private ArrayList<NewProductItemBean>productList;
	
	private class MyAdapter extends BaseAdapter{

		private View view;
		private ViewHolder holder;
		private ImageView findViewById;
		private TextView textid;

		@Override
		public int getCount() {
			return productList.size();
		}

		@Override
		public Object getItem(int position) {
			return productList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 
			if(convertView == null){
				
				convertView = View.inflate(NewproductActivity.this, R.layout.newproductitem, null);
				holder = new ViewHolder();
				holder.im = (ImageView) convertView.findViewById(R.id.newproduct_im);
				holder.name = (TextView) convertView.findViewById(R.id.textView1);
				holder.price = (TextView) convertView.findViewById(R.id.textView2);
				holder.textid = (TextView) convertView.findViewById(R.id.textid);
				
				convertView.setTag(holder); 
				
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			NewProductItemBean newProductItemBean = productList.get(position);
			
			BitmapUtils bitmapUtils = new BitmapUtils(context);
			bitmapUtils.display(holder.im , UrlUtils.BaseUrl+newProductItemBean.pic);
			
			holder.name.setText("商品名:"+newProductItemBean.name);
			holder.price.setText("市场价:"+newProductItemBean.price);
			holder.textid.setText("商品ID:"+newProductItemBean.id);
			return convertView;
		}
	}
	
	private class ViewHolder{
		
		public ImageView im;
		public TextView name;
		public TextView price;
		public TextView textid;
	}
}


















