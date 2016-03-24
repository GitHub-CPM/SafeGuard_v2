package com.itheima.safeguard.activities;

import java.io.BufferedReader;
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
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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

/**
 * @author CPM
 * 
 *         �ֻ���ʿ��splash����
 */
public class SplashActivity extends ActionBarActivity {

	private static final long ANIMATION_TIMES = 3000L; // loadingʱ��Ķ�������ʱ������Ϊ3��
	private static final int LOADMAIN = 1; // ����������
	private static final int SHOWUPDATEDIALOG = 2; // ��ʾ���µĶԻ���
	
	private UrlBean parseJson;// ��װ����������Ӧ�õİ汾��Ϣ��bean
	private RelativeLayout rl_root; // ������layout
	private String urlPath_version = "http://10.0.2.2:8080/safeguard_version.json"; // ����Ӧ��ʱ,���appӦ�ð汾����������ַ
	private int versionCode; // ��app�İ汾��

	private long startTimeMillis; // ��ʼ���������ʱ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		initData(); // ��ʼ������,��ñ�app����İ汾��Ϣ
		initAnimation(); // ��ʼ������
		checkVersion(); // ��������,���app�İ汾,��������µ�,�����Ը���app
	}

	/**
	 * ��ʼ������,���app����İ汾��Ϣ,���ԱȶԸ���app
	 */
	private void initData() {
		// ��ð�������,ȡ�ð��������Ϣ,�õ���app�İ汾��
		PackageManager pm = getPackageManager();
		try {
			PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
			versionCode = packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ʷ�����,���app�汾�Ƿ��и���
	 */
	private void checkVersion() {
		// �������߳�,����������ĺ�ʱ�����������߳��ϲ���
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// ��ÿ�ʼ����������µĿ�ʼʱ��
					startTimeMillis = System.currentTimeMillis();
					
					// ���ʰ汾���µ���ַ,��������/��ȡ��Դ��ʱ��Ϊ5��
					// ����ʽΪGET,��������
					URL url = new URL(urlPath_version);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					conn.setRequestMethod("GET");
					int responseCode = conn.getResponseCode();

					// ���״̬��Ϊ200,����ʳɹ�
					if (responseCode == 200) {
						// ��������,��ȡ�������˵����ݵ�����
						// �������ݴ洢
						InputStream is = conn.getInputStream();
						BufferedReader br = new BufferedReader(
								new InputStreamReader(is));
						// ��һ������������new������.
						StringBuilder jsonData = new StringBuilder();
						String line = br.readLine();
						while (line != null) {
							jsonData.append(line);
							line = br.readLine();
						}

						parseJson = parseJson(jsonData);
						System.out.println(parseJson.getVersion() + "�汾��");

						// ���÷���,�鿴�Ƿ��������app�汾

						isNewVersion(parseJson);

						// ����,������
						br.close();
						conn.disconnect();

					} else { // �������ʧ��,�򵯳���˾�����û��汾����ʧ��.
						Toast.makeText(SplashActivity.this, "�������ʧ��,�汾����ʧ��.",
								Toast.LENGTH_SHORT).show();
					}

					// ץȡ�쳣��Ϣ
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).start();
	}

	/**
	 * ����Handler����,���Դ������߳������߳̽��н���
	 */
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADMAIN: // ����������
				Intent intent = new Intent(SplashActivity.this, HomeActivities.class);
				startActivity(intent);
				break;
			case SHOWUPDATEDIALOG: // ����������
				showUpdateDialog();
				break;
			default:
				break;
			}
		};
	};

	/**
	 * �Աȱ�app�汾�����������µİ汾��,�Ƿ������µİ汾
	 * 
	 * @param parseJson
	 */
	protected void isNewVersion(UrlBean parseJson) {
		int versionNetCode = parseJson.getVersion();
		
		// ��õ����Ի���ǰ��ʱ��
		long endTimeMillis = System.currentTimeMillis();
		if (endTimeMillis - startTimeMillis < 3000) {
			SystemClock.sleep(3000 - (endTimeMillis - startTimeMillis));
		}
		
		if (versionNetCode == versionCode) {
			// �汾��һ��,����Ҫ����,ֱ�ӽ���������
			loadMainActivity();
		} else {
			// �汾�Ų�һ��,��Ҫ�����Ի���,��ʾ�û�����apk����
			Message msg = Message.obtain();
			msg.what = SHOWUPDATEDIALOG;
			handler.sendMessage(msg);
		}
	}

	/**
	 * ����������
	 */
	private void loadMainActivity() {
		Message msg = Message.obtain();
		msg.what = LOADMAIN;
		handler.sendMessage(msg);
	}

	/**
	 * Ӧ�ð汾�����������°汾�Ų�һ��,����apk���µĶԻ��� �������apk,��XXXXX(��̨��������/��װ) ���������apk,��ת����������
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder updateDialog = new AlertDialog.Builder(this);
		updateDialog
				.setTitle("����")
				.setMessage(
						"��!Ӧ�������µİ汾,�������»�ø��õı���Ŷ~������������:"
								+ parseJson.getDesc())
				.setPositiveButton("����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ������µ��߼�
						// ������˾��һ��,ģ����µ����
						Toast.makeText(SplashActivity.this, "���ڸ���~~",
								Toast.LENGTH_LONG).show();
					}
				}).setNegativeButton("�´��ٸ���", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// ������apk,����������
						loadMainActivity();
					}
				});
		updateDialog.show();// ��ʾ�Ի���
	}

	/**
	 * ����������ڶԴ����Json���ݲ������н���,������һ��UrlBean���ʵ�����
	 * 
	 * @param jsonData
	 *            ���Ǵ���Json���ݵĲ���
	 */
	private UrlBean parseJson(StringBuilder jsonData) {
		// ����UrlBean����,���Է�װ����������Json����
		UrlBean urlBean = new UrlBean();

		try {
			// ��ʼ����json����
			JSONObject jsonObject = new JSONObject(jsonData + "");
			int version = jsonObject.getInt("version");
			String apkUrl = jsonObject.getString("url");
			String desc = jsonObject.getString("desc");

			// ����ȡ����Ϣ��װ��urlBean��
			urlBean.setVersion(version);
			urlBean.setApkUrl(apkUrl);
			urlBean.setDesc(desc);

			// ���񲢴����쳣
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return urlBean;
	}

	/**
	 * ��������ĳ�ʼ��
	 */
	private void initView() {
		setContentView(R.layout.activity_splash);

		// ��öԸ�������������,�������ö���Ч��
		rl_root = (RelativeLayout) findViewById(R.id.rl_splash_root);
	}

	/**
	 * �����ĳ�ʼ��
	 */
	private void initAnimation() {
		// 1.���䶯��
		// �������䶯������,��0.0��1.0�ı仯,Ҳ���Ǵ���ȫ͸������ȫ��ʾ״̬
		// ���ý��䶯���ĳ���ʱ��
		// �øö���������ɺ��״̬ͣ���ڽ���ʱ�����ʽ
		AlphaAnimation aa = new AlphaAnimation(0.0F, 1.0F);
		aa.setDuration(ANIMATION_TIMES);
		aa.setFillAfter(true);

		// 2.��ת����
		// ������ת����,������������ĵ�(ê��)����˳ʱ����ת
		RotateAnimation ra = new RotateAnimation(0.0F, 360F,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		ra.setDuration(ANIMATION_TIMES);
		ra.setFillAfter(true);

		// 3.���Ŷ���
		// �������Ŷ���,����������ĵ㿪ʼ,��С��������ʾ
		ScaleAnimation sa = new ScaleAnimation(0.0F, 1.0F, 0.0F, 1.0F,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.5F);
		sa.setDuration(ANIMATION_TIMES);
		sa.setFillAfter(true);

		// 4.��϶���
		// ������϶���,���������ֶ�����ʽ�����һ��
		AnimationSet as = new AnimationSet(true);
		as.addAnimation(aa);
		as.addAnimation(ra);
		as.addAnimation(sa);

		// ��splash����ĸ���������Ѿ������˵Ľ��䶯��Ч��
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
