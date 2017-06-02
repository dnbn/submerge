package com.github.dnbn.submerge.web.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.github.dnbn.submerge.web.model.UserBO;
import com.github.dnbn.submerge.web.model.entity.Authorities;

/**
 * Service used to manage users
 */
public interface UserService extends UserDetailsService {

	/**
	 * Find a user from its id
	 * 
	 * @param id: the user id
	 * @return the user
	 */
	public UserBO findById(int id);

	/**
	 * Find a user from its email
	 * 
	 * @param email: the user email
	 * @return the user
	 */
	public UserBO findByEmail(String email);

	/**
	 * Find a user from its login
	 * 
	 * @param name: the user login
	 * @return the user
	 */
	public UserBO findByName(String name);

	/**
	 * Save a user in database
	 * 
	 * @param user: the user to be saved
	 */
	public void save(UserBO user);

	/**
	 * Create a new user
	 * 
	 * @param user: the user to be saved
	 */
	public void create(UserBO user);

	/**
	 * Add an authority to a user
	 * 
	 * @param user: the user
	 * @param authority: the authority
	 */
	public void addAuthority(UserBO user, Authorities authority);

	/**
	 * Encode a user password
	 * 
	 * @param password: the password to be encoded
	 * @return the encoded password
	 */
	public String hashPassword(String password);

}
