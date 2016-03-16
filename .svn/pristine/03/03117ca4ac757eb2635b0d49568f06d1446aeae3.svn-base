package com.taotao.redboy.bean;

import java.util.List;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

public class AddressBean {

	
	public List<Address> addresslist;
	public String request;
	
	public class Address{
		/**
		 * 地址详情
		 */
		public int id;
		public String addressDetail;
		public String area;//区域
		public String name;//收货人姓名
		public String phoneNumber;//收货人电话号码
		public int userId;//收货人id
		public String province;//省份
		
		public String city;//城市
		
	}
	public AddressBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddressBean(List<Address> addresslist, String request) {
		super();
		this.addresslist = addresslist;
		this.request = request;
	}
	
	
	
	/*public int provinceId;
	public int areaId;
	public int userId;
	public int cityId;*/
}
