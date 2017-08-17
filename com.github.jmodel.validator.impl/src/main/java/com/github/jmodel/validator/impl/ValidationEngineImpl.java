package com.github.jmodel.validator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.github.jmodel.ModelBuilder;
import com.github.jmodel.api.ModelException;
import com.github.jmodel.api.domain.Array;
import com.github.jmodel.api.domain.Entity;
import com.github.jmodel.api.domain.Field;
import com.github.jmodel.api.domain.Model;
import com.github.jmodel.validator.Result;
import com.github.jmodel.validator.api.ValidationEngine;
import com.github.jmodel.validator.api.domain.Validation;

public class ValidationEngineImpl implements ValidationEngine {

	protected ResourceBundle messages;

	@Override
	public <T> Result check(T sourceObj, Validation validation, Map<String, Object> argsMap) {

		Result result = new Result();

		messages = ResourceBundle.getBundle("com.github.jmodel.validation.api.MessagesBundle");

		if (argsMap != null) {
			checkVariables(validation, argsMap);
		}

		Model templateModel = validation.getTemplateModel();

		if (!validation.isTemplateReady()) {
			populateModel(templateModel, validation.getRawFieldPaths());
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

	private void populateModel(final Model root, final List<String> fieldPaths) {
		final Map<String, Object> modelOrFieldMap = new HashMap<String, Object>();

		for (String fieldPath : fieldPaths) {

			String[] paths = fieldPath.split("\\.");

			String currentPath = "";
			String parentPath = "";

			for (int i = 0; i < paths.length - 1; i++) {

				if (parentPath.equals("")) {
					currentPath = paths[i];
				} else {
					currentPath = parentPath.replace("[]", "[0]") + "." + paths[i];
				}

				Model currentModel = (Model) modelOrFieldMap.get(currentPath);
				if (currentModel == null) {
					// create model object
					if (parentPath.equals("")) {
						currentModel = root;
					} else {
						if (paths[i].indexOf("[]") != -1) {
							currentModel = new Array();
							currentModel.setName(StringUtils.substringBefore(paths[i], "[]"));
						} else {
							currentModel = new Entity();
							currentModel.setName(paths[i]);
						}
					}
					if (currentModel.getName() == null) {
						currentModel.setName(paths[i]);
					}
					currentModel.setModelPath(currentPath);
					modelOrFieldMap.put(currentPath, currentModel);

					// if current Model is Array,create a existing entity
					if (currentModel instanceof Array) {
						Entity subEntity = new Entity();
						String entityName = StringUtils.substringBefore(paths[i], "[]");
						subEntity.setName(entityName);
						currentPath = currentPath.replace("[]", "[0]");
						subEntity.setModelPath(currentPath);
						subEntity.setParentModel(currentModel);
						currentModel.getSubModels().add(subEntity);
						modelOrFieldMap.put(currentPath, subEntity);

					}

					// maintenence parent model relation
					Model parentModel = (Model) modelOrFieldMap.get(parentPath.replaceAll("\\[\\]", "\\[0\\]"));
					if (parentModel != null) {
						currentModel.setParentModel(parentModel);
						List<Model> subModelList = parentModel.getSubModels();
						if (subModelList == null) {
							subModelList = new ArrayList<Model>();
							parentModel.setSubModels(subModelList);
						}
						subModelList.add(currentModel);

					}

				}
				// set parentPath
				parentPath = currentPath;
			}

			// set field list
			String fieldName = paths[paths.length - 1];
			if (!fieldName.equals("_")) {
				currentPath = currentPath + "." + fieldName;
				Field currentField = (Field) modelOrFieldMap.get(currentPath);
				if (currentField == null) {
					currentField = new Field();
					currentField.setName(fieldName);
					modelOrFieldMap.put(currentPath, currentField);
					Entity currentModel = null;
					Object model = modelOrFieldMap.get(parentPath);
					if (model instanceof Entity) {
						currentModel = (Entity) model;
					} else if (model instanceof Array) {
						Array aModel = (Array) model;
						currentModel = (Entity) aModel.getSubModels().get(0);
					}
					List<Field> fieldList = currentModel.getFields();
					if (fieldList == null) {
						fieldList = new ArrayList<Field>();
						currentModel.setFields(fieldList);
					}
					fieldList.add(currentField);
				}
			}
		}
	}

	private void checkVariables(final Validation validation, Map<String, Object> argsMap) throws ModelException {
		if (validation.getRawVariables().size() > 0) {
			if (argsMap == null || argsMap.size() == 0) {
				throw new ModelException(messages.getString("V_NOT_FOUND"));
			}
			if (argsMap.keySet().parallelStream().filter(s -> validation.getRawVariables().contains(s)).count() == 0) {
				throw new ModelException(messages.getString("V_NOT_FOUND"));
			}
		}
	}

}
