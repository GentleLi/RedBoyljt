package com.taotao.redboy.activity;

import com.taotao.redboy.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener{

	public BaseActivity context;
	private ImageView backup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(getLayout());
		context = this;
		initView();
		backup = (ImageView) findViewById(R.id.backup);
		
		if (backup!=null) {
			
			backup.setOnClickListener(this);
		}
		initData();
		initLinstener();
	}

	public abstract void initView();
	public abstract void initData();
	public abstract void initLinstener();
	public abstract int getLayout();

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.back:
			finish();
			break;
			
		default:
			initOtherButton(v);
			break;
		}
	}

	public void initOtherButton(View v) {
		
	}
}






















