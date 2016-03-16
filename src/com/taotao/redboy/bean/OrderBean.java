package com.taotao.redboy.bean;

import java.util.List;

public class OrderBean {
	
	public List<Order> orderlist;
	
	public class Order{
		public String flag;//订单表示
		
		public String orderid;//订单id
		
		public String paymenttype;//支付方式
		
		public long price;//产品价格
		
		public String status;//显示状态
		
		public String time;//订单时间
		
	}

}
