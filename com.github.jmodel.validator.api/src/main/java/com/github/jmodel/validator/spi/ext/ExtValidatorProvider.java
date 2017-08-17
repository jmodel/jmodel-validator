package com.github.jmodel.validator.spi.ext;

import com.github.jmodel.validator.api.ext.ExtValidator;

public interface ExtValidatorProvider {

	public ExtValidator getValidator(String validatorName);
}
