package com.v3devopsautomation.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.v3devopsautomation.model.BuildInformation;



@Repository
@Transactional
public class BUILDDAO {
	@PersistenceContext 
	private EntityManager em;
	 public String insertBuildInfo(BuildInformation buildinformation) {
	        //em.getTransaction().commit();
	        List<BuildInformation> check= validateBUILD(buildinformation.getBuildid());
	        if(check.size()==1)
	        {
	        	System.out.println("BUILD ALREADY STARTED");
	        	return "Already Register";
	        }
	        else
	        {
	        	em.persist(buildinformation);
	        	return "Successfully register";
	        	
	        }
	    } 
	 public List<BuildInformation> validateBUILD(String buildid)
	    {
	    	Query query = em.createQuery(
		            "FROM BuildInformation where buildid=:buildid");
	    	query.setParameter("buildid", buildid);
	    		
		        return query.getResultList();
	    
	    }
	 public List<BuildInformation> allBuildInformation()
	    {
	    	Query query = em.createQuery(
		            "FROM BuildInformation ");
	    	//query.setParameter("groupname", groupname);
	    		
		        return query.getResultList();
	    
	    }

}
