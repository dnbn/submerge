package com.github.dnbn.submerge.web.service.impl;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.dnbn.submerge.web.model.UserBO;
import com.github.dnbn.submerge.web.model.entity.PersistentLogin;
import com.github.dnbn.submerge.web.service.UserService;
import com.github.dnbn.submerge.web.service.impl.converter.UserConverter;

@Component("persistentTokenRepositoryServiceImpl")
@Transactional
public class PersistentTokenRepositoryServiceImpl implements PersistentTokenRepository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private transient UserService userService;

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		
		PersistentLogin persistentLogin = new PersistentLogin();
		UserBO user = this.userService.findByName(token.getUsername());
		persistentLogin.setSeries(token.getSeries());
		persistentLogin.setToken(token.getTokenValue());
		persistentLogin.setUser(UserConverter.convert(user));
		persistentLogin.setLastUsed(token.getDate());
		
		this.sessionFactory.getCurrentSession().save(persistentLogin);
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String seriesId) {
		
		PersistentRememberMeToken rememberMeToken = null;
		PersistentLogin persistentLogin = getPersistantLoginsFromSeriesId(seriesId);
		if (persistentLogin != null) {
			rememberMeToken = new PersistentRememberMeToken(persistentLogin.getUser().getName(),
					persistentLogin.getSeries(), persistentLogin.getToken(), persistentLogin.getLastUsed());
		}
		return rememberMeToken;
	}

	@Override
	public void removeUserTokens(String username) {
		
		UserBO user = this.userService.findByName(username);
		if (user != null) {
			this.sessionFactory.getCurrentSession()
			.createQuery("delete from PersistentLogin pl where pl.user.id = :id")
			.setInteger("id", new Integer(user.getId()))
			.executeUpdate();
		}
	}

	@Override
	public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
		
		PersistentLogin persistentLogin = getPersistantLoginsFromSeriesId(seriesId);
		if (persistentLogin != null) {
			persistentLogin.setToken(tokenValue);
			persistentLogin.setLastUsed(lastUsed);
			this.sessionFactory.getCurrentSession().update(persistentLogin);
		}
	}

	private PersistentLogin getPersistantLoginsFromSeriesId(String seriesId) {
		
		return (PersistentLogin) this.sessionFactory.getCurrentSession()
				.createCriteria(PersistentLogin.class)
				.add(Restrictions.eq("series", seriesId))
				.uniqueResult();
	}

}
