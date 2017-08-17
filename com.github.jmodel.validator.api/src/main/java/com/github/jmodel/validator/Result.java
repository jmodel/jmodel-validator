package com.github.jmodel.validator;

import java.util.ArrayList;
import java.util.List;

public class Result {

	private boolean isSuccess;

	private List<String> messages = new ArrayList<String>();

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

}
