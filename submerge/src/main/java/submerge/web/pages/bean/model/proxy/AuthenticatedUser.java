package com.submerge.web.bean.model.proxy;

import java.io.Serializable;

import com.submerge.model.entity.User;

public interface AuthenticatedUser extends Serializable {

	public User getUser();

	public void setUser(User user);

}
