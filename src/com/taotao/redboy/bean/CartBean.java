package com.taotao.redboy.bean;

import java.util.List;

public class CartBean {

	
	public List<Cart> cart;
	public String response;
	public int totalCount;
	public int totalPoint;
	public double totalPrice;
	
	
	public class Cart{
		
		public int prodNum;
		public Product product;
		
		public class Product{
			public int id;
			public boolean isgift;
			public String name;
			public int number;
			public String pic;
			public double price;
			public String uplimit;
			
		}
	}
}
