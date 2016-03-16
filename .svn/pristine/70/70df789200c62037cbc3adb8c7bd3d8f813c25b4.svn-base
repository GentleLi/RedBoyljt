package com.taotao.redboy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taotao.redboy.R;
import com.taotao.redboy.bean.GoodsBean;
import com.taotao.redboy.utils.UrlUtils;

public class DetailActivity extends BaseActivity {

	private static final int SHOW = 0;
	/**
	 * 商品名称
	 */
	private String detailName;
	/**
	 * 商品市场价
	 */
	private String detailMarketprice;
	/**
	 * 商品评论
	 */
	private String detailCommentcount;
	/**
	 * 商品数量
	 */
	private String detailNumber;
	/**
	 * 商品图片URL路径
	 */
	private String detailPic;
	/**
	 * 商品ID
	 */
	private String detailId;

	@ViewInject(R.id.im_detail)
	private ImageView im_detail;

	@ViewInject(R.id.text_name_detail)
	private TextView text_name_detail;

	@ViewInject(R.id.text_mactprice_detail)
	private TextView text_mactprice_detail;

	@ViewInject(R.id.text_huiyuan_detail)
	private TextView text_huiyuan_detail;

	@ViewInject(R.id.edit_num_detail)
	private EditText edit_num_detail;

	@ViewInject(R.id.button_addcar)
	private Button button_addcar;

	@ViewInject(R.id.button_addsoucang)
	private Button button_addsoucang;

	@ViewInject(R.id.text_cangku)
	private TextView text_cangku;

	@ViewInject(R.id.text_comment)
	private TextView text_comment;

	@ViewInject(R.id.back_detail)
	private ImageView back_detail;
	@ViewInject(R.id.btn_cart)
	private Button btnCart;

	@Override
	public void initView() {

		dbUtils = DbUtils.create(context, "goods.db");
		sp = getSharedPreferences("config", 0);

		ViewUtils.inject(this);

		pd = new ProgressDialog(context);

		pd.setMessage("正在为您加载!");

		pd.show();

	}

	private ProgressDialog pd;

	@Override
	public void initData() {

		Intent intent = getIntent();
		detailName = intent.getStringExtra("detailName");
		detailMarketprice = intent.getStringExtra("detailMarketprice");
		detailCommentcount = intent.getStringExtra("detailCommentcount");
		detailNumber = intent.getStringExtra("detailNumber");
		detailPic = intent.getStringExtra("detailPic");
		detailId = intent.getStringExtra("detailId");

		text_name_detail.append(detailName);
		text_huiyuan_detail.append((Integer.valueOf(detailMarketprice) - 20)
				+ "");
		text_mactprice_detail.append(detailMarketprice);
		text_cangku.setText("北京仓库");
		text_comment.setText("评论300");

		BitmapUtils bitmapUtils = new BitmapUtils(context);
		bitmapUtils.display(im_detail, UrlUtils.BaseUrl + detailPic);

		handler.sendEmptyMessageDelayed(SHOW, 1000);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
			case SHOW:
				pd.dismiss();
				break;

			default:
				break;
			}
		}
	};

	/**
	 * 用户输入的购买的数量
	 */
	private String numString;
	private SharedPreferences sp;
	private DbUtils dbUtils;

	@Override
	public void initLinstener() {
		
		btnCart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(DetailActivity.this,CheckActivity.class);
				startActivity(intent);
				
			}
		});

		/**
		 * 加入购物车
		 */
		button_addcar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				numString = edit_num_detail.getText().toString().trim();

				boolean islog = sp.getBoolean(PersonCenterActivity.ISLOG, true);

				if (islog) {

					Toast.makeText(context, "请先登录!!", 0).show();
					Intent intent=new Intent(DetailActivity.this,PersonCenterActivity.class);
					startActivity(intent);

				} else {

					if (!TextUtils.isEmpty(numString)) {
						Toast.makeText(context, "谢谢合作", 0).show();

						String userInfo = sp.getString("userid", "-1");

						String[] split = userInfo.split("#");

						int userId = Integer.valueOf(split[0]);

						GoodsBean goodsBean = new GoodsBean(Integer
								.valueOf(detailId), userId, detailName, 500,
								Integer.valueOf(detailMarketprice), Integer
										.valueOf(detailMarketprice) - 20, "L",
								detailPic);
						System.out.println("&&&&&&&&&&&"+detailPic);
						try {
							dbUtils.save(goodsBean);
						} catch (DbException e) {
							e.printStackTrace();
						}
					} else {

						Toast.makeText(context, "请输入数量!", 0).show();

					}
				}
			}
		});

		/**
		 * 加入收藏
		 */
		button_addsoucang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		back_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public int getLayout() {
		return R.layout.activity_detail;
	}
}
