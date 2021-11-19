package com.github.dnbn.submerge.boot.service;

import com.github.dnbn.submerge.boot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Service used to manage users
 */
public interface UserService extends UserDetailsService {

	/**
	 * Find a user from its login
	 * 
	 * @param name: the user login
	 * @return the user
	 */
	User findByName(String name);

	/**
	 * Save a user in database
	 * 
	 * @param user: the user to be saved
	 */
	void save(User user);

	/**
	 * Create a new user
	 * 
	 * @param user: the user to be saved
	 */
	void create(User user);
}
