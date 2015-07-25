package com.submerge.model.entity.accountstatus;

import com.submerge.model.entity.AccountStatus;

public class EnabledStatus extends AccountStatus {

	private static final long serialVersionUID = 1L;

	public EnabledStatus() {
		super();
		this.id = 1;
		this.info = "ENABLED";
	}

}
