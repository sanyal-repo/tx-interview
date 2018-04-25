package com.learnvest.nmlv.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name = "users") 
public class User {

  @Id
  private Long id;
  private String userid;
  private String permission;
  private String displayName;    


  protected User() {
  }
  
  private User(Long id, String userid, String displayName) {
	    this.id = id;
	    this.userid = userid;
	    this.displayName = displayName;
	  }

	  public Long getId() {
	    return id;
	  }

	  public String getUserId() {
	    return userid;
	  }

	  public String getDisplayName() {
	    return displayName;
	  }
	  
	  public Boolean isAdmin(){
		  return "admin".equals(this.permission);
	  }

  
}