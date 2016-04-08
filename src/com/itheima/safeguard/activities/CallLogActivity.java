package com.itheima.safeguard.activities;

import java.util.List;

import com.itheima.safeguard.domain.ContactsBean;
import com.itheima.safeguard.engine.ReadContactsEngine;

/**
 * @author CPM
 *
 *		通信记录的界面
 */ 
public class CallLogActivity extends BaseTelSmsBlackActivity {

	@Override
	public List<ContactsBean> readData() {
		return ReadContactsEngine.readPhoneLog(getApplicationContext());
	}


}
