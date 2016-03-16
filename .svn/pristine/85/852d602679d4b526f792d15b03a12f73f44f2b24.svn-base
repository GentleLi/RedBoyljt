package com.taotao.redboy.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.taotao.redboy.R;

public class ChoosePayMethodActivity extends BaseActivity {

	private ListView listView;

	private MyAdapter adapter = new MyAdapter();
	private Button btnHeadRight, back;
	private int checkedPosition = -1;

	private static List<String> payMethodList = null;

	private SharedPreferences sp;


	@Override
	public void initView() {
		sp = getSharedPreferences("config", MODE_PRIVATE);
		payMethodList = new ArrayList<String>();
		payMethodList.clear();
		payMethodList.add("到付-现金");
		payMethodList.add("到付-pos");
		payMethodList.add("支付宝");
		back = (Button) findViewById(R.id.back);
		back.setText("结算中心");
		btnHeadRight = (Button) findViewById(R.id.btn_head_right);
		btnHeadRight.setVisibility(View.GONE);
		listView = (ListView) findViewById(R.id.list_view);

	}

	@Override
	public void initData() {

	}

	@Override
	public void initLinstener() {

		back.setOnClickListener(this);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				checkedPosition = position;

				// 将userId保存
				adapter.notifyDataSetChanged();
				
				
				Intent intent=new Intent();
				
				intent.putExtra("pay_method", checkedPosition);
				setResult(CheckActivity.RES_CHOOSE_PAY_METHOD,intent);
			}
		});

	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}
	@Override
	public int getLayout() {
		// TODO Auto-generated method stub
		return R.layout.activity_choose_pay_method;
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {

			return payMethodList.size();
		}

		@Override
		public Object getItem(int position) {
			return payMethodList.get(position);
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
				convertView = View.inflate(context,
						R.layout.pay_method_list_item, null);
				holder.tvPayMethod = (TextView) convertView
						.findViewById(R.id.tv_pay_method);
				holder.ivMark = (ImageView) convertView
						.findViewById(R.id.iv_mark);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.tvPayMethod.setText(payMethodList.get(position));
			/**
			 * 是点击条目设置为选中
			 */
			if (checkedPosition == position) {
				holder.ivMark.setVisibility(View.VISIBLE);
			} else {
				holder.ivMark.setVisibility(View.GONE);
			}
			return convertView;
		}
	}

	private class ViewHolder {
		ImageView ivMark;
		TextView tvPayMethod;

	}

	@Override
	protected void onStop() {
		super.onStop();

	}

	@Override
	protected void onDestroy() {
		
		
//		sp.edit().putInt("pay_method", checkedPosition).commit();
		super.onDestroy();
	}

}
