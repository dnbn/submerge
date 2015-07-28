package com.submerge.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.submerge.constant.AppConstants;
import com.submerge.model.entity.Authorities;
import com.submerge.model.entity.DualSubtitleConfig;
import com.submerge.model.entity.SubtitleProfile;
import com.submerge.model.entity.User;
import com.submerge.model.entity.UserAuthorities;
import com.submerge.model.entity.UserAuthoritiesId;
import com.submerge.model.entity.accountstatus.EnabledStatus;
import com.submerge.model.entity.authority.UserAuthority;
import com.submerge.service.UserService;

@Service("sbmUserService")
@Transactional
public class SbmUserService implements UserService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findById(int id) {
		return (User) this.sessionFactory.getCurrentSession().get(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		return (User) this.sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("email", email)).uniqueResult();
	}

	@Override
	public User findByName(String name) {
		return (User) this.sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public void save(User user) {
		Date currDate = Calendar.getInstance().getTime();
		user.setLastUpdate(currDate);
		for (DualSubtitleConfig subConfigs : user.getDualSubtitleConfigs()) {
			subConfigs.setLastUpdate(currDate);
			subConfigs.getProfileOne().setLastUpdate(currDate);
			subConfigs.getProfileTwo().setLastUpdate(currDate);
		}
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void create(User user) {
		Date currDate = Calendar.getInstance().getTime();

		// Set default sub profile if not set
		Set<DualSubtitleConfig> subConfigs = user.getDualSubtitleConfigs();
		if (!subConfigs.stream().anyMatch(sc -> sc.isCurrent())) {
			DualSubtitleConfig dsc = new DualSubtitleConfig(user, new SubtitleProfile(), new SubtitleProfile(), true);
			dsc.setLastUpdate(currDate);
			subConfigs.add(dsc);
			user.setDualSubtitleConfigs(subConfigs);
		}
		user.setLastUpdate(currDate);

		// Assign default status (enabled)
		if (user.getAccountStatus() == null) {
			user.setAccountStatus(new EnabledStatus());
		}

		// Save user and profiles
		this.sessionFactory.getCurrentSession().save(user);

		// If no priviledge assigned, default is user
		if (user.getUserAuthorities().size() == 0) {
			addAuthority(user, new UserAuthority());
		}

	}

	@Override
	public void addAuthority(User user, Authorities authority) {
		UserAuthoritiesId id = new UserAuthoritiesId(user.getId(), authority.getId());
		UserAuthorities auth = new UserAuthorities(id, user, authority, Calendar.getInstance().getTime());
		this.sessionFactory.getCurrentSession().save(auth);
	}

	@Override
	public String hashPassword(String password) {
		return new MessageDigestPasswordEncoder(AppConstants.SHA_256.toString()).encodePassword(password, null);
	}

}
