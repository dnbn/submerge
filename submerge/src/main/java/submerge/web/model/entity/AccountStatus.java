package com.submerge.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "account_status", schema = "public")
public class AccountStatus implements Serializable {

	public enum Status {
		LOCKED(3), 
		DISABLED(2), 
		ENABLED(1);

		private int id;

		Status(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}

	private static final long serialVersionUID = -8133484130630670009L;
	
	protected int id;
	protected String info;
	protected Set<User> users = new HashSet<>(0);

	public AccountStatus() {
	}

	public AccountStatus(int id, String info) {
		this.id = id;
		this.info = info;
	}

	public AccountStatus(int id, String info, Set<User> users) {
		this.id = id;
		this.info = info;
		this.users = users;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "info", nullable = false, length = 10)
	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "accountStatus")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return this.info;
	}

}
