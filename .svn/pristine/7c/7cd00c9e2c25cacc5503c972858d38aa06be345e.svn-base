package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.taotao.redboy.bean.AddressBean;
import com.taotao.redboy.bean.UserAddress;
import com.taotao.redboy.bean.AddressBean.Address;
import com.taotao.redboy.utils.APPRes;

public class AddAddressActivity extends BaseActivity {

	// 收货人
	@ViewInject(R.id.et_name)
	private EditText et_name;
	// /电话号码
	@ViewInject(R.id.et_tel)
	private EditText et_tel;
	// 省份
	@ViewInject(R.id.et_sheng)
	private EditText et_sheng;
	// 区
	@ViewInject(R.id.et_qu)
	private EditText et_qu;
	// 市
	@ViewInject(R.id.et_shi)
	private EditText et_shi;
	// 市
	@ViewInject(R.id.et_address_detail)
	private EditText et_address_detail;
	private Button btnHeadRight;
	private String name;
	private String tel;
	private String sheng;
	private String shi;
	private String qu;
	private String detail;
	private Button back;
	private TextView tvHead;
	private SharedPreferences sp;
	private DbUtils dbUtils;

	private int userId;

	@Override
	public void initView() {

		dbUtils = DbUtils.create(context, "addresses.db");
		sp = getSharedPreferences("config", MODE_PRIVATE);
		tvHead = (TextView) findViewById(R.id.tv_head);
		back = (Button) findViewById(R.id.back);
		back.setText("地址列表");
		btnHeadRight = (Button) findViewById(R.id.btn_head_right);
		btnHeadRight.setText("保存");
		tvHead.setText("地址列表");
		ViewUtils.inject(this);
	}

	@Override
	public void initData() {

	}

	@Override
	public void initLinstener() {
		back.setOnClickListener(this);
		btnHeadRight.setOnClickListener(this);
	}

	@Override
	public void initOtherButton(View v) {

		switch (v.getId()) {
		case R.id.btn_head_right:

			checkInfoFormat();

			break;
		case R.id.back:

			finish();
			break;
		}
	}

	private void checkInfoFormat() {
		name = et_name.getText().toString().trim();
		tel = et_tel.getText().toString().trim();
		sheng = et_sheng.getText().toString().trim();
		shi = et_shi.getText().toString().trim();
		qu = et_qu.getText().toString().trim();
		detail = et_address_detail.getText().toString().trim();
		System.out.println("姓名：" + name + "电话：" + tel + "省份：" + sheng + "市："
				+ shi + "详细" + detail);

		/**
		 * 先进行非空判断
		 */

		if (TextUtils.isEmpty(name) || TextUtils.isEmpty(tel)
				|| TextUtils.isEmpty(detail)) {
			/**
			 * 上传信息到服务器，并保存到本地
			 */
			Toast.makeText(context, "数据为空", 0).show();
			return;
		}
//		saveInfoToDb();
		uploadInfoToServer();
		Toast.makeText(context, "提交成功", 0).show();
	}

	private void uploadInfoToServer() {

		/**
		 * String name = request.getParameter("name"); String phoneNumber =
		 * request.getParameter("phoneNumber"); String addressDetail =
		 * request.getParameter("addressDetail"); String fixedtel =
		 * request.getParameter("fixedtel"); String zipCode =
		 * request.getParameter("zipCode"); String province =
		 * request.getParameter("province"); String city =
		 * request.getParameter("city"); String area =
		 * request.getParameter("area");
		 */

		// /addresssave?userId=23&name=23987&phoneNumber=274698&province=北京市&city=北京市&area="海淀区"&addressDetail=iuhfeau
		HttpUtils httpUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addBodyParameter("name", name);
		params.addBodyParameter("phoneNumber", tel);
		params.addBodyParameter("city", shi);
		params.addBodyParameter("province", sheng);
		params.addBodyParameter("area", qu);
		params.addBodyParameter("addressDetail", detail);

		String userInfo = sp.getString("userid", "-1");

		String[] split = userInfo.split("#");

		userId = Integer.valueOf(split[0]);

		params.addBodyParameter("userId", "" + userId);

		httpUtils.send(HttpMethod.POST, APPRes.BASE_URL
				+ APPRes.address_save_url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						System.out.println("请求成功:" + responseInfo.result);

						getAddressFromServer();
					}

					@Override
					public void onFailure(HttpException error, String msg) {

						System.out.println("请求失败:" + msg);
					}
				});

	}

	protected void getAddressFromServer() {

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

	private ArrayList<Address> addresslist = new ArrayList<AddressBean.Address>();

	private ArrayList<Address> myAddresses = new ArrayList<Address>();
	private ArrayList<UserAddress> userAddresses = new ArrayList<UserAddress>();

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
		Address address = null;
		if (addresslist != null) {
			for (int i = 0; i < addresslist.size(); i++) {
				address = addresslist.get(i);
				// TODO

				if (address.userId == userId) {
					myAddresses.add(address);
					UserAddress userAddress = new UserAddress();

					userAddress.id = address.id;
					userAddress.area = address.area;
					userAddress.addressDetail = address.addressDetail;
					userAddress.userId = address.userId;
					userAddress.city = address.city;
					userAddress.name = address.name;
					userAddress.phoneNumber = address.phoneNumber;
					userAddress.province = address.province;
					userAddresses.add(userAddress);
				}
			}
		}
		System.out.println(addresses.addresslist.get(0).addressDetail);
		// 获取数据后，刷新listview列表
		if (userAddresses.size() > 0) {
			try {
				dbUtils.saveAll(userAddresses);
				Toast.makeText(context, "保存地址成功", 0).show();
			} catch (DbException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 存储用户信息到数据库
	 */
	/*
	private void saveInfoToDb() {
		UserAddress address = new UserAddress();

		address.userId = sp.getInt("userId", 0);
		// TODO
		address.id = address.userId;
		address.addressDetail = detail;
		address.city = shi;
		address.province = sheng;
		address.area = qu;
		address.name = name;
		address.phoneNumber = tel;
		try {
			dbUtils.save(address);

			System.out.println("保存成功");
		} catch (DbException e) {

			e.printStackTrace();
		}
	}*/

	@Override
	public int getLayout() {
		return R.layout.add_address;
	}

}
