package com.github.jmodel.validator.spi;

import com.github.jmodel.validator.api.ValidationEngine;

public interface ValidationEngineFactory {

	public ValidationEngine getEngine();
}
