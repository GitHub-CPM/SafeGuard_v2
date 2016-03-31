package com.itheima.safeguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.safeguard.R;
import com.itheima.safeguard.domain.ContactsBean;
import com.itheima.safeguard.utils.MyConstants;

public abstract class BaseTelSmsBlackActivity extends ListActivity {

	protected final int LOADING = 1;
	protected final int FINISHED = 2;

	// 存在所有手机联系人的列表,必须先创建对象,因为listview的数据是后面才更新的,不先创建就会有空指针异常
	private List<ContactsBean> list = new ArrayList<>();
	private ProgressDialog pd; // 加载进度的对话框
	private MyAdapter mAdapter; // listview的适配器
	private ListView lv_contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 获取listview的引用
		lv_contacts = getListView();

		// 关联listview适配器,显示listview

		mAdapter = new MyAdapter();
		lv_contacts.setAdapter(mAdapter);

		initData(); // 初始化数据
		initEvent();// 初始化点击事件
	}

	/**
	 * 点击选中的条目,把数据返回到设置页面
	 */
	private void initEvent() {
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String phone = list.get(position).getPhoneNum();
				Intent intent = new Intent(BaseTelSmsBlackActivity.this,
						Setup3Activities.class);
				intent.putExtra(MyConstants.SAFENUMBER, phone);// 将选中的电话号码返回到设置页面
				setResult(1, intent);
				finish();// 关闭自己
			}
		});
	}

	/**
	 * 创建listview的适配器
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();// 按照联系人的数量显示条目数量
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(BaseTelSmsBlackActivity.this,
						R.layout.item_friends_listview, null);
			}

			TextView tv_name = (TextView) convertView
					.findViewById(R.id.tv_item_friends_name);
			TextView tv_phoneNum = (TextView) convertView
					.findViewById(R.id.tv_item_friends_phonenum);

			tv_name.setText(list.get(position).getName());
			tv_phoneNum.setText(list.get(position).getPhoneNum());

			return convertView;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	/**
	 * 新开启一个子线程,进行耗时操作
	 */
	private void initData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 先弹出一个进度加载对话框(因为这个是activity,所以布局就不用progressbar)
				sendMessage(LOADING);

//				SystemClock.sleep(500);// 让其休眠0.5秒钟,用以显示弹出的进度加载对话框

				list = readData();
				// // 读取手机联系人,加载数据完成后,显示在ListView中
				// list = ReadContactsEngine.readContacts(getApplicationContext());
				sendMessage(FINISHED);
			}

		}).start();
	}

	public abstract List<ContactsBean> readData();

	/**
	 * handler发送信息
	 * 
	 * @param what
	 *            发送信息所对应的类型
	 */
	private void sendMessage(int what) {
		Message msg = Message.obtain();
		msg.what = what;
		mHandler.sendMessage(msg);
	}

	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING:// 正在加载数据
				pd = new ProgressDialog(BaseTelSmsBlackActivity.this);
				pd.setTitle("请稍等~");
				pd.setMessage("正在拼命加载啊......");
				pd.show();// 显示

				break;
			case FINISHED:// 已经完成数据加载
				// 关闭加载对话框
				if (pd != null) {
					pd.dismiss();
					pd = null;
				}
				// 通知更新数据
				mAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

}
