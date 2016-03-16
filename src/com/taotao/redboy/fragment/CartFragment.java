package com.taotao.redboy.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.activity.CheckActivity;
import com.taotao.redboy.bean.CartBean.Cart;
import com.taotao.redboy.bean.GoodsBean;
import com.taotao.redboy.utils.APPRes;
import com.taotao.redboy.view.MyListView;

public class CartFragment extends BaseFragment implements OnClickListener {

	@ViewInject(R.id.tv_total_num)
	private TextView tvTotalNum;
	@ViewInject(R.id.tv_total_point)
	private TextView tvTotalPrice;
	@ViewInject(R.id.tv_total_price)
	private TextView tvTotalPoint;
	@ViewInject(R.id.btn_head_right)
	private Button btnRight;
	@ViewInject(R.id.tv_head)
	private TextView tvHead;
	@ViewInject(R.id.back)
	private Button back;

	@Override
	public View initView() {
		sp = getActivity().getSharedPreferences("config", 0);
		View view = View.inflate(getActivity(), R.layout.fragment_cart, null);
		ViewUtils.inject(this, view);
		cartBody = view.findViewById(R.id.cart_body);
		cartNull = view.findViewById(R.id.cart_null);
		listView = (MyListView) view.findViewById(R.id.list_view_cart);
		tvHead.setText("购物车");
		btnRight.setText("去结算");
		return view;
	}

	@Override
	public void onResume() {
		System.out.println("onResume");
		super.onResume();

	}

	@Override
	public void initData() {
		

		bitmapUtils = new BitmapUtils(getActivity());
		try {
			goods = dbUtils.findAll(GoodsBean.class);

			if (goods == null) {
				cartBody.setVisibility(View.GONE);
				cartNull.setVisibility(View.VISIBLE);
			} else {
				getMyGoodsInCart();
			}
		} catch (DbException e) {
			e.printStackTrace();
		}

		/*
		 * HttpUtils httpUtils=new HttpUtils();
		 * 
		 * RequestParams params=new RequestParams();
		 * 
		 * params.addBodyParameter("sku", "3:3:1,3|5:2:2");
		 * httpUtils.send(HttpMethod.POST, APPRes.BASE_URL+APPRes.cart_url,
		 * params, new RequestCallBack<String>() {
		 * 
		 * @Override public void onSuccess(ResponseInfo<String> responseInfo) {
		 * 
		 * System.out.println("onSuccess:"+responseInfo.result); String
		 * jsonResult=responseInfo.result; parseJson(jsonResult); }
		 * 
		 * @Override public void onFailure(HttpException error, String msg) {
		 * System.out.println("联网发生错误::"+msg); } });
		 */
	}

	List<GoodsBean> myGoods = new ArrayList<GoodsBean>();

	private void getMyGoodsInCart() {

		String userInfo = sp.getString("userid", "-1");

		String[] split = userInfo.split("#");

		int userId = Integer.valueOf(split[0]);

		GoodsBean good = null;
		for (int i = 0; i < goods.size(); i++) {

			good = goods.get(i);

			if (good.userId == userId) {

				myGoods.add(good);
			}else {
				
				cartBody.setVisibility(View.GONE);
				cartNull.setVisibility(View.VISIBLE);
			}

		}

		// 设置适配，填充数据
		if (myGoods != null&&myGoods.size()>0) {
			cartBody.setVisibility(View.VISIBLE);
			cartNull.setVisibility(View.GONE);
			adapter = new MyAdapter();
			listView.setAdapter(adapter);
		}

	}

	/**
	 * 解析json数据
	 */
	protected void parseJson(String json) {

		/*
		 * Gson gson=new Gson(); CartBean cartBean = gson.fromJson(json,
		 * CartBean.class);
		 * 
		 * System.out.println(cartBean.response);
		 * System.out.println(cartBean.cart.get(0).product.name);
		 * 
		 * int totalCount=cartBean.totalCount; int
		 * totalPoint=cartBean.totalPoint; double
		 * totalPrice=cartBean.totalPrice; carts = cartBean.cart; for (int i =
		 * 0; i < carts.size(); i++) { Product product=carts.get(i).product;
		 * System.err.println("*************"+product.name);
		 * products.add(product);
		 * 
		 * } tvTotalNum.setText(""+totalCount);
		 * tvTotalPoint.setText(""+totalPoint);
		 * tvTotalPrice.setText(""+totalPrice); adapter=new MyAdapter();
		 * listView.setAdapter(adapter); adapter.notifyDataSetChanged();
		 */
	}

	@Override
	public void initListener() {

		btnRight.setOnClickListener(this);

	}

	private MyAdapter adapter;
	private BitmapUtils bitmapUtils;
	private MyListView listView;
	private List<Cart> carts;
	private View cartBody;
	private View cartNull;
	private List<GoodsBean> goods;
	private SharedPreferences sp;
	private int userId;

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return goods.size();
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
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(getActivity(),
						R.layout.good_info_list_item, null);
				holder.tvColor = (TextView) convertView
						.findViewById(R.id.tv_color);
				holder.tvNum = (TextView) convertView.findViewById(R.id.tv_num);
				holder.tvPrice = (TextView) convertView
						.findViewById(R.id.tv_price);
				holder.tvSum = (TextView) convertView.findViewById(R.id.tv_sum);
				holder.ivIcon = (ImageView) convertView
						.findViewById(R.id.iv_icon);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();

			}

			System.out.println("***************************");

			GoodsBean good = myGoods.get(position);
			int procNum = good.number;
			holder.tvNum.setText("" + procNum);

			holder.tvPrice.setText("" + good.price);
			holder.tvSum.setText("" + good.price * procNum);
			holder.tvTitle.setText(good.name);

			String imgUrl = APPRes.BASE_URL + good.url;

			bitmapUtils.display(holder.ivIcon, imgUrl);

			return convertView;
		}

	}

	private class ViewHolder {
		ImageView ivIcon;
		TextView tvTitle, tvNum, tvSum, tvPrice, tvColor;

	}

	private void toCheckActivity() {

		Intent intent = new Intent(getActivity(), CheckActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_head_right:
			toCheckActivity();
			break;
		}
	}

}
