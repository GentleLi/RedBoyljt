package com.taotao.redboy.bean;

public class NewProductItemBean {

	public String id;
	/**
	 * 市场价
	 */
	public String marketprice;
	/** 
	 * 商品名称
	 */
	public String name;
	/**
	 * 图片URL路径
	 */
	public String pic;
	/**
	 * 标价
	 */
	public String price;
	/**
	 * 原价
	 */
	public String sales;
	/**
	 * 评论数量
	 */
	public String commentcount;
	/**
	 * 剩余数量
	 */
	public String number;
	/**
	 * 商品描述
	 */
	public String productdesc;
	
	public String toString(){
		
		return "商品名称:"+name+"商品市场价格:"+marketprice+"图片URL路径:"+pic+"商品原价:"+sales;
	}
}
