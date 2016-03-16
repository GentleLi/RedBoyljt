package com.taotao.redboy.bean;

import java.util.ArrayList;
import java.util.List;

import android.widget.ImageView;

public class RollItemBean {

	public String response;
	/**
	 * 轮播图的集合s
	 */
	public List <Itemdata> home_banner;
	
	public class Itemdata{
		
		/**
		 * 轮播图的ID
		 */
		public String id;
		/**
		 * 轮播图的URL
		 */
		public String pic;
		/**
		 * 轮播图的title
		 */
		public String title;
		
	}
}
