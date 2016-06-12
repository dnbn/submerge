package submerge.web.service;

import submerge.web.model.entity.Authorities;
import submerge.web.model.entity.User;

/**
 * Service used to manage users
 */
public interface UserService {

	/**
	 * Find a user from its id
	 * 
	 * @param id: the user id
	 * @return the user
	 */
	public User findById(int id);

	/**
	 * Find a user from its email
	 * 
	 * @param email: the user email
	 * @return the user
	 */
	public User findByEmail(String email);

	/**
	 * Find a user from its login
	 * 
	 * @param name: the user login
	 * @return the user
	 */
	public User findByName(String name);

	/**
	 * Save a user in database
	 * 
	 * @param user: the user to be saved
	 */
	public void save(User user);

	/**
	 * Create a new user
	 * 
	 * @param user: the user to be saved
	 */
	public void create(User user);

	/**
	 * Add an authority to a user
	 * 
	 * @param user: the user
	 * @param authority: the authority
	 */
	public void addAuthority(User user, Authorities authority);

	/**
	 * Encode a user password
	 * 
	 * @param password: the password to be encoded
	 * @return the encoded password
	 */
	public String hashPassword(String password);

}
