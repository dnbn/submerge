package com.github.dnbn.submerge.web.service.impl.converter;

import java.util.stream.Collectors;

import com.github.dnbn.submerge.web.model.AccountState;
import com.github.dnbn.submerge.web.model.AuthorityBO;
import com.github.dnbn.submerge.web.model.UserBO;
import com.github.dnbn.submerge.web.model.entity.AccountStatus;
import com.github.dnbn.submerge.web.model.entity.User;

public class UserConverter {

	public static UserBO convert(User user) {

		if (user == null) {
			return null;
		}
		
		UserBO userBO = new UserBO();
		AccountState status = AccountState.getById(user.getAccountStatus().getId());

		userBO.setAccountStatus(status);
		userBO.setCreation(user.getCreation());
		userBO.setEmail(user.getEmail());
		userBO.setId(user.getId());
		userBO.setLastLogin(user.getLastLogin());
		userBO.setName(user.getName());
		userBO.setPassword(user.getPassword());
		userBO.setLastUpdate(user.getLastUpdate());
		userBO.setSimpleAssConfig(SubtitleProfileConverter.convert(user.getSimpleAssConfig()));
		userBO.setDualSubtitleConfigs(DualSubtitleConfigConverter.convertToBO(user.getDualSubtitleConfigs()));
		userBO.setAuthorities(user.getUserAuthorities().stream()
				.map(uas -> new AuthorityBO(uas.getAuthorities().getName())).collect(Collectors.toList()));

		return userBO;
	}

	public static User convert(UserBO userBO) {

		if (userBO == null) {
			return null;
		}
		
		User user = new User();
		
		if (userBO.getAccountStatus() != null) {
			AccountStatus status = new AccountStatus();
			status.setId(userBO.getAccountStatus().getId());
			user.setAccountStatus(status);			
		}

		user.setCreation(userBO.getCreation());
		user.setEmail(userBO.getEmail());
		user.setId(userBO.getId());
		user.setLastLogin(userBO.getLastLogin());
		user.setName(userBO.getName());
		user.setPassword(userBO.getPassword());
		user.setLastUpdate(userBO.getLastUpdate());
		user.setSimpleAssConfig(SubtitleProfileConverter.convert(userBO.getSimpleAssConfig()));
		user.setDualSubtitleConfigs(DualSubtitleConfigConverter.convertBO(userBO.getDualSubtitleConfigs()));

		return user;
	}
}
