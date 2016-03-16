package com.taotao.redboy.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.taotao.redboy.R;
import com.taotao.redboy.utils.APPRes;
import com.taotao.redboy.utils.AppUtil;

public class LjtLoginActivity extends Activity implements OnClickListener{

	protected static final int REQUEST_CODE_GALLERY = 100;
	protected static final int REQUEST_CODE_CAMERA = 106;
	private static final int RESULT_CODE_CROP = 108;
	private static final String KEY_ICON_PATH = "icon";
	private static String iconFile="";
	private ImageView ivIcon;
	private Context context;
	private CheckBox checkBox;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		context=this;
		sp = getSharedPreferences("config", MODE_PRIVATE);
		iconFile=getFilesDir().getAbsolutePath()+"/pics/head.png";
		ivIcon = (ImageView) findViewById(R.id.iv_icon);
		checkBox=(CheckBox) findViewById(R.id.check_box);
		etAccount = (EditText) findViewById(R.id.et_account);
		
		etPwd = (EditText) findViewById(R.id.et_pwd);
		
		btnLogin = (Button) findViewById(R.id.btn_login);
		
		btnToRegister = (Button) findViewById(R.id.btn_to_register);

		String icon_path = sp.getString("icon", "");
		/**
		 * 显示用户名和密码
		 */
		showRemember();

		Bitmap icon = BitmapFactory.decodeFile(icon_path);
		if (icon==null) {
			ivIcon.setImageResource(R.drawable.health_guide_men_selected);
		}else {
			ivIcon.setImageBitmap(icon);
		}
		ivIcon.setOnClickListener(this);
		output_X = AppUtil.dip2px(this, 60);
		
		btnLogin.setOnClickListener(this);
		
		btnToRegister.setOnClickListener(this);
	}

	/**
	 * 显示记住的用户名和密码
	 */
	private void showRemember() {
		String pwd = sp.getString("password", "");
		String username = sp.getString("username", "");
		etAccount.setText(username);
		etPwd.setText(pwd);
	}
	
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_login://用户登陆验证
			validateLogin();
			break;

		case R.id.iv_icon://设置用户图像
			showSelectIconDialog();
			break;
		case R.id.btn_to_register://设置用户图像
			
			Intent intent=new Intent(this,LjtRegisterActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void validateLogin() {
		
		
		String username = etAccount.getText().toString().trim();
		String pwd=etPwd.getText().toString().trim();
		
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(context, "用户名不能为空", 0).show();
			return;
		}
		if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(context, "密码不能为空", 0).show();
			return;
		}
		
		sp.edit().putString("username", username).commit();
		//如果用户选择记住密码
		if (checkBox.isChecked()) {
			sp.edit().putString("password", pwd).commit();
		}
		
		doLogin(username,pwd);
		
	}

	private void doLogin(String username, String pwd) {

		HttpUtils httpUtils=new HttpUtils();
		
		RequestParams params=new RequestParams();
		params.addBodyParameter("username", username);
		params.addBodyParameter("password", pwd);
		
		
		
		httpUtils.send(HttpMethod.POST, APPRes.BASE_URL+APPRes.login_url, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String jsonString=responseInfo.result;
				System.out.println(jsonString);
				parseJson(jsonString);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				
				System.out.println("网络错误"+msg);
			}
		});
	}

	/**
	 * 解析Json字符串
	 */
	private void parseJson(String json) {
		
		/**
		 * 登陆成功之后跳转到主页面
		 */
		
		
		try {
			JSONObject jsonObject=new JSONObject(json);
			
			JSONObject userinfo = jsonObject.getJSONObject("userinfo");
			
			int id=userinfo.getInt("userid");
			String bonus=userinfo.getString("bonus");
			String level=userinfo.getString("level"); 
			
			System.out.println("id:"+id);
			System.out.println("bonus:"+bonus);
			System.out.println("level:"+level);
			
			/**
			 * 登陆之后将userId保存
			 */
			Editor edit = sp.edit();
			edit.putInt("userId", id);
			edit.commit();
			
			Intent intent=new Intent(this,MainActivity.class);
			startActivity(intent);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
	}
	private String[] items = new String[] { "从手机获取图片", "拍照获取图片" };
	private int output_X;
	private int output_Y;
	private String IMAGE_FILE_NAME = "photo";
	private SharedPreferences sp;
	private EditText etAccount;
	private EditText etPwd;
	private Button btnLogin;
	private Button btnToRegister;

	private void showSelectIconDialog() {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setTitle("请选择获取头像的方式");

		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				switch (which) {
				case 0:
					// 说明从手机获取图片，做打开图库的操作
					Intent galleryIntent = new Intent();
					galleryIntent.setType("image/*");
					galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
					break;
				case 1:
					// 说明要从相机新拍图片，调用系统相机功能
					Intent cameraIntent = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// 判断sd卡是否可用
					if (isSdAvailable()) {
						File file = new File(Environment
								.getExternalStorageDirectory(), IMAGE_FILE_NAME);
						cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
								Uri.fromFile(file));

						startActivityForResult(cameraIntent,
								REQUEST_CODE_CAMERA);
					}
					break;
				}
			}
		});
		builder.show();
	}

	/**
	 * 判断是sd卡是否可用
	 */
	private boolean isSdAvailable() {

		String externalStorageState = Environment.getExternalStorageState();
		if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_CANCELED) {
			// 说明用户未作任何操作
			Toast.makeText(LjtLoginActivity.this, "未选取图片", 0).show();
			return;
		}

		switch (requestCode) {
		case REQUEST_CODE_GALLERY:
			// 说明用户从图库选取图片
			cropRawPhoto(data.getData());

			break;
		case REQUEST_CODE_CAMERA:
			// 说明用户从相机新拍图片
			if (isSdAvailable()) {
				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);

				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(LjtLoginActivity.this, "sd卡不可用", 0).show();
			}
			break;
		case RESULT_CODE_CROP:
			// 说明用户从图库选取图片
			setImageToIcon(data);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, RESULT_CODE_CROP);
	};

	/**
	 * 提取保存裁剪后的图片数据并设置给imageview
	 * 
	 * @param intent
	 */
	public void setImageToIcon(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			ivIcon.setImageBitmap(photo);
			saveBitmapToFile(this, photo);
		}
	}

	/**
	 * 保存用户头像到本地
	 */
	private void saveBitmapToFile(Context context, Bitmap bitmap) {

		String path = context.getFilesDir().getAbsolutePath() + "/pics";

		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		
		// 新建一个文件
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(new File(file, "head.png"));
			boolean result = bitmap.compress(Bitmap.CompressFormat.PNG, 100,
					outputStream);

			Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();
			sp.edit().putString(KEY_ICON_PATH, iconFile).commit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}
