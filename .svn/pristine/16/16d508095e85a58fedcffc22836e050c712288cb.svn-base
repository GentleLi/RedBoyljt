package com.taotao.redboy.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Shader.TileMode;
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

public class SaleTicketActivity extends BaseActivity {

	private ArrayList<String> salesTickets = null;
	private ListView listView;

	@Override
	public void initView() {

		tvHead = (TextView) findViewById(R.id.tv_head);
		
		tvHead.setText("我的优惠券");
		listView = (ListView) findViewById(R.id.list_view);
		back = (Button) findViewById(R.id.back);
		salesTickets = new ArrayList<String>();
		salesTickets.add("9月惊喜50元礼券");
		salesTickets.add("国亲节80元礼券");
		salesTickets.add("圣诞节大放送80元礼券");
	}

	@Override
	public void initData() {

	}

	private int checkedPosition = -1;

	@Override
	public void initLinstener() {
		adapter = new MyAdapter();
		back.setOnClickListener(this);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				checkedPosition = position;
				Intent intent=new Intent();
				intent.putExtra("sale_ticket", position);
				setResult(CheckActivity.RES_CHOOSE_SALE_TICKET,intent);
				adapter.notifyDataSetChanged();
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
		super.onClick(v);
	}

	@Override
	public int getLayout() {
		return R.layout.activity_sale_ticket;
	}

	private MyAdapter adapter;
	private Button back;
	private TextView tvHead;

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return salesTickets.size();
		}

		@Override
		public Object getItem(int position) {
			return salesTickets.get(position);
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
				convertView = View.inflate(context, R.layout.sale_ticket_item,
						null);
				holder.tvSalesTicket = (TextView) convertView
						.findViewById(R.id.tv_sale_ticket);
				holder.ivMark = (ImageView) convertView
						.findViewById(R.id.iv_mark);
				convertView.setTag(holder);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == checkedPosition) {
				holder.ivMark.setVisibility(View.VISIBLE);

			} else {
				holder.ivMark.setVisibility(View.GONE);
			}

			holder.tvSalesTicket.setText(salesTickets.get(position));
			return convertView;
		}

	}

	private class ViewHolder {

		TextView tvSalesTicket;
		ImageView ivMark;
	}

}
