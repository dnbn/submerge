package submerge.web.model.entity.accountstatus;

import submerge.web.model.entity.AccountStatus;

public class DisabledStatus extends AccountStatus {

	private static final long serialVersionUID = 253397194400185697L;

	public DisabledStatus() {
		super();
		this.id = 2;
		this.info = "DISABLED";
	}

}
