package com.github.dnbn.submerge.web.pages.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.github.dnbn.submerge.web.service.UserService;

@Component("emailNotExistValidator")
@Scope(value = "request")
public class EmailNotExistValidator implements Validator {

	private static final String EMAIL_EXISTS = "Email already exists";

	@Autowired
	private UserService userService;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String email = (String) value;
		if (this.userService.findByEmail(email) != null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_EXISTS, null));
		}
	}

}
