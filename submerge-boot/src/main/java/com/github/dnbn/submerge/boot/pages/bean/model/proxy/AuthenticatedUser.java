package com.github.dnbn.submerge.boot.pages.bean.model.proxy;

import java.io.Serializable;

import com.github.dnbn.submerge.boot.model.User;

public interface AuthenticatedUser extends Serializable {

	User getUser();

	void setUser(User user);

}
