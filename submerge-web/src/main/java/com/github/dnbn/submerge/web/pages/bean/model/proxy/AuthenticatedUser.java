package com.github.dnbn.submerge.web.pages.bean.model.proxy;

import java.io.Serializable;

import com.github.dnbn.submerge.web.model.UserBO;

public interface AuthenticatedUser extends Serializable {

	public UserBO getUser();

	public void setUser(UserBO user);

}
