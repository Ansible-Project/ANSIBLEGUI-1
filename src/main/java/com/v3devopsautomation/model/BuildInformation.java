package com.v3devopsautomation.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="buildinformation")
@Entity
public class BuildInformation implements Serializable{
	  private static final long serialVersionUID = 1L;
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  @Column(name="id")
	  private int id;
	  @Column(name="buildid")
	  private String buildid;
	  @Column(name="loglocation")
	  private String loglocation;
	  @Column(name="buildstarttime")
	  private String buildstarttime;
	  @Column(name="buildendtime")
	  private String buildendtime;
	  
	public String getBuildid() {
		return buildid;
	}
	public void setBuildid(String buildid) {
		this.buildid = buildid;
	}
	public String getLoglocation() {
		return loglocation;
	}
	public void setLoglocation(String loglocation) {
		this.loglocation = loglocation;
	}
	public String getBuildstarttime() {
		return buildstarttime;
	}
	public void setBuildstarttime(String buildstarttime) {
		this.buildstarttime = buildstarttime;
	}
	public String getBuildendtime() {
		return buildendtime;
	}
	public void setBuildendtime(String buildendtime) {
		this.buildendtime = buildendtime;
	}  
}
