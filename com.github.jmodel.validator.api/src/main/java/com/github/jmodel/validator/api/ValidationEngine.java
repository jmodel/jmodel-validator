package com.github.jmodel.validator.api;

import java.util.Map;

import com.github.jmodel.validator.Result;
import com.github.jmodel.validator.api.domain.Validation;

public interface ValidationEngine {

	public <T> Result check(T sourceObj, Validation validation, Map<String, Object> argsMap);

}
