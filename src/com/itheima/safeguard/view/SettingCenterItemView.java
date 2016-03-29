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
 *         Ϊ���������Զ���һ��view,������ʾ������Ŀ
 */
public class SettingCenterItemView extends LinearLayout {

	private TextView tv_content;// ������Ŀ������
	private TextView tv_title;// ������Ŀ�ı���
	private CheckBox cb_check; // ������Ŀ�ĸ�ѡ��
	private String[] contents; // content��������
	private View item; // �������ĵ�����Ŀ

	public SettingCenterItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();// ��ʼ���ؼ�
		initEvent();// ��ʼ���¼�
		String title = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.safeguard",
				"mytitle");// ��ȡÿ����Ŀ����Ϊ"title"��ֵ
		String content = attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/com.itheima.safeguard",
				"mycontent");// ��ȡÿ����Ŀ����Ϊ"content"��ֵ

		contents = content.split("#");
		tv_title.setText(title);
		
		tv_content.setTextColor(Color.RED);
		tv_content.setText(contents[0]);
	}

	/**
	 * ���û���̬���ü�����
	 * 
	 * @param listener
	 *            �����ú�,����ļ�����
	 */
	public void setItemClickListener(OnClickListener listener) {
		item.setOnClickListener(listener);
	}

	/**
	 * ������ṩ���������ڲ���ѡ���Ƿ��ϵĹ�������
	 */
	public void setIsCheck(Boolean isCheck) {
		cb_check.setChecked(isCheck);
	}

	/**
	 * ������ṩ�����ڲ���ѡ���Ƿ񱻹���
	 */
	public Boolean getIsCheck() {
		return cb_check.isChecked();
	}

	/**
	 * ��ʼ���¼�
	 */
	private void initEvent() {
		// ������ѡ��Ĺ�ѡ״̬,���ö�Ӧ����ʾ
		cb_check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) {// �������ѡ����
					tv_content.setTextColor(Color.GREEN);
					tv_content.setText(contents[1]);
				} else {// û�б���ѡ��
					tv_content.setTextColor(Color.RED);
					tv_content.setText(contents[0]);
				}
			}
		});
		// // ����������Ŀ�ı�������,��������
		// item.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// cb_check.setChecked(!cb_check.isChecked());
		// }
		// });

	}

	/**
	 * ��ʼ���Զ���Ŀؼ�
	 */
	private void initView() {
		// ���Զ������Ŀ�ؼ�������һ������ؼ�
		item = View.inflate(getContext(), R.layout.item_settingcenter_view,
				null);
		// ��ӵ���������������
		addView(item);

		tv_title = (TextView) item
				.findViewById(R.id.tv_settingcenter_item_title);
		tv_content = (TextView) item
				.findViewById(R.id.tv_settingcenter_item_content);
		cb_check = (CheckBox) item
				.findViewById(R.id.cb_settingcenter_item_check);
	}

}
