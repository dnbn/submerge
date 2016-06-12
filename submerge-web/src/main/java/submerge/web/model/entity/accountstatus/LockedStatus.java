package submerge.web.model.entity.accountstatus;

import submerge.web.model.entity.AccountStatus;

public class LockedStatus extends AccountStatus {

	private static final long serialVersionUID = 2390198539151617177L;

	public LockedStatus() {
		super();
		this.id = 3;
		this.info = "LOCKED";
	}
}
