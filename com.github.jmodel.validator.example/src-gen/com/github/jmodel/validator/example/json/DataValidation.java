package com.github.jmodel.validator.example.json;

import com.github.jmodel.ModelException;
import com.github.jmodel.api.domain.Model;
import com.github.jmodel.validator.Result;
import com.github.jmodel.validator.api.domain.Validation;
import java.util.Map;

@SuppressWarnings("all")
public class DataValidation extends Validation {
  private static Validation instance;
  
  public static synchronized Validation getInstance() {
    if (instance == null) {
    	instance = new com.github.jmodel.validator.example.json.DataValidation();
    	
    	instance.init(instance);
    }	
    
    return instance;
    
  }
  
  @Override
  public void init(final Validation myInstance) {
    super.init(myInstance);
    myInstance.setFormat(com.github.jmodel.FormatEnum.JSON);														
    
    com.github.jmodel.api.domain.Entity rootModel = new com.github.jmodel.api.domain.Entity();
    myInstance.setTemplateModel(rootModel);
    
    			
    
    
    myInstance.getRawFieldPaths().add("Content._");
    
    	myInstance.getRawFieldPaths().add("Content.Name");
    
    
  }
  
  @Override
  public void execute(final Model model, final Map serviceArgsMap, final Map myVariablesMap, final Result result) throws ModelException {
    super.execute(model, serviceArgsMap, myVariablesMap, result);
    {
    {
    if ((com.github.jmodel.api.utils.ModelHelper.predict(com.github.jmodel.api.utils.ModelHelper.getFieldValue(model.getFieldPathMap().get("Content.Name")),(Comparable)null,com.github.jmodel.api.utils.OperationEnum.EQUALS))) {
      result.getMessages().add("Name can not be null");
    }
    }
    }
  }
}
