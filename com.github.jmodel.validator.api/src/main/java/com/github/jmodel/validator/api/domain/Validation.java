package com.github.jmodel.validator.api.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.jmodel.FormatEnum;
import com.github.jmodel.ModelException;
import com.github.jmodel.api.entity.Model;
import com.github.jmodel.validator.Result;

public abstract class Validation {

	protected final static DateFormat dateFormat = new SimpleDateFormat();

	private FormatEnum format;

	private Model templateModel;

	private boolean isTemplateReady;

	private List<String> rawVariables = new ArrayList<String>();

	private List<String> rawFieldPaths = new ArrayList<String>();

	private Map<String, Boolean> modelRecursiveMap = new HashMap<String, Boolean>();

	private List<String> serviceList = new ArrayList<String>();

	private boolean isSuccess;

	private List<String> messages = new ArrayList<String>();

	public FormatEnum getFormat() {
		return format;
	}

	public void setFormat(FormatEnum format) {
		this.format = format;
	}

	public Model getTemplateModel() {
		return templateModel;
	}

	public void setTemplateModel(Model templateModel) {
		this.templateModel = templateModel;
	}

	public boolean isTemplateReady() {
		return isTemplateReady;
	}

	public void setTemplateReady(boolean isTemplateReady) {
		this.isTemplateReady = isTemplateReady;
	}

	public List<String> getRawVariables() {
		return rawVariables;
	}

	public void setRawVariables(List<String> rawVariables) {
		this.rawVariables = rawVariables;
	}

	public List<String> getRawFieldPaths() {
		return rawFieldPaths;
	}

	public void setRawFieldPaths(List<String> rawFieldPaths) {
		this.rawFieldPaths = rawFieldPaths;
	}

	public Map<String, Boolean> getModelRecursiveMap() {
		return modelRecursiveMap;
	}

	public void setModelRecursiveMap(Map<String, Boolean> modelRecursiveMap) {
		this.modelRecursiveMap = modelRecursiveMap;
	}

	public List<String> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public void init(final Validation myInstance) {

	}

	public void execute(final Model model, final Map<String, List<String>> serviceArgsMap,
			final Map<String, Object> myVariablesMap, final Result result) throws ModelException {

	}

}
