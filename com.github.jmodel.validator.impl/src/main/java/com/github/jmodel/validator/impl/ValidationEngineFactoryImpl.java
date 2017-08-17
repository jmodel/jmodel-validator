package com.github.jmodel.validator.impl;


import com.github.jmodel.validator.spi.ValidationEngineFactory;
import com.github.jmodel.validator.api.ValidationEngine;

public class ValidationEngineFactoryImpl implements ValidationEngineFactory {

	@Override
	public ValidationEngine getEngine() {
		return new ValidationEngineImpl();
	}
}
