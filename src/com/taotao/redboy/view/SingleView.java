package com.taotao.redboy.view;

import com.taotao.redboy.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleView extends FrameLayout implements Checkable {

	private TextView tvSendTime;
	private ImageView ivMark;
	private boolean isChecked=false;

	public SingleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public SingleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SingleView(Context context) {
		super(context);
		
		initView(context);
	}

	public void setText(String text){
		
		tvSendTime.setText(text);
	}
	private void initView(Context context) {
		View view = View.inflate(context, R.layout.single_view_item, null);
		tvSendTime = (TextView) view.findViewById(R.id.tv_send_time);
		ivMark = (ImageView) view.findViewById(R.id.iv_mark);
		this.addView(view);
	}

	@Override
	public void setChecked(boolean checked) {
		if (checked) {
			ivMark.setVisibility(View.VISIBLE);
		}else {
			ivMark.setVisibility(View.GONE);
		}
		isChecked=checked;
	}

	@Override
	public boolean isChecked() {
		
		return isChecked;
	}
	

	@Override
	public void toggle() {
		if (isChecked) {
			ivMark.setVisibility(View.VISIBLE);
			isChecked=true;
		}else {
			ivMark.setVisibility(View.GONE);
			isChecked=false;
		}
	}

}
