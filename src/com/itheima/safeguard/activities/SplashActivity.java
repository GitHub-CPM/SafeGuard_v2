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

import android.os.Bundle;
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
	private RelativeLayout rl_root;
	private String urlPath_version = "http://10.0.2.2:8080/safeguard_version.json"; // ����Ӧ��ʱ,���appӦ�ð汾����������ַ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		initAnimation(); // ��ʼ������
		checkVersion(); // ��������,���app�İ汾,��������µ�,�����Ը���app
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
						while (line!= null) {
							jsonData.append(line);
							line = br.readLine();
						}

						// ����Json����,������һ����װ��Json���ݵ�ʵ�����
						UrlBean parseJson = parseJson(jsonData);
						System.out.println(parseJson.toString());
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
			String version = jsonObject.getString("version");
			String apkUrl = jsonObject.getString("url");
			String desc = jsonObject.getString("desc");

			// ����ȡ����Ϣ��װ��urlBean��
			urlBean.setVersion(Float.parseFloat(version));
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
