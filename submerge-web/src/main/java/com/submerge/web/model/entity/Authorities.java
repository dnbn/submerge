package com.submerge.web.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "authorities", schema = "public")
public class Authorities implements Serializable {

	private static final long serialVersionUID = 6242067807788118140L;
	protected int id;
	protected String name;
	protected Set<UserAuthorities> userAuthoritieses = new HashSet<>(0);

	public Authorities() {
	}

	public Authorities(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public Authorities(int id, String name, Set<UserAuthorities> userAuthoritieses) {
		this.id = id;
		this.name = name;
		this.userAuthoritieses = userAuthoritieses;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "s_authorities_id")
	@SequenceGenerator(name = "s_authorities_id", sequenceName = "s_authorities_id", allocationSize = 1)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "authorities")
	public Set<UserAuthorities> getUserAuthoritieses() {
		return this.userAuthoritieses;
	}

	public void setUserAuthoritieses(Set<UserAuthorities> userAuthoritieses) {
		this.userAuthoritieses = userAuthoritieses;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
