package com.itheima.safeguard.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itheima.safeguard.R;

/**
 * @author CPM
 * 
 *         为设置中心自定义一个view,用于显示单个条目
 */
public class SettingCenterItemView extends LinearLayout {

	private TextView tv_content;// 单个条目的内容
	private TextView tv_title;// 单个条目的标题
	private CheckBox cb_check; // 单个条目的复选框
	private String[] contents; // content的数据组
	private View item; // 填充而来的单个条目

	public SettingCenterItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();// 初始化控件
		initEvent();// 初始化事件
		String title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.safeguard",
				"mytitle");// 获取每个条目当中为"title"的值
		String content = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.safeguard",
				"mycontent");// 获取每个条目当中为"content"的值

		contents = content.split("#");
		tv_title.setText(title);
		
		tv_content.setTextColor(Color.RED);
		tv_content.setText(contents[0]);
	}

	/**
	 * 让用户动态设置监听器
	 * 
	 * @param listener
	 *            外界调用后,传入的监听器
	 */
	public void setItemClickListener(OnClickListener listener) {
		item.setOnClickListener(listener);
	}

	/**
	 * 对外界提供访问设置内部复选框是否勾上的公共方法
	 */
	public void setIsCheck(Boolean isCheck) {
		cb_check.setChecked(isCheck);
	}

	/**
	 * 对外界提供访问内部复选框是否被勾上
	 */
	public Boolean getIsCheck() {
		return cb_check.isChecked();
	}

	/**
	 * 初始化事件
	 */
	private void initEvent() {
		// 监听复选框的勾选状态,设置对应的显示
		cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// 如果被勾选上了
					tv_content.setTextColor(Color.GREEN);
					tv_content.setText(contents[1]);
				} else {// 没有被勾选上
					tv_content.setTextColor(Color.RED);
					tv_content.setText(contents[0]);
				}
			}
		});
		// // 监听单个条目的被点击情况,做测试用
		// item.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// cb_check.setChecked(!cb_check.isChecked());
		// }
		// });

	}

	/**
	 * 初始化自定义的控件
	 */
	private void initView() {
		// 将自定义的条目控件组填充成一个整体控件
		item = View.inflate(getContext(), R.layout.item_settingcenter_view,
				null);
		// 添加到本布局容器当中
		addView(item);

		tv_title = (TextView) item
				.findViewById(R.id.tv_settingcenter_item_title);
		tv_content = (TextView) item
				.findViewById(R.id.tv_settingcenter_item_content);
		cb_check = (CheckBox) item
				.findViewById(R.id.cb_settingcenter_item_check);
	}

}
