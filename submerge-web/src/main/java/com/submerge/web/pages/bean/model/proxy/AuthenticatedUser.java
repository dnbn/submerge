package com.submerge.web.pages.bean.model.proxy;

import java.io.Serializable;

import com.submerge.web.model.entity.User;

public interface AuthenticatedUser extends Serializable {

	public User getUser();

	public void setUser(User user);

}
