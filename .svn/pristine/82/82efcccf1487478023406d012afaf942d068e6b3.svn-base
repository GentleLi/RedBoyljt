package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.taotao.redboy.R;
import com.taotao.redboy.bean.AddressBean;
import com.taotao.redboy.bean.UserAddress;
import com.taotao.redboy.bean.AddressBean.Address;
import com.taotao.redboy.utils.APPRes;

public class AddressListActivity extends BaseActivity {

	private ListView listView;
	private ArrayList<Address> addresslist = new ArrayList<AddressBean.Address>();

	
	@Override
	public void initView() {
		tvHead = (TextView) findViewById(R.id.tv_head);
		listView = (ListView) findViewById(R.id.list_view);
		btnHeadRight = (Button) findViewById(R.id.btn_head_right);
		tvHead.setText("地址列表");
		back = (Button) findViewById(R.id.back);
		back.setText("结算中心");
		btnHeadRight.setText("新增地址");
		sp = getSharedPreferences("config", MODE_PRIVATE);
		
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_head_right:
			Intent intent=new Intent(this,AddAddressActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	

	@Override
	public void initData() {
		
		dbUtils = DbUtils.create(context, "addresses.db");
		String userInfo = sp.getString("userid", "-1");

		String[] split = userInfo.split("#");

		userId = Integer.valueOf(split[0]);
		
		HttpUtils httpUtils = new HttpUtils();

		httpUtils.send(HttpMethod.GET, APPRes.BASE_URL
				+ APPRes.address_list_url, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				System.out.println("AddressListActivity请求成功:"
						+ responseInfo.result);
				parseJson(responseInfo.result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				System.out.println("AddressListActivity请求失败:" + msg);
			}
		});

	}

	/**
	 * 解析json数据
	 */
	
	private ArrayList<Address> myAddresses=new ArrayList<Address>();
	private ArrayList<UserAddress> userAddresses=new ArrayList<UserAddress>();
	protected void parseJson(String result) {
		/*
		 * {"addresslist":[{"addressDetail":"文化路农业路","area":"深圳市","areaId":9,"city"
		 * :
		 * "青岛二区","cityId":18,"id":10,"name":"taotao","phoneNumber":"1352682849"
		 * ,
		 * "province":"广州市","provinceId":6,"userId":25}],"request":"addresslist"
		 * }
		 */

		Gson gson = new Gson();

		AddressBean addresses = gson.fromJson(result, AddressBean.class);

		addresslist = (ArrayList<Address>) addresses.addresslist;
		myAddresses.clear();
		Address address=null;
		if (addresslist!=null) {
			for (int i = 0; i < addresslist.size(); i++) {
				address=addresslist.get(i);
				//TODO
				
				if (address.userId==userId) {
					myAddresses.add(address);
					UserAddress userAddress=new UserAddress();
					
					userAddress.id=address.id;
					userAddress.area=address.area;
					userAddress.addressDetail=address.addressDetail;
					userAddress.userId=address.userId;
					userAddress.city=address.city;
					userAddress.name=address.name;
					userAddress.phoneNumber=address.phoneNumber;
					userAddress.province=address.province;
					userAddresses.add(userAddress);
				}
			}
		}
		System.out.println(addresses.addresslist.get(0).addressDetail);
		// 获取数据后，刷新listview列表
		adapter.notifyDataSetChanged();
		if (userAddresses.size()>0) {
			try {
				dbUtils.saveAll(userAddresses);
				Toast.makeText(context, "保存地址成功", 0).show();
			} catch (DbException e) {
				e.printStackTrace();
			}
		}
		
	}

	private int checkedPosition;
	
	@Override
	public void initLinstener() {
		back.setOnClickListener(this);
		listView.setAdapter(adapter);
		btnHeadRight.setOnClickListener(this);
		listView.setOnItemClickListener(new OnItemClickListener() {

			

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				checkedPosition=position;
				
				Address address = (Address) parent.getItemAtPosition(position);
				
				int addressId = address.id;
				//将userId保存
				sp.edit().putInt("addressId", addressId).commit();
				adapter.notifyDataSetChanged();
				
			}
		});
	}

	private MyAdapter adapter = new MyAdapter();
	private Button btnHeadRight, back;
	private SharedPreferences sp;
	private TextView tvHead;
	private int userId;
	private DbUtils dbUtils;;

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return myAddresses.size();
		}

		@Override
		public Object getItem(int position) {
			return myAddresses.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context, R.layout.address_list_item,
						null);
				
				holder.tvAddress = (TextView) convertView
						.findViewById(R.id.tv_address);
				holder.tvTel = (TextView) convertView.findViewById(R.id.tv_tel);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.tv_name);
				holder.ivMark = (ImageView) convertView.findViewById(R.id.iv_mark);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			Address address = myAddresses.get(position);

			String province = address.province;
			String city = address.city;
			String area = address.area;
			holder.tvAddress.setText(address.addressDetail);
			/**
			 * 是点击条目设置为选中
			 */
			if (checkedPosition==position) {
				holder.ivMark.setVisibility(View.VISIBLE);
			}else {
				holder.ivMark.setVisibility(View.GONE);
			}

			holder.tvName.setText(address.name);

			holder.tvTel.setText(address.phoneNumber);
			return convertView;
		}

	}

	private class ViewHolder {
		ImageView ivMark;
		TextView tvName, tvTel, tvAddress;

	}

	@Override
	public int getLayout() {
		return R.layout.address_list;
	}

}
