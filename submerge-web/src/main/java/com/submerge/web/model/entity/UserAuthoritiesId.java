package com.submerge.web.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserAuthoritiesId implements Serializable {

	private static final long serialVersionUID = 3056068247550696873L;
		
	private int idUser;
	private int idAuthorities;

	public UserAuthoritiesId() {
	}

	public UserAuthoritiesId(int idUser, int idAuthorities) {
		this.idUser = idUser;
		this.idAuthorities = idAuthorities;
	}

	@Column(name = "fk_id_account", nullable = false)
	public int getIdUser() {
		return this.idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	@Column(name = "fk_id_authorities", nullable = false)
	public int getIdAuthorities() {
		return this.idAuthorities;
	}

	public void setIdAuthorities(int idAuthorities) {
		this.idAuthorities = idAuthorities;
	}

	@Override
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserAuthoritiesId))
			return false;
		UserAuthoritiesId castOther = (UserAuthoritiesId) other;

		return (this.getIdUser() == castOther.getIdUser()) && (this.getIdAuthorities() == castOther.getIdAuthorities());
	}

	@Override
	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getIdUser();
		result = 37 * result + this.getIdAuthorities();
		return result;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Id User : ").append(this.idUser);
		sb.append("\nId auth : ").append(this.idAuthorities);
		return sb.toString();
	}

}
