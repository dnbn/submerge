package com.submerge.security.model;

import org.springframework.security.core.GrantedAuthority;

public class SbmAuthority implements GrantedAuthority {

	private static final long serialVersionUID = -7521460933659506665L;
	private String authority;

	public SbmAuthority(String authority) {
		super();
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}

	@Override
	public String toString() {
		return "[Authority: " + this.authority + " ]";
	}

}
