package com.taotao.redboy.activity;

import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.taotao.redboy.R;

public class FillDataActivity extends BaseActivity implements OnCheckedChangeListener{

	
	private ScrollView scrollView;
	private TextView textView;
	private RadioGroup radioGroup;
	private RadioButton rbOne;
	private RadioButton rbTwo;
	private RadioButton rbThree;
	private RadioButton rbFour;

	@Override
	public void initView() {

		scrollView = (ScrollView) findViewById(R.id.scrollView);
		textView=(TextView) findViewById(R.id.tv_personal_info);
		radioGroup=(RadioGroup) findViewById(R.id.radio_group);
		rbOne=(RadioButton) findViewById(R.id.rb_one);
		rbTwo=(RadioButton) findViewById(R.id.rb_two);
		rbThree=(RadioButton) findViewById(R.id.rb_three);
		rbFour=(RadioButton) findViewById(R.id.rb_four);
		/**
		 * 如果在initView方法里调用getTop方法是不能正确得到top值的，因为可能此时还没有测量其top值
		 */
		/*int top = textView.getTop();
		System.out.println("initView方法中得到的top值："+top);*/
		Handler handler=new Handler();
		handler.postDelayed(runnable,200);
		
	}

	private Runnable runnable=new Runnable() {
		
		@Override
		public void run() {
			int top = textView.getTop();
			scrollView.scrollTo(0, top);
		}
	};
	@Override
	public void initData() {

	}

	@Override
	public void initLinstener() {

		radioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public int getLayout() {
		return R.layout.activity_fill_data;
	}
	
	@Override
	public void initOtherButton(View v) {
		super.initOtherButton(v);
		
		switch (v.getId()) {
		case R.id.btn_ok:
			
			
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {

		switch (checkedId) {
		case R.id.rb_one:
			rbOne.setChecked(true);
			rbTwo.setChecked(false);
			rbThree.setChecked(false);
			rbFour.setChecked(false);
			break;
		case R.id.rb_two:
			rbTwo.setChecked(true);
			rbOne.setChecked(false);
			rbThree.setChecked(false);
			rbFour.setChecked(false);
			
			break;
		case R.id.rb_three:
			rbThree.setChecked(true);
			rbOne.setChecked(false);
			rbTwo.setChecked(false);
			rbFour.setChecked(false);
			break;
		case R.id.rb_four:
			rbOne.setChecked(false);
			rbTwo.setChecked(false);
			rbThree.setChecked(false);
			rbFour.setChecked(true);
			break;
			
		}
	}
	

}
