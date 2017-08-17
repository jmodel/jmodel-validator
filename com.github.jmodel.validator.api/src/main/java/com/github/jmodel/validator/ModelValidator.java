package com.github.jmodel.validator;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.github.jmodel.api.ModelException;
import com.github.jmodel.validator.api.ValidationEngine;
import com.github.jmodel.validator.api.ValidationEngineFactoryService;
import com.github.jmodel.validator.api.domain.Validation;

/**
 * Model validator.
 * 
 * @author jianni@hotmail.com
 *
 */
public class ModelValidator {

	private final static Logger logger = Logger.getLogger(ModelValidator.class.getName());

	private static String NAME_PATTERN = "([a-zA-Z_][a-zA-Z\\d_]*\\.)*[a-zA-Z_][a-zA-Z\\d_]*";

	private static ResourceBundle messages = ResourceBundle.getBundle("com.github.jmodel.mapper.api.MessagesBundle");

	public static <T> Result check(T sourceObj, String validationURI) {
		return check(sourceObj, validationURI, null);
	}

	public static <T> Result check(T sourceObj, String validationURI, Map<String, Object> argsMap) {

		final Validation validation = getValidation(validationURI);
		logger.info(() -> "The validation is constructed : " + validationURI);

		ValidationEngine validationEngine = ValidationEngineFactoryService.getInstance().getValidationEngine();
		logger.info(() -> "The validation engine is found : " + validationEngine);

		return validationEngine.check(sourceObj, validation, argsMap);

	}

	private static Validation getValidation(String validationURI) {

		if (validationURI == null || !Pattern.matches(NAME_PATTERN, validationURI)) {
			throw new ModelException(messages.getString("M_NAME_IS_ILLEGAL"));
		}

		// TODO consider more loading mechanism later, local or remote
		Class<?> validationClz;
		try {
			validationClz = Class.forName(validationURI);
		} catch (ClassNotFoundException e) {
			throw new ModelException(messages.getString("M_IS_MISSING"));
		}

		Validation validation = null;
		try {
			Method method = validationClz.getMethod("getInstance");
			validation = (Validation) (method.invoke(null));
			return validation;
		} catch (Exception e) {
			throw new ModelException(messages.getString("M_IS_ILLEGAL"));
		}

	}

}
