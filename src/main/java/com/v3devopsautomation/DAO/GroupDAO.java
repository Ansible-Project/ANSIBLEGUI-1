package com.v3devopsautomation.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.v3devopsautomation.model.GroupNames;


@Repository
@Transactional
public class GroupDAO {
	@PersistenceContext 
	private EntityManager em;
	 public String insertGroup(GroupNames groupname) {
	        //em.getTransaction().commit();
	        List<GroupNames> check= validateGroup(groupname.getGroupname());
	        if(check.size()==1)
	        {
	        	System.out.println("User details registered");
	        	return "Already Register";
	        }
	        else
	        {
	        	em.persist(groupname);
	        	return "Successfully register";
	        	
	        }
	    } 
	 public List<GroupNames> validateGroup(String groupname)
	    {
	    	Query query = em.createQuery(
		            "FROM GroupNames where groupname=:groupname");
	    	query.setParameter("groupname", groupname);
	    		
		        return query.getResultList();
	    
	    }
	 public List<GroupNames> allGroup()
	    {
	    	Query query = em.createQuery(
		            "FROM GroupNames ");
	    	//query.setParameter("groupname", groupname);
	    		
		        return query.getResultList();
	    
	    }
}
