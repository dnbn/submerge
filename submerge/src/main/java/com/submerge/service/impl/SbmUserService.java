package com.submerge.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.logging.Logger;

import javax.xml.bind.DatatypeConverter;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.submerge.constant.AppConstants;
import com.submerge.exception.HashingPasswordException;
import com.submerge.model.entity.AccountStatus;
import com.submerge.model.entity.Authorities;
import com.submerge.model.entity.User;
import com.submerge.model.entity.UserAuthorities;
import com.submerge.model.entity.UserAuthoritiesId;
import com.submerge.model.entity.authority.UserAuthority;
import com.submerge.service.UserService;

@Service("userService")
@Transactional
public class SbmUserService implements UserService {

	@Autowired
	private SessionFactory sessionFactory;
	private static final Logger logger = Logger.getLogger(SbmUserService.class.getName());

	@Override
	public User findById(int id) {
		return (User) this.sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		return (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
	}

	@Override
	public User findByName(String name) {
		return (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
	}

	@Override
	public void save(User user) {
		user.setLastUpdate(Calendar.getInstance().getTime());
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void create(User user) {
		AccountStatus ac = user.getAccountStatus();
		if (ac == null) {
			user.setAccountStatus(new AccountStatus(1, null));
		}
		user.setLastUpdate(Calendar.getInstance().getTime());
		this.sessionFactory.getCurrentSession().save(user);
		addAuthority(user, new UserAuthority());
	}

	private void addAuthority(User user, Authorities autority) {
		UserAuthoritiesId uaId = new UserAuthoritiesId(user.getId(), autority.getId());
		UserAuthorities ua = new UserAuthorities();
		ua.setLastUpdate(Calendar.getInstance().getTime());
		ua.setAuthorities(autority);
		ua.setUser(user);
		ua.setId(uaId);
		this.sessionFactory.getCurrentSession().save(ua);
	}

	@Override
	public String hashPassword(String password) {
		MessageDigest digest = null;
		String hash = null;
		try {
			digest = MessageDigest.getInstance(AppConstants.SHA_256.toString());
			hash = DatatypeConverter.printHexBinary(digest.digest(password.getBytes(AppConstants.UTF_8.toString())));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			SbmUserService.logger.severe(e.getStackTrace().toString());
			throw new HashingPasswordException(e); // Runtime
		}
		return hash;
	}

}
