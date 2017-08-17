package com.github.jmodel.validator.api;

import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import com.github.jmodel.validator.spi.ValidationEngineFactory;

public class ValidationEngineFactoryService {

	private static ValidationEngineFactoryService service;
	private ServiceLoader<ValidationEngineFactory> loader;

	private ValidationEngineFactoryService() {
		loader = ServiceLoader.load(ValidationEngineFactory.class);
	}

	public static synchronized ValidationEngineFactoryService getInstance() {
		if (service == null) {
			service = new ValidationEngineFactoryService();
		}
		return service;
	}

	public ValidationEngine getValidationEngine() {
		ValidationEngine validationEngine = null;

		try {
			Iterator<ValidationEngineFactory> engineFactorys = loader.iterator();
			while (validationEngine == null && engineFactorys.hasNext()) {
				ValidationEngineFactory engineFactory = engineFactorys.next();
				validationEngine = engineFactory.getEngine();
			}
		} catch (ServiceConfigurationError serviceError) {
			validationEngine = null;
			serviceError.printStackTrace();

		}
		return validationEngine;
	}
}
