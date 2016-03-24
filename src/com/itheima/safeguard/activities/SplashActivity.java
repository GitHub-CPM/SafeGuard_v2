package com.itheima.safeguard.activities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.domain.UrlBean;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * @author CPM
 * 
 *         手机卫士的splash界面
 */
public class SplashActivity extends ActionBarActivity {

	private static final long ANIMATION_TIMES = 3000L; // loading时候的动画播放时间设置为3秒
	private static final int LOADMAIN = 1; // 进入主界面
	private static final int SHOWUPDATEDIALOG = 2; // 显示更新的对话框
	
	private UrlBean parseJson;// 封装了网络最新应用的版本信息的bean
	private RelativeLayout rl_root; // 动画的layout
	private String urlPath_version = "http://10.0.2.2:8080/safeguard_version.json"; // 开启应用时,检查app应用版本情况的网络地址
	private int versionCode; // 本app的版本号

	private long startTimeMillis; // 开始访问网络的时间

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
		initData(); // 初始化数据,获得本app本身的版本信息
		initAnimation(); // 初始化动画
		checkVersion(); // 访问网络,检查app的版本,如果有最新的,则用以更新app
	}

	/**
	 * 初始化数据,获得app本身的版本信息,用以比对更新app
	 */
	private void initData() {
		// 获得包管理者,取得包的相关信息,拿到本app的版本号
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 访问服务器,检查app版本是否有更新
	 */
	private void checkVersion() {
		// 创建子线程,将访问网络的耗时操作放在子线程上操作
		new Thread(new Runnable() {

			private HttpURLConnection conn;
			private BufferedReader br;

			@Override
			public void run() {
				try {
					// 获得开始访问网络更新的开始时间
					startTimeMillis = System.currentTimeMillis();
					
					// 访问版本更新的网址,设置连接/读取资源超时均为5秒
					// 请求方式为GET,建立连接
					URL url = new URL(urlPath_version);
					conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int responseCode = conn.getResponseCode();

					// 如果状态码为200,则访问成功
					if (responseCode == 200) {
						// 建立连接,读取服务器端的数据到本地
						// 并将数据存储
						InputStream is = conn.getInputStream();
						br = new BufferedReader(
								new InputStreamReader(is));
						// 第一次在这里忘了new对象了.
						StringBuilder jsonData = new StringBuilder();
						String line = br.readLine();
						while (line != null) {
							jsonData.append(line);
							line = br.readLine();
						}

						parseJson = parseJson(jsonData);

					} else { // 如果访问失败,则弹出吐司提醒用户版本更新失败.
						Toast.makeText(SplashActivity.this, "网络访问失败,版本更新失败.",
								Toast.LENGTH_SHORT).show();
					}

					// 抓取异常信息,统一发消息到hanlder,以吐司的形式弹出
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					// 最后必须要执行的操作

					// 调用方法,查看是否存在最新app版本
					isNewVersion(parseJson);
					
					try {
						// 关流,关连接
						br.close();
						conn.disconnect();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
			}

		}).start();
	}

	/**
	 * 创建Handler对象,用以处理子线程与主线程进行交互
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADMAIN: // 进入主界面
				Intent intent = new Intent(SplashActivity.this, HomeActivities.class);
				startActivity(intent);
				
				// 去到主界面之后,把当前的SplashActivity界面给finish()
				finish();
				
				break;
			case SHOWUPDATEDIALOG: // 进入主界面
				showUpdateDialog();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 对比本app版本号与网络最新的版本号,是否有最新的版本
	 * 
	 * @param parseJson
	 */
	protected void isNewVersion(UrlBean parseJson) {
		int versionNetCode = parseJson.getVersion();
		
		// 获得弹出对话框前的时间
		long endTimeMillis = System.currentTimeMillis();
		if (endTimeMillis - startTimeMillis < 3000) {
			SystemClock.sleep(3000 - (endTimeMillis - startTimeMillis));
		}
		
		if (versionNetCode == versionCode) {
			// 版本号一致,不需要更新,直接进入主界面
			loadMainActivity();
		} else {
			// 版本号不一致,需要弹出对话框,提示用户更新apk操作
			Message msg = Message.obtain();
			msg.what = SHOWUPDATEDIALOG;
			handler.sendMessage(msg);
		}
	}

	/**
	 * 下载最新版本的apk
	 */
	private void downLoadNewApk() {
		// 导入Xutils库,调用相关方法
		HttpUtils utils = new HttpUtils();
		
		// 下载新版本apk
		utils.download(parseJson.getApkUrl(), "/mnt/sdcard/SG.apk", new RequestCallBack<File>() {
			
			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// 下载apk成功
				Toast.makeText(getApplicationContext(), "下载apk成功!", Toast.LENGTH_LONG).show();
				
				// 安装下载完成的apk
				installApk();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// 下载apk失败
				Toast.makeText(getApplicationContext(), "下载apk失败.", Toast.LENGTH_LONG).show();
			}
			
		});
	}
	
	/**
	 * 安装下载完成的最新的apk
	 */
	private void installApk() {
		// 使用意图去安装下载好的apk安装包
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		Uri data = Uri.fromFile(new File("/mnt/sdcard/SG.apk"));
		String type = "application/vnd.android.package-archive";
		intent.setDataAndType(data, type);
		
		// 开启安装apk的活动.
		// 如果用户在安装的过程当中,取消了安装,则应该使用一个结果的返回,在返回结果里进入主界面
		// 这里需要重写onActivityResult()的方法
		startActivityForResult(intent,0);
	}
	
	/* 
	 * 如果用户在安装过程当中取消了apk的安装,则需要让界面回到主界面
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		loadMainActivity();
		super.onActivityResult(arg0, arg1, arg2);
	}

	/**
	 * 进入主界面
	 */
	private void loadMainActivity() {
		Message msg = Message.obtain();
		msg.what = LOADMAIN;
		handler.sendMessage(msg);
	}

	/**
	 * 应用版本号与网络最新版本号不一致,弹出apk更新的对话框 如果更新apk,则XXXXX(后台进行下载/安装) 如果不更新apk,则转到主界面中
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
		
		// 当用户在弹出对话框之后,并没有选择点击"更新"或者"不更新",而是直接按了返回键,那么就要进入到主界面当中.
		updateDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				//	点解了返回键触发了取消事件,则回到主界面
				loadMainActivity();
			}
		});
		
		updateDialog
				.setTitle("提醒")
				.setMessage(
						"亲!应用有最新的版本,请点击更新获得更好的保护哦~更新特性如下:"
								+ parseJson.getDesc())
				.setPositiveButton("更新", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 下载新的apk
						downLoadNewApk();
					}
				}).setNegativeButton("下次再更新", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 不更新apk,进入主界面
						loadMainActivity();
					}
				});
		updateDialog.show();// 显示对话框
	}

	/**
	 * 这个方法用于对传入的Json数据参数进行解析,并返回一个UrlBean类的实体对象
	 * 
	 * @param jsonData
	 *            这是带有Json数据的参数
	 */
	private UrlBean parseJson(StringBuilder jsonData) {
		// 创建UrlBean对象,用以封装解析出来的Json内容
		UrlBean urlBean = new UrlBean();

		try {
			// 开始解析json内容
			JSONObject jsonObject = new JSONObject(jsonData + "");
			int version = jsonObject.getInt("version");
			String apkUrl = jsonObject.getString("url");
			String desc = jsonObject.getString("desc");

			// 将读取到信息封装到urlBean中
			urlBean.setVersion(version);
			urlBean.setApkUrl(apkUrl);
			urlBean.setDesc(desc);

			// 捕获并处理异常
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return urlBean;
	}

	/**
	 * 界面组件的初始化
	 */
	private void initView() {
		setContentView(R.layout.activity_splash);

		// 获得对根组件对象的引用,用以设置动画效果
		rl_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
	}

	/**
	 * 动画的初始化
	 */
	private void initAnimation() {
		// 1.渐变动画
		// 创建渐变动画对象,从0.0到1.0的变化,也就是从完全透明到完全显示状态
		// 设置渐变动画的持续时间
		// 让该动画播放完成后的状态停留在结束时候的样式
		AlphaAnimation aa = new AlphaAnimation(0.0F, 1.0F);
		aa.setDuration(ANIMATION_TIMES);
		aa.setFillAfter(true);

		// 2.旋转动画
		// 创建旋转动画,让组件本身中心点(锚点)自行顺时针旋转
		RotateAnimation ra = new RotateAnimation(0.0F, 360F,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		ra.setDuration(ANIMATION_TIMES);
		ra.setFillAfter(true);

		// 3.缩放动画
		// 创建缩放动画,从自身的中心点开始,从小到大逐渐显示
		ScaleAnimation sa = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		sa.setDuration(ANIMATION_TIMES);
		sa.setFillAfter(true);

		// 4.组合动画
		// 创建组合动画,将以上三种动画形式结合在一起
		AnimationSet as = new AnimationSet(true);
		as.addAnimation(aa);
		as.addAnimation(ra);
		as.addAnimation(sa);

		// 让splash界面的根组件启动已经设置了的渐变动画效果
		rl_root.startAnimation(as);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
