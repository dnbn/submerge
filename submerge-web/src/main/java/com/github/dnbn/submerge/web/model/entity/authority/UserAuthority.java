package com.github.dnbn.submerge.web.model.entity.authority;

import com.github.dnbn.submerge.web.model.entity.Authorities;

public class UserAuthority extends Authorities {

	private static final long serialVersionUID = 731131541160682876L;

	public UserAuthority() {
		super();
		this.id = 2;
		this.name = "ROLE_USER";
	}

}
