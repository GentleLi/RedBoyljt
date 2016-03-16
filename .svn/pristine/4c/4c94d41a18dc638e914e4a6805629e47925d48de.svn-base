package com.taotao.redboy.activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.base.BaseActivity;
import com.taotao.redboy.bean.OrderDetailsBean;
import com.taotao.redboy.res.HttpRes;

/**
 * 订单详情页面
 * 
 * @author zupp
 * 
 */
public class OrderContentActivity extends BaseActivity {
	
	/**
	 * 地址信息所需控件
	 */
	@ViewInject(R.id.tv_order_num)
	private TextView tv_order_num;
	@ViewInject(R.id.tv_name)
	private TextView tv_name;
	@ViewInject(R.id.tv_phone)
	private TextView tv_phone;
	@ViewInject(R.id.tv_address)
	private TextView tv_address;
	
	/**
	 * 订单详情控件
	 */
	@ViewInject(R.id.tv_ordre_state)
	private TextView tv_ordre_state;
	@ViewInject(R.id.tv_delivery)
	private TextView tv_delivery;
	@ViewInject(R.id.tv_pay)
	private TextView tv_pay;
	@ViewInject(R.id.tv_start_time)
	private TextView tv_start_time;
	@ViewInject(R.id.tv_out_time)
	private TextView tv_out_time;
	@ViewInject(R.id.tv_is_invoice)
	private TextView tv_is_invoice;
	@ViewInject(R.id.tv_invoice_head)
	private TextView tv_invoice_head;
	@ViewInject(R.id.tv_ask)
	private TextView tv_ask;

	
	
	

	
	

	@Override
	public void initView() {
		
		//注册控件
		ViewUtils.inject(this);
		

	}

	@Override
	public void initData() {
		// 收到订单列表页面穿过来的订单id
		String orderid = getIntent().getStringExtra("orderid");

		String url = HttpRes.base_uRL + "/orderdetail?orderid=" + orderid;
		RequestParams params = new RequestParams();
		// 根据id请求商品列表
		getRequestData(url, HttpMethod.GET, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String result = arg0.result;

						System.out.println("订单详情："+result);

						// 解析第一个总的json

						parserJson(result);

					}
					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}
				});

	}

	/**
	 * 解析订单的详情的json
	 * 
	 * @param result
	 */
	protected void parserJson(String result) {

		// 解析订单详情json
		packagingBean(result);
		
		//设置地址信息
		setAddressInfo();
		
		setOrderInfo();
		
		
		
		

	}
	/**
	 * 设置订单详情信息
	 * 
	 */
	
	private void setOrderInfo() {
		
		tv_ordre_state.setText(od.status);
		tv_delivery.setText("快递");
		tv_pay.setText(od.payment_info);
		tv_start_time.setText(od.time);
		tv_out_time.setText(od.time);
		
		//判断是否需要开发票
		
		
		if(od.invoice_info==null){
			tv_is_invoice.setText("否");
			tv_invoice_head.setText("无");
		}else{
			tv_is_invoice.setText("是");
			tv_invoice_head.setText(od.invoice_info);
		}
		tv_ask.setText(od.delivery_info);
	}

	/**
	 * 设置地址信息
	 */
	private void setAddressInfo() {
		tv_phone.setText(od.address_info.phoneNumber);
		tv_name.setText(od.address_info.name);
		tv_order_num.setText(od.address_info.id);
		tv_address.setText(od.address_info.addressDetail);

		
	}

	private OrderDetailsBean od;
	/**
	 * 打包订单详情json为bean
	 * @param result
	 */
	private void packagingBean(String result) {
		
		
		

		od=new OrderDetailsBean();
		
		// 使用普通的解析

		try {
			JSONObject jo = new JSONObject(result);
			JSONObject jsonOrder = jo.getJSONObject("order_info");
			
		
			

			
			/**
			 * 订单详情
			 */
			od.delivery_info = jsonOrder.getString("delivery_info");
			od.flag = jsonOrder.getString("flag");
			od.orderid = jsonOrder.getString("orderid");
			od.payment_info = jsonOrder.getString("payment_info");
			od.price = jsonOrder.getString("price");
			od.productlist = jsonOrder.getString("productlist");
			od.status = jsonOrder.getString("status");
			od.time = jsonOrder.getString("time");
			od.userid = jsonOrder.getString("userid");
			od.invoice_info=jsonOrder.getString("invoice_info");
			
			/**
			 * 解析地址bean
			 */
			JSONObject addressObject = jo.getJSONObject("address_info");
			
			String addressDetail=addressObject.getString("addressDetail");
			String id=addressObject.getString("id");
			String name=addressObject.getString("name");
			String phoneNumber=addressObject.getString("phoneNumber");
			String zipCode=addressObject.getString("zipCode");
			
			
			od.address_info=new OrderDetailsBean.Address(addressDetail, id, name, phoneNumber,zipCode);

			//取出一个信息看看
			
			//System.out.println("地址："+od.address_info.addressDetail);
			
			
			
			
		 
		 

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initListener() {

	}

	@Override
	public int getLayoutId() {
		return R.layout.ordre_content_activity;
	}

}
