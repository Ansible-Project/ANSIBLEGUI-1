package com.v3devopsautomation.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.v3devopsautomation.model.SingleServer;

public class SingleServerValidator implements Validator {

	   @Override
	   public boolean supports(Class<?> clazz) {
	      return SingleServer.class.isAssignableFrom(clazz);
	   }

	   @Override
	   public void validate(Object target, Errors errors) {		
	      
	      SingleServer hostNames = (SingleServer) target;
	      if(hostNames.getGroupname().equals("NONE")){
	    	  errors.rejectValue("groupname", "groupname.required","Field groupname is required.");
	      }
	      if(hostNames.getHostname().equals("NONE")){
	    	  errors.rejectValue("hostname", "Hostname.required","Field Hostname is required.");
	      }
	      if(hostNames.getRolename().equals("NONE")){
	    	  errors.rejectValue("rolename", "rolename.required","Field rolename is required.");
	      }
	   }
	}
