package com.github.dnbn.submerge.boot.service.impl;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.dnbn.submerge.boot.model.DualSubtitleConfig;
import com.github.dnbn.submerge.boot.model.SubtitleProfile;
import com.github.dnbn.submerge.boot.model.User;
import com.github.dnbn.submerge.boot.pages.bean.model.proxy.AuthenticatedUser;
import com.github.dnbn.submerge.boot.pages.bean.model.proxy.SubConfig;
import com.github.dnbn.submerge.boot.service.UserService;
import com.github.dnbn.submerge.boot.utils.ProfileUtils;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

public class UserServiceImpl implements UserService {

	@Autowired
	private DynamoDbEnhancedClient dynamodbClient;
		
	@Autowired
	private AuthenticatedUser authenticatedUser;

	@Autowired
	private SubConfig subConfig;

	@Override
	public User findByName(String name) {

		return getTable().getItem(Key.builder().partitionValue(name).build());
	}

	@Override
	public void create(User user) {

		Instant currDate = getCurrdate();

		// Set default sub profile if not set
		List<DualSubtitleConfig> subConfigs = user.getDualSubtitleConfigs();
		if (!subConfigs.stream().anyMatch(sc -> sc.isCurrent())) {
			DualSubtitleConfig dsc = new DualSubtitleConfig();
			dsc.setProfileOne(defaultProfile());
			dsc.setProfileTwo(defaultProfile());
			dsc.setLastUpdate(currDate);
			dsc.setAvoidSwitch(true);
			dsc.setCurrent(true);
			dsc.setClean(true);
			subConfigs.add(dsc);
			user.setDualSubtitleConfigs(subConfigs);
		}
		user.setLastUpdate(currDate);

		if (user.getSimpleAssConfig() == null) {
			user.setSimpleAssConfig(defaultProfile());
		}
		user.getSimpleAssConfig().setLastUpdate(currDate);

		if (StringUtils.isEmpty(user.getEmail())) {
			user.setEmail(null);
		}

		// Save user and profiles
		getTable().putItem(user);
	}

	@Override
	public User loadUserByUsername(String login) throws UsernameNotFoundException {

		User user = getTable().getItem(Key.builder().partitionValue(login).build());

		if (user == null) {
			throw new UsernameNotFoundException("User not found : " + login);
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		user.setLastLogin(getCurrdate());
		this.save(user);

		// Update the user in session
		this.authenticatedUser.setUser(user);

		// Update profiles in session
		this.subConfig.setConfigs(user.getDualSubtitleConfigs(), user.getSimpleAssConfig());

		return user;
	}

	public void save(User user) {

		Instant currDate = getCurrdate();
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

		getTable().putItem(user);
	}

	private static SubtitleProfile defaultProfile() {

		SubtitleProfile profile = new SubtitleProfile();
		profile.setLastUpdate(getCurrdate());
		ProfileUtils.initProfiles(profile);

		return profile;
	}

	private static Instant getCurrdate() {
		return ZonedDateTime.now(ZoneId.of("Europe/Paris")).toInstant();
	}

	private DynamoDbTable<User> getTable() {
		return dynamodbClient.table("submerge-users", TableSchema.fromBean(User.class));
	}
}
