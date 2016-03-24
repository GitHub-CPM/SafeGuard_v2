package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.itheima.safeguard.R;

/**
 * @author CPM
 * 
 *         这是主界面
 */
public class HomeActivities extends Activity {

	private int[] icons = { R.drawable._0safe, R.drawable._1callmsgsafe,
			R.drawable._2app, R.drawable._3taskmanager,
			R.drawable._4netmanager, R.drawable._5trojan,
			R.drawable._6sysoptimize, R.drawable._7atools,
			R.drawable._8settings };// menu菜单的九宫格图标
	private String[] names = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
			"缓存清理", "高级工具", "设计中心" };// menu菜单的九宫格名称

	private GridView gv_menu; // 主界面的九宫格菜单
	private MyAdapter myAdapter; // gridview的适配器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
		initData();// 初始化gridview的显示数据
	}

	/**
	 * 在此方法中进行初始化界面的操作
	 */
	private void initView() {
		setContentView(R.layout.activity_home);

		gv_menu = (GridView) findViewById(R.id.gv_home_menu);

	}
	
	/**
	 * 为主界面上的GridView设置布局,初始化数据
	 */
	private void initData() {
		// 创建自定义的适配器,并设置给主界面的gridview控件
		myAdapter = new MyAdapter();
		gv_menu.setAdapter(myAdapter);
	}

	/**
	 * @author Administrator
	 * 
	 *         创建适配器,用以设计九宫格里的每个按钮(按钮的样式是图片+文字形式)
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// 此处返回的是九宫格的个数,用以设定每个图标都有点击事件
			return icons.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 将整个item的layout层填充生成新的view
			View view = View.inflate(getApplicationContext(), R.layout.item_home_gridview, null);
			
			// 获得item这个view里面的图片和文字的组件(这时候的view就是新的view了)
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_item_home_gv_icon);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_item_home_gv_name);

			// 为每个icon图标设置内容
			iv_icon.setImageResource(icons[position]);

			// 为每个九宫格的名字设置名字
			tv_name.setText(names[position]);

			return view;
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


}
