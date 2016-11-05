package com.submerge.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.submerge.web.constant.AppConstants;
import com.submerge.web.model.SubtitleProfileBO;
import com.submerge.web.model.UserBO;
import com.submerge.web.model.entity.Authorities;
import com.submerge.web.model.entity.DualSubtitleConfig;
import com.submerge.web.model.entity.SubtitleProfile;
import com.submerge.web.model.entity.User;
import com.submerge.web.model.entity.UserAuthorities;
import com.submerge.web.model.entity.UserAuthoritiesId;
import com.submerge.web.model.entity.accountstatus.EnabledStatus;
import com.submerge.web.model.entity.authority.UserAuthority;
import com.submerge.web.pages.bean.model.proxy.AuthenticatedUser;
import com.submerge.web.pages.bean.model.proxy.SubConfig;
import com.submerge.web.service.UserService;
import com.submerge.web.service.impl.converter.SubtitleProfileConverter;
import com.submerge.web.service.impl.converter.UserConverter;
import com.submerge.web.utils.ProfileUtils;

@Service("userDetailsServiceImpl")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private AuthenticatedUser authenticatedUser;

	@Autowired
	private SubConfig subConfig;

	@Override
	public UserBO findById(int id) {

		User user = (User) this.sessionFactory.getCurrentSession().get(User.class, id);
		return UserConverter.convert(user);
	}

	@Override
	public UserBO findByEmail(String email) {

		User user = (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();

		return UserConverter.convert(user);
	}

	@Override
	public UserBO findByName(String name) {

		User user = (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();

		return UserConverter.convert(user);
	}

	@Override
	public void save(UserBO userBO) {

		save(UserConverter.convert(userBO));
	}

	@Override
	public void create(UserBO userBO) {

		User user = UserConverter.convert(userBO);
		Date currDate = Calendar.getInstance().getTime();

		// Set default sub profile if not set
		Set<DualSubtitleConfig> subConfigs = user.getDualSubtitleConfigs();
		if (!subConfigs.stream().anyMatch(sc -> sc.isCurrent())) {
			DualSubtitleConfig dsc = new DualSubtitleConfig(user, defaultProfile(), defaultProfile(), true);
			dsc.setLastUpdate(currDate);
			dsc.setAvoidSwitch(true);
			dsc.setClean(true);
			subConfigs.add(dsc);
			user.setDualSubtitleConfigs(subConfigs);
		}
		user.setLastUpdate(currDate);

		if (user.getSimpleAssConfig() == null) {
			user.setSimpleAssConfig(defaultProfile());
		}
		user.getSimpleAssConfig().setLastUpdate(currDate);

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
	public void addAuthority(UserBO userBO, Authorities authority) {

		User user = (User) this.sessionFactory.getCurrentSession().get(User.class, userBO.getId());

		addAuthority(user, authority);
	}

	@Override
	public String hashPassword(String password) {

		return new MessageDigestPasswordEncoder(AppConstants.SHA_256.toString()).encodePassword(password, null);
	}

	@Override
	public UserBO loadUserByUsername(String login) throws UsernameNotFoundException {

		User user = (User) this.sessionFactory.getCurrentSession().createCriteria(User.class)
				.add(Restrictions.eq("name", login)).uniqueResult();

		if (user == null) {
			throw new UsernameNotFoundException("User not found : " + login);
		}

		UserBO userBO = UserConverter.convert(user);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, userBO.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		user.setLastLogin(Calendar.getInstance().getTime());
		this.save(user);

		// Update the user in session
		this.authenticatedUser.setUser(userBO);

		// Update profiles in session
		this.subConfig.setConfigs(userBO.getDualSubtitleConfigs(), userBO.getSimpleAssConfig());

		return userBO;
	}

	private void save(User user) {

		Date currDate = Calendar.getInstance().getTime();
		user.setLastUpdate(currDate);

		for (DualSubtitleConfig subConfigs : user.getDualSubtitleConfigs()) {
			subConfigs.setLastUpdate(currDate);
			subConfigs.getProfileOne().setLastUpdate(currDate);
			subConfigs.getProfileTwo().setLastUpdate(currDate);
			subConfigs.setUser(user);
		}

		if (user.getSimpleAssConfig() == null) {
			user.setSimpleAssConfig(defaultProfile());
		}

		user.getSimpleAssConfig().setLastUpdate(currDate);

		this.sessionFactory.getCurrentSession().update(user);
	}

	private void addAuthority(User user, Authorities authority) {

		UserAuthoritiesId id = new UserAuthoritiesId(user.getId(), authority.getId());
		UserAuthorities auth = new UserAuthorities(id, user, authority, Calendar.getInstance().getTime());

		this.sessionFactory.getCurrentSession().save(auth);
	}

	private static SubtitleProfile defaultProfile() {

		SubtitleProfileBO profile = new SubtitleProfileBO();
		profile.setLastUpdate(Calendar.getInstance().getTime());
		ProfileUtils.initProfiles(profile);

		return SubtitleProfileConverter.convert(profile);
	}

}
