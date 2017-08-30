package com.v3devopsautomation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.v3devopsautomation.model.GroupNames;


public class GorupNameValidator implements Validator {

	   @Override
	   public boolean supports(Class<?> clazz) {
	      return GroupNames.class.isAssignableFrom(clazz);
	   }

	   @Override
	   public void validate(Object target, Errors errors) {		
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
	         "groupname", "required.groupname","Field groupname is required.");  
	   }
	}
