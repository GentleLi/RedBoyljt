package com.taotao.redboy.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;
@Table(name="addresses")
public class UserAddress {

	@NoAutoIncrement
	@Column(column="_id")
	public int id;
	public String addressDetail;
	public String area;//区域
	public String name;//收货人姓名
	public String phoneNumber;//收货人电话号码
	public int userId;//收货人id
	public String province;//省份
	public String city;//城市
	@Override
	public String toString() {
		return "UserAddress [id=" + id + ", addressDetail=" + addressDetail
				+ ", area=" + area + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", userId=" + userId + ", province=" + province
				+ ", city=" + city + "]";
	}
	
	
}
