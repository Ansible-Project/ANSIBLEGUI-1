package com.v3devopsautomation.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.v3devopsautomation.model.HostNames;
@Repository
@Transactional
public class HostNameDAO {
	@PersistenceContext 
	private EntityManager em;
	 public String insertGroup(HostNames hostname) {
	        //em.getTransaction().commit();
	        List<HostNames> check= validateGroup(hostname.getHostname(), hostname.getGroupname());
	        if(check.size()==1)
	        {
	        	System.out.println("User details registered");
	        	return "Already Register";
	        }
	        else
	        {
	        	em.persist(hostname);
	        	return "Successfully register";
	        	
	        }
	    } 
	 public List<HostNames> validateGroup(String hostname, String groupname)
	    {
	    	Query query = em.createQuery(
		            "FROM HostNames where hostname=:hostname and groupname=:groupname");
	    	query.setParameter("hostname", hostname);
	    	query.setParameter("groupname", groupname);
	    		
		        return query.getResultList();
	    
	    }
	 public List<HostNames> getIPList(String groupname)
	    {
	    	Query query = em.createQuery(
		            "FROM HostNames where groupname=:groupname");
	    	query.setParameter("groupname", groupname);
	    		
		        return query.getResultList();
	    
	    }

}
