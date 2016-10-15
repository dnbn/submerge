package com.submerge.web.security.model;

import org.springframework.security.core.GrantedAuthority;

public class SbmAuthority implements GrantedAuthority {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -7521460933659506665L;

	/**
	 * Authority name
	 */
	private String authority;

	/**
	 * Constructor
	 * 
	 * @param authority: the authority name
	 */
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
