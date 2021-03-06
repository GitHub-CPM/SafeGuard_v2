package com.itheima.safeguard.activities;

import java.util.List;

import com.itheima.safeguard.domain.ContactsBean;
import com.itheima.safeguard.engine.ReadContactsEngine;

/**
 * @author CPM
 *
 *		从联系人导入的界面
 */
public class FriendsActivity extends BaseTelSmsBlackActivity {

	@Override
	public List<ContactsBean> readData() {
		return ReadContactsEngine.readContacts(getApplicationContext());
	}


}
