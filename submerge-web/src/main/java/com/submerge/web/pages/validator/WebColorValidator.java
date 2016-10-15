package com.submerge.web.pages.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.submerge.web.constant.Regex;

@FacesValidator(value = "webColorValidator")
@RequestScoped
public class WebColorValidator implements Validator {

	private static final String HEX_PATTERN = Regex.COLOR.toString();
	private static final String INVALID_HEX_COLOR = "Invalid color";

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		Pattern pattern = Pattern.compile(HEX_PATTERN);
		Matcher matcher = pattern.matcher((String) value);

		if (!matcher.matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_HEX_COLOR, null));
		}

	}

}
