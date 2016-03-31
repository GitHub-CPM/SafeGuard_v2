package com.itheima.safeguard.activities;

import java.util.List;

import com.itheima.safeguard.domain.ContactsBean;
import com.itheima.safeguard.engine.ReadContactsEngine;

public class SmsLogActivity extends BaseTelSmsBlackActivity {

	@Override
	public List<ContactsBean> readData() {
		return ReadContactsEngine.readSmslog(getApplicationContext());
	}

}
