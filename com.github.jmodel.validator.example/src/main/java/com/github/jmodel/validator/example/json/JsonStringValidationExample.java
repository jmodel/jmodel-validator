package com.github.jmodel.validator.example.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jmodel.validator.ModelValidator;
import com.github.jmodel.validator.Result;

public class JsonStringValidationExample {

	public static void main(String[] args) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode data = objectMapper.readTree("{\"Content\": {\"Name\": null}}");
			Result r = ModelValidator.check(data, "com.github.jmodel.validator.example.json.DataValidation");
			if (r.isSuccess()) {
				System.out.println("pass");
			} else {
				System.out.println("failed");
				for (String msg : r.getMessages()) {
					System.out.println(msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
