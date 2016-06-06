package com.submerge.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "confirmPasswordValidator")
@RequestScoped
public class ConfirmPasswordValidator implements Validator {

	private static final String PWD_FIELD = "cpntPassword";
	private static final String PWD_ARE_DIFFERENTS = "Passwords are not the same";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		UIInput cpntPassword = (UIInput) component.getAttributes().get(PWD_FIELD);
		String password = (String) cpntPassword.getValue();
		String confirm = (String) value;

		if (confirm != null && !confirm.equals(password)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, PWD_ARE_DIFFERENTS, null));
		}

	}

}
