package com.taotao.redboy.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.taotao.redboy.R;
import com.taotao.redboy.bean.BrandBean;
import com.taotao.redboy.bean.BrandBean.Brand.Value;
import com.taotao.redboy.res.HttpRes;

public class BrandAdapter extends BaseExpandableListAdapter {

	private BrandBean datas;
	private Context ctx;
	private ArrayList<Value> values;
	
	private BitmapUtils bitmapU;

	public BrandAdapter(BrandBean brandBean,Context ctx) {
		this.datas = brandBean;
		this.ctx=ctx;
	}

	@Override
	public int getGroupCount() {
		return datas.brand.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return datas.brand.get(groupPosition).value.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		bitmapU=new BitmapUtils(ctx);
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = View
					.inflate(ctx, R.layout.brand_item, null);
			vh.tv = (TextView) convertView.findViewById(R.id.brandParent);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		vh.tv.setText(datas.brand.get(groupPosition).key);

		return convertView;
	}

	@Override
	/**
	 * 设置儿子
	 */
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			vh = new ViewHolder();
			convertView = View.inflate(ctx,
					R.layout.brand_child_list, null);
			vh.brandGv = (GridView) convertView.findViewById(R.id.brandGv);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		
		values = datas.brand.get(groupPosition).value;
	
		// 设置每个产品的适配器
				
				MyGvAdapter adapter=new MyGvAdapter(values);
				
				vh.brandGv.setAdapter(adapter);



		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean islast=true;
	
	class ViewHolder {

		public GridView brandGv;
		public TextView tv;
		public ImageView childtv;

	}
	
	public class MyGvAdapter extends BaseAdapter{
		
		
		
		private ArrayList<Value> values;

		public MyGvAdapter(ArrayList<Value> values){
			this.values=values;
		}

		@Override
		public int getCount() {
			//System.out.println("zzzzzzzz:"+values.size());
			return values.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder vh=null;
			if(convertView==null){
				vh=new ViewHolder();
				
				convertView=View.inflate(ctx, R.layout.brand_child_item, null);
				vh.childtv=(ImageView)convertView.findViewById(R.id.iv_itme);
				convertView.setTag(vh);
			}else{
				vh=(ViewHolder) convertView.getTag();
			}
			
			
			String name = values.get(position).name;
			//System.out.println(position+"我是专区条目："+name);
			
			bitmapU.display(vh.childtv, HttpRes.base_uRL+values.get(position).picurl);
			return convertView;
		}
		
	}
	

}
