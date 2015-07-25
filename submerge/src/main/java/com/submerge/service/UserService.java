package com.submerge.service;

import com.submerge.exception.HashingPasswordException;
import com.submerge.model.entity.Authorities;
import com.submerge.model.entity.User;

public interface UserService {

	User findById(int id);

	User findByEmail(String email);

	User findByName(String name);

	void save(User user);

	void create(User user);

	void addAuthority(User user, Authorities authority);

	String hashPassword(String password) throws HashingPasswordException;

}
