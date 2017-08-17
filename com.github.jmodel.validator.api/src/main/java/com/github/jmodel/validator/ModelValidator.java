package com.github.jmodel.validator;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.github.jmodel.ModelException;
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

	public static <T> Result check(T sourceObj, String validationURI) throws ModelException {
		return check(sourceObj, validationURI, null);
	}

	public static <T> Result check(T sourceObj, String validationURI, Map<String, Object> argsMap)
			throws ModelException {

		final Validation validation = getValidation(validationURI);
		logger.info(() -> "The validation is constructed : " + validationURI);

		ValidationEngine validationEngine = ValidationEngineFactoryService.getInstance().getValidationEngine();
		logger.info(() -> "The validation engine is found : " + validationEngine);

		return validationEngine.check(sourceObj, validation, argsMap);

	}

	private static Validation getValidation(String validationURI) throws ModelException {

		if (validationURI == null || !Pattern.matches(NAME_PATTERN, validationURI)) {
			throw new ModelException("ValidationURI is wrong");
		}

		// TODO consider more loading mechanism later, local or remote
		Class<?> validationClz;
		try {
			validationClz = Class.forName(validationURI);
		} catch (ClassNotFoundException e) {
			throw new ModelException("Validation is not found");
		}

		Validation validation = null;
		try {
			Method method = validationClz.getMethod("getInstance");
			validation = (Validation) (method.invoke(null));
			return validation;
		} catch (Exception e) {
			throw new ModelException("Could not get instance of Validation", e);
		}

	}

}
