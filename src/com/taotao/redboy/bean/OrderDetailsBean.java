package com.taotao.redboy.bean;

import java.util.List;

public class OrderDetailsBean {

	public OrderDetailsBean(Address address_info, String delivery_info,
			String flag, String orderid, String payment_info, String price,
			String productlist, String status, String time, String userid) {
		super();
		this.address_info = address_info;
		this.delivery_info = delivery_info;
		this.flag = flag;
		this.orderid = orderid;
		this.payment_info = payment_info;
		this.price = price;
		this.productlist = productlist;
		this.status = status;
		this.time = time;
		this.userid = userid;
	}

	public OrderDetailsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 地址对应的id
	 */
	public Address address_info;// 地址对应的id

	public static class Address {
		
		public Address(String addressDetail, String id, String name,
				String phoneNumber, String zipCode) {
			super();
			this.addressDetail = addressDetail;
			this.id = id;
			this.name = name;
			this.phoneNumber = phoneNumber;
			this.zipCode = zipCode;
		}
		
		public Address() {
			super();
		}

		public String addressDetail;
		public String id;
		public String name;
		public String phoneNumber;
		public String zipCode;

	}

	/**
	 * 送货情
	 */
	public String delivery_info;// 送货情况
	/**
	 * 暂时不知道干啥用
	 */
	public String flag;// 暂时不知道干啥用
	/**
	 * 商品id
	 */
	public String orderid;// 商品id
	/**
	 * 支付方式
	 */
	public String payment_info;
	/**
	 * 商品价格
	 */
	public String price;

	/**
	 * 商品信息
	 */
	public String productlist;
	/**
	 * 交易状态
	 */
	public String status;
	/**
	 * 时间
	 */
	public String time;
	/**
	 * 当前用户id
	 */
	public String userid;
	
	/**
	 * 发票抬头
	 */
	public String invoice_info;
}
