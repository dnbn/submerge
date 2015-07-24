package com.submerge.security.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.submerge.model.entity.User;

public interface SecurityUser extends Serializable {

	User getUser();

	void setUser(User user);

	void loginSuccess(HttpServletRequest request, HttpServletResponse response, boolean isRemember);

	void logout(HttpServletRequest request, HttpServletResponse response);

}
