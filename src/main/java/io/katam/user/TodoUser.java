package io.katam.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.katam.todolist.TodoList;


@Entity 
public class TodoUser {

	//@OneToMany(targetEntity = TodoUser.class, orphanRemoval = true, cascade=CascadeType.PERSIST )
	@GeneratedValue @Id private Long id;
	@Column(unique=true)
	private String username;
	private String password;
	private String email;
	
	public TodoUser() {}
	
    public TodoUser(Long id, String username, String email, String password) {
        //this.setId(id);
        
        if(id != null)
        	this.id = id;       
        
        this.username = username;
        this.email = email;
        this.password = password;
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}
