package com.itheima.safeguard.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

/**
 * @author CPM
 * 
 *         �Զ���Ŀؼ�
 */
public class MyTextView extends TextView {

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// ����Ϊ��Զ��ý���
		return true;
	}

}
