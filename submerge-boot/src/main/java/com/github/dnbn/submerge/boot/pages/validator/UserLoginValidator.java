package com.github.dnbn.submerge.boot.pages.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.boot.model.User;
import com.github.dnbn.submerge.boot.service.UserService;

@Component("userLoginValidator")
@Scope(value = "request")
public class UserLoginValidator implements Validator {

	private static final String PWD_FIELD = "cpntPasswordLogin";
	private static final String INCORRECT_USER_LOGIN = "Incorrect username or password";
	private static final String MISSING_INFORMATION = "Username or password missing";

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		UIInput cpntPasswordLogin = (UIInput) component.getAttributes().get(PWD_FIELD);
		String userName = (String) cpntPasswordLogin.getValue();
		String password = (String) value;

		if (password == null || StringUtils.isEmpty(userName)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, MISSING_INFORMATION, null));
		}

		User result = this.userService.findByName(userName);
		if (result == null || !this.passwordEncoder.matches(password, result.getPassword())) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INCORRECT_USER_LOGIN, null));
		}
	}

}
