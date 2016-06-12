package submerge.web.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import submerge.web.constant.AppConstants;
import submerge.web.model.entity.Authorities;
import submerge.web.model.entity.DualSubtitleConfig;
import submerge.web.model.entity.SubtitleProfile;
import submerge.web.model.entity.User;
import submerge.web.model.entity.UserAuthorities;
import submerge.web.model.entity.UserAuthoritiesId;
import submerge.web.model.entity.accountstatus.EnabledStatus;
import submerge.web.model.entity.authority.UserAuthority;
import submerge.web.service.UserService;
import submerge.web.utils.ProfileUtils;

@Service("sbmUserService")
@Transactional
public class SbmUserService implements UserService {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public User findById(int id) {
		
		User user = (User) this.sessionFactory.getCurrentSession().get(User.class, id);
		return user;
	}

	@Override
	public User findByEmail(String email) {
		
		User user = (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("email", email))
				.uniqueResult();
		return user;
	}

	@Override
	public User findByName(String name) {
		
		User user = (User) this.sessionFactory.getCurrentSession()
				.createCriteria(User.class)
				.add(Restrictions.eq("name", name))
				.uniqueResult();
		return user;
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
		
		if (user.getSimpleAssConfig() == null) {
			user.setSimpleAssConfig(defaultProfile());
		}
		
		user.getSimpleAssConfig().setLastUpdate(currDate);
		
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public void create(User user) {

		Date currDate = Calendar.getInstance().getTime();

		// Set default sub profile if not set
		Set<DualSubtitleConfig> subConfigs = user.getDualSubtitleConfigs();
		if (!subConfigs.stream().anyMatch(sc -> sc.isCurrent())) {
			DualSubtitleConfig dsc = new DualSubtitleConfig(user, defaultProfile(), defaultProfile(), true);
			dsc.setLastUpdate(currDate);
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
	public void addAuthority(User user, Authorities authority) {

		UserAuthoritiesId id = new UserAuthoritiesId(user.getId(), authority.getId());
		UserAuthorities auth = new UserAuthorities(id, user, authority, Calendar.getInstance().getTime());
		this.sessionFactory.getCurrentSession().save(auth);
	}

	@Override
	public String hashPassword(String password) {

		return new MessageDigestPasswordEncoder(AppConstants.SHA_256.toString()).encodePassword(password, null);
	}

	private static SubtitleProfile defaultProfile() {

		SubtitleProfile profile = new SubtitleProfile();
		profile.setLastUpdate(Calendar.getInstance().getTime());
		ProfileUtils.initProfiles(profile);
		return profile;
	}
}
