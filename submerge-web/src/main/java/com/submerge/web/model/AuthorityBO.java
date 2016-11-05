package com.submerge.web.model;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityBO implements GrantedAuthority {

	private static final long serialVersionUID = -7521460933659506665L;

	private String authority;

	/**
	 * Constructor
	 * 
	 * @param authority: the authority name
	 */
	public AuthorityBO(String authority) {
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
