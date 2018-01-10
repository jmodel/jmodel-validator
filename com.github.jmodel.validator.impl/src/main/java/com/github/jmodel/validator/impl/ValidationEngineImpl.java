package com.github.jmodel.validator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.github.jmodel.ModelBuilder;
import com.github.jmodel.ModelException;
import com.github.jmodel.api.entity.Model;
import com.github.jmodel.utils.ModelHelper;
import com.github.jmodel.validator.Result;
import com.github.jmodel.validator.api.ValidationEngine;
import com.github.jmodel.validator.api.domain.Validation;

public class ValidationEngineImpl implements ValidationEngine {

	private final static Logger logger = Logger.getLogger(ValidationEngineImpl.class.getName());

	@Override
	public <T> Result check(T sourceObj, Validation validation, Map<String, Object> argsMap) throws ModelException {

		Result result = new Result();

		if (argsMap != null) {
			checkVariables(validation, argsMap);
			logger.log(Level.INFO, () -> "variable is checked");
		}

		Model templateModel = validation.getTemplateModel();

		if (!validation.isTemplateReady()) {
			ModelHelper.populateModel(templateModel, validation.getRawFieldPaths(), validation.getModelRecursiveMap());
			validation.setTemplateReady(true);
		}

		Model model = templateModel.clone();
		ModelBuilder.update(validation.getFormat(), model, sourceObj);

		Map<String, List<String>> serviceArgsMap = new HashMap<String, List<String>>();
		List<String> serviceList = validation.getServiceList();
		for (String service : serviceList) {
			serviceArgsMap.put(service, new ArrayList<String>());
		}

		validation.execute(model, serviceArgsMap, argsMap, result);
		if (result.getMessages().size() > 0) {
			result.setSuccess(false);
		} else {
			result.setSuccess(true);
		}
		return result;
	}

	private void checkVariables(final Validation validation, Map<String, Object> argsMap) throws ModelException {

		if (validation.getRawVariables().size() > 0) {
			if (argsMap == null || argsMap.size() == 0) {
				throw new ModelException("Variable is not found");
			}
			if (argsMap.keySet().parallelStream().filter(s -> validation.getRawVariables().contains(s)).count() == 0) {
				throw new ModelException("Variable is not found");
			}
		}
	}

}
