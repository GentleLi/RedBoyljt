package com.taotao.redboy.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.bean.OrderBean;
import com.taotao.redboy.bean.OrderBean.Order;
import com.taotao.redboy.res.HttpRes;

public class MyOrderActivity extends BaseActivity {
	/**
	 * 1=>近3分钟订单 2=>3分钟前订单 3=>已取消订单
	 */
	private int  current=1;
	public Context ctx;
	
	
	
	private final class OnItemClickListenerImplementation implements
			OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
				int position, long id) {
			//根据position获取当前页面的数据，传送过去差选详细清单，进行展示
			Order orderItem = orderBean.orderlist.get(position);
			//获得订单id
			String orderid=orderItem.orderid;
			
			Intent intent=new Intent(ctx,OrderContentActivity.class);
			intent.putExtra("orderid", orderid);
			startActivity(intent);
			
			
			
			
			
		}
	}
	private final class OnCheckedChangeListenerImplementation implements
			OnCheckedChangeListener {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.rb1:
				current=1;
				break;
			case R.id.rb2:
				current=2;
				break;
			case R.id.rb3:
				current=3;
				break;

			default:
				break;
			}
			//改变了
			
			System.out.println("当前的筛选类型 ："+current);
			
			requestData();
			//刷新列表
			adapter.notifyDataSetChanged();
			
		}
	}

	/**
	 * tab控件
	 */
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;
	@ViewInject(R.id.back)
	private LinearLayout back;
	@ViewInject(R.id.tv_user_centre)
	private TextView tv_user_centre;
	@ViewInject(R.id.tv_tab_logout)
	private TextView tv_tab_logout;
	
	
	//内容所需控件
	@ViewInject(R.id.rg_indent)
	private RadioGroup rg_indent;
	@ViewInject(R.id.lv_indent_show)
	private ListView lv_indent_show;
	private OrderBean orderBean;
	private MyOrderAdapter adapter;
	private String userid;
	

	@Override
	public void initView() {
		ctx=this;
		
		//注册控件
		ViewUtils.inject(this);
		
		//初始化标头
		tv_tab_name.setText("我的订单");
		
		back.setVisibility(View.GONE);
		
		tv_tab_logout.setVisibility(View.GONE);
		
		tv_user_centre.setVisibility(View.VISIBLE);
		
		
		//设置默认位置和默认的当前id
		current=1;
		
		rg_indent.check(R.id.rb1);
		
		
		
		//给定一个初始值
		
		

	}

	@Override
	public void initData() {
		
		//获取当前的用户id
		
		userid = getIntent().getStringExtra("userid");
		
		//System.out.println("用户id："+userid);
		
		requestData();
		
		
		
		
		
		
		

	}

	private void requestData() {
		//http://192.168.2.109/ECServicez8/orderlist?type=3&page=1&pageNum=10&userid=2
		
		//联网获取数据
		
		String url=HttpRes.base_uRL+"/orderlist?type="+current+"&page=1&pageNum=10&userid="+userid;
		
		RequestParams params=new RequestParams();
		getRequestData(url, HttpMethod.GET, params, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				//链接成功
				String result=arg0.result;

				//System.out.println("订单详情页面："+result);
				
				//解析数据
				praserJson(result);
				

			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				//链接错误
				arg0.printStackTrace();
			}
		});
	}
	/**
	 * 解析json数据
	 * @param result
	 */
	protected void praserJson(String result) {
		Gson gson=new Gson();
		
		orderBean = gson.fromJson(result, OrderBean.class);
		
		//填充到listview上
		
		adapter = new MyOrderAdapter();
		
		lv_indent_show.setAdapter(adapter);
		
		
	}

	@Override
	public void initListener() {
		rg_indent.setOnCheckedChangeListener(new OnCheckedChangeListenerImplementation());
		
		
		/**
		 * 条目点击数时间，点击进入订单的详情界面
		 */
		lv_indent_show.setOnItemClickListener(new OnItemClickListenerImplementation());
	
		
	}

	@Override
	public int getLayoutId() {
		return R.layout.my_indent_activity;
	}
	/**
	 * listview的适配器
	 */
	private class MyOrderAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return orderBean.orderlist.size();
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
			ViewHolder vh=null;
			if(convertView==null){
				vh=new ViewHolder();
				convertView=View.inflate(MyOrderActivity.this, R.layout.my_indent_activity_item, null);
				
				vh.orderid=(TextView)convertView.findViewById(R.id.tv_orderid);
				vh.tv_order_price=(TextView)convertView.findViewById(R.id.tv_order_price);
				vh.tv_order_state=(TextView)convertView.findViewById(R.id.tv_order_state);
				vh.tv_order_time=(TextView)convertView.findViewById(R.id.tv_order_time);
				
				convertView.setTag(vh);
			
			}else{
				vh=(ViewHolder) convertView.getTag();
			}
			
			Order order = orderBean.orderlist.get(position);
			
			//设置参数
			vh.orderid.setText(order.orderid);
			vh.tv_order_price.setText(order.price+"");
			vh.tv_order_state.setText(order.status+"");
			vh.tv_order_time.setText(order.time);
			
			
			
			return convertView;
		}


		
	}
	/**
	 * 优化listview使用的类
	 * @author zupp
	 *
	 */
	class ViewHolder{

		public TextView tv_order_time;
		public TextView tv_order_state;
		public TextView tv_order_price;
		public TextView orderid;
		
		
	}
	

}
