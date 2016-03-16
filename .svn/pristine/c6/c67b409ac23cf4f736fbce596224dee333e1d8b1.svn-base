package com.taotao.redboy.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.NoAutoIncrement;
import com.lidroid.xutils.db.annotation.Table;

@Table(name="goods")
public class GoodsBean {
	
	@NoAutoIncrement
	@Column(column="_id")
	public int id;//商品id
	public int userId;//用户id
	public String name;//商品名称
	public int number;//商品数量
	public int price;//市场价
	public int vipPrice;//会员价
	public String size;//商品尺寸
	public String url;
	public GoodsBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GoodsBean(int id, int userId, String name, int number,
			int price, int vipPrice, String size,String url) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
		this.number = number;
		this.price = price;
		this.vipPrice = vipPrice;
		this.size = size;
		this.url=url;
	}
	
	
}
