package com.submerge.web.bean.model.proxy;

import java.io.Serializable;

import com.submerge.model.entity.User;

public interface AuthenticatedUser extends Serializable {

	User getUser();

	void setUser(User user);

}
