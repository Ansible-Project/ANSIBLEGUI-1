package com.v3devopsautomation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.v3devopsautomation.model.HostNames;



public class HostNameValidator implements Validator {

	   @Override
	   public boolean supports(Class<?> clazz) {
	      return HostNames.class.isAssignableFrom(clazz);
	   }

	   @Override
	   public void validate(Object target, Errors errors) {		
	      ValidationUtils.rejectIfEmptyOrWhitespace(errors, 
	         "hostname", "required.hostname","Field hostname is required."); 
	      HostNames hostNames = (HostNames) target;
	      if(hostNames.getGroupname().equals("NONE")){
	    	  errors.rejectValue("groupname", "groupname.required","Field groupname is required.");
	      }
	   }
	}
