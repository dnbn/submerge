package com.submerge.web.pages.bean.model.proxy;

import java.io.Serializable;

import com.submerge.web.model.UserBO;

public interface AuthenticatedUser extends Serializable {

	public UserBO getUser();

	public void setUser(UserBO user);

}
