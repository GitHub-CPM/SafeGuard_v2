package com.itheima.safeguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.dao.BlackDao;
import com.itheima.safeguard.domain.BlackBean;
import com.itheima.safeguard.domain.BlackTable;

/**
 * @author Administrator
 * 
 *         这是通讯卫士的界面,主要是添加黑名单,将黑名单中的手机/短信进行拦截
 */
public class TelSmsActivity extends Activity {

	protected static final int LOADING = 1;
	protected static final int FINISHED = 2;
	protected static final int quantityPerPage = 10; // 每次加载一批数据的条目量为10条
	private ProgressBar pb_loading;
	private TextView tv_nodata;
	private ListView lv_black;
	private List<BlackBean> blackList;
	private List<BlackBean> moreList;
	private BlackDao blackDao;
	private MyAdapter myAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();// 初始化界面
		initData();// 初始化数据
		initEvent(); // 初始化事件
	}

	/**
	 * 初始化事件,监听页面滚动加载数据
	 */
	private void initEvent() {
		lv_black.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 监听当滑动停止之后,并且停在显示最后一条条目的时候,进行加载更新数据
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // 当listview滑动静止的时候
					// 停在了最后一个条目
					if (blackList.size() == lv_black.getLastVisiblePosition() + 1) {
						initData(); // 加载数据
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING: // 正在加载的页面进行显示
				// 加载页面显示,没有数据和黑名单列表不显示
				pb_loading.setVisibility(View.VISIBLE);
				tv_nodata.setVisibility(View.GONE);
				lv_black.setVisibility(View.GONE);
				break;
			case FINISHED: // 显示黑名单数据
				// 1.有数据内容
				if (moreList.size() != 0) {
					lv_black.setVisibility(View.VISIBLE);
					pb_loading.setVisibility(View.GONE);
					tv_nodata.setVisibility(View.GONE);
					// 更新数据
					myAdapter.notifyDataSetChanged();
				} else {
					// 2.没有数据内容
					if (blackList.size() != 0) {
						Toast.makeText(getApplicationContext(), "已经没有更多数据了~", Toast.LENGTH_SHORT).show();
						lv_black.setVisibility(View.VISIBLE);
						pb_loading.setVisibility(View.GONE);
						tv_nodata.setVisibility(View.GONE);
						// 更新数据
						myAdapter.notifyDataSetChanged();
						return;
					}
					tv_nodata.setVisibility(View.VISIBLE);
					lv_black.setVisibility(View.GONE);
					pb_loading.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * 初始化数据,因为需要根据数据库的实际情况, 来回切换没有数据/加载中/黑名单三个页面的显示与否
	 */
	private void initData() {

		new Thread(new Runnable() { // 进行数据查询的耗时操作

					@Override
					public void run() {
						// 进行查询数据前,发送一条信息显示正在加载
						mHandler.obtainMessage(LOADING).sendToTarget();
						// SystemClock.sleep(500);

						// 取数据
						// 1.读取更多的数据,因为一开始加载时候,size为0,每次加载后数量均为第一批的首角标
						moreList = blackDao.getMoreDatas(blackList.size(), quantityPerPage);
						// 2.之前加载的数据继续保存在总显示条数里,继续显示
						blackList.addAll(moreList);

						// 发通知
						mHandler.obtainMessage(FINISHED).sendToTarget();
					}
				}).start();
	}
	

	/**
	 * @author CPM
	 * 
	 *         优化缓存显示(Listview)的封装类
	 */
	public class ItemView {
		private TextView tv_phone;
		private TextView tv_mode;
		private ImageView iv_lajitong;
	}

	/**
	 * @author CPM 创建适配器
	 */
	public class MyAdapter extends BaseAdapter {

		private BlackBean bean;

		@Override
		public int getCount() {
			return blackList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ItemView itemView = null;
			if (convertView == null) {
				itemView = new ItemView();
				// 第一次创建view
				convertView = View.inflate(getApplicationContext(), R.layout.item_telsms_listview_black, null);
				// 获得各个组件的引用
				itemView.tv_phone = (TextView) convertView.findViewById(R.id.tv_item_telsms_phone);
				itemView.tv_mode = (TextView) convertView.findViewById(R.id.tv_item_telsms_mode);
				itemView.iv_lajitong = (ImageView) convertView.findViewById(R.id.iv_item_telsms_lajitong);
				// 将itemview放在标记里
				convertView.setTag(itemView);
			}else {
				// 已经有了缓存view
				itemView = (ItemView) convertView.getTag();
			}
			
			bean = blackList.get(position);
			itemView.tv_phone.setText(bean.getPhone());
			switch (bean.getMode()) {
			case BlackTable.SMS_MODE:
				itemView.tv_mode.setText("短信拦截");
				break;
			case BlackTable.TEL_MODE:
				itemView.tv_mode.setText("电话拦截");
				break;
			case BlackTable.ALL_MODE:
				itemView.tv_mode.setText("短信&电话拦截");
				break;
			default:
				break;
			}
			// 设置删除黑名单的点击事件
			itemView.iv_lajitong.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder ab = new AlertDialog.Builder(TelSmsActivity.this);
					ab.setTitle("删除");
					ab.setMessage("确定要要删除吗?");
					ab.setPositiveButton("真删~", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 删除数据库对应条目
							blackDao.delete(bean.getPhone());
							// 删除容器中对应内容
							blackList.remove(position);
							// 更新界面
							myAdapter.notifyDataSetChanged();
						}
					});
					ab.setNegativeButton("点错了", null);
					ab.show();
				}
			});
			return convertView;
//			if (convertView == null) {
//				convertView = View.inflate(getApplicationContext(),
//						R.layout.item_telsms_listview_black, null);
//			}
//			TextView tv_phone = (TextView) convertView
//					.findViewById(R.id.tv_item_telsms_phone);
//			TextView tv_mode = (TextView) convertView
//					.findViewById(R.id.tv_item_telsms_mode);
//			// ImageView iv_delete = (ImageView)
//			// convertView.findViewById(R.id.iv_item_telsms_lajitong);
//
//			BlackBean bean = blackList.get(position);
//			tv_phone.setText(bean.getPhone());
//
//			switch (bean.getMode()) {
//			case BlackTable.SMS_MODE:
//				tv_mode.setText("短信拦截");
//				break;
//			case BlackTable.TEL_MODE:
//				tv_mode.setText("电话拦截");
//				break;
//			case BlackTable.ALL_MODE:
//				tv_mode.setText("全部拦截");
//				break;
//			default:
//				break;
//			}
//
//			return convertView;
		}

	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		setContentView(R.layout.activity_telsms);

		pb_loading = (ProgressBar) findViewById(R.id.pb_telsms_loading);// 正在加载
		tv_nodata = (TextView) findViewById(R.id.tv_telsms_nodata);// 没有数据
		lv_black = (ListView) findViewById(R.id.lv_telsms_black);// 黑名单列表

		blackList = new ArrayList<>(); // 实例化带有blackbean的list
		moreList = new ArrayList<>(); // 装有读取更多实例的list
		myAdapter = new MyAdapter(); // 创建适配器
		lv_black.setAdapter(myAdapter); // 给listview设置适配器

		blackDao = new BlackDao(getApplicationContext()); // 创建业务类
	}

	/**
	 * 添加黑名单的按钮触发事件,弹出下拉列表
	 * 
	 * @param view
	 */
	public void addBlack(View view) {
		Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT)
				.show();
	}

}
