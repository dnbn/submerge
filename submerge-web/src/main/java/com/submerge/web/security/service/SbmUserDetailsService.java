package com.submerge.web.security.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.submerge.web.model.entity.User;
import com.submerge.web.pages.bean.model.proxy.AuthenticatedUser;
import com.submerge.web.pages.bean.model.proxy.SubConfig;
import com.submerge.web.security.model.SbmUserDetails;
import com.submerge.web.service.UserService;

@Service("sbmUserDetailsService")
public class SbmUserDetailsService implements UserDetailsService {

	@Autowired
	private transient UserService userService;

	@Autowired
	private AuthenticatedUser authenticatedUser;

	@Autowired
	private SubConfig subConfig;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		final User user = this.userService.findByName(login);
		UserDetails details = new SbmUserDetails(user);
		if (user == null) {
			throw new UsernameNotFoundException("User not found : " + login);
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(details, null, details.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Update the user last login info
		user.setLastLogin(Calendar.getInstance().getTime());
		this.userService.save(user);

		// Update the user in session
		this.authenticatedUser.setUser(user);

		// Update profiles in session
		this.subConfig.setConfigs(user.getDualSubtitleConfigs(), user.getSimpleAssConfig());

		return details;
	}

}
