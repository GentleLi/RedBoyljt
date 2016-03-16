package com.taotao.redboy.fragment;

import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.adapter.BrandAdapter;
import com.taotao.redboy.bean.BrandBean;
import com.taotao.redboy.res.HttpRes;
import com.taotao.redboy.utils.NetWorkUtils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.TextView;

public class BrandFragment extends BaseFragment {
	/**
	 * 标头控件
	 */
	@ViewInject(R.id.tv_tab_name)
	private TextView tv_tab_name;
	
	/**
	 * list的控件
	 */
	@ViewInject(R.id.listBrandInfo)
	private ExpandableListView listBrandInfo;

	private BrandBean brandBean;
	
	
	
	
	@Override
	public View initView() {
		View view=View.inflate(getActivity(), R.layout.brand_activity, null);
		//注册控件
		
		ViewUtils.inject(this, view);
		
		//设置界面的初始化
		
		tv_tab_name.setText("推荐品牌");
		
		return view;
	}

	@Override
	public void initData() {
		//获得数据
		//http://169.254.224.51/ECServicez8/brand
		String url="/brand";
		/**
		 * 联网获取推荐商品的信息
		 */
		NetWorkUtils.requestGetData(url, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				//System.out.println(arg0.result);

				String result=arg0.result;
				//解析打包数据
				
				praresJson(result);
				
				
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				//System.out.println("-------------");
				arg0.printStackTrace();
			}
		});
		
		

	}
	/**
	 * 解析数据，并打包
	 * @param result
	 */
	protected void praresJson(String result) {
		Gson gson=new Gson();
		
		brandBean = gson.fromJson(result, BrandBean.class);
		
		System.out.println(brandBean.brand.get(1).key);
		
		
		
		//设置适配器
		
		 BrandAdapter adapter=new BrandAdapter(brandBean, getActivity());
		
		listBrandInfo.setAdapter(adapter);
		
	}
	

	
	
	

}
