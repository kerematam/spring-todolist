package io.katam.todolist;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.katam.user.TodoUser;


@Entity 
public class TodoList {

	@GeneratedValue @Id private Long id;
	private String name;

	@ManyToOne
	private TodoUser todoUser;
	
	public TodoList() {}
	
    public TodoList(Long id, String name, Long todoUserId) {
        super();
        
        if(id != null)
        	this.id = id;       
        
        this.name = name;
        this.todoUser = new TodoUser(todoUserId, "","","");
 
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public TodoUser getUser() {
		return todoUser;
	}

	public void setUser(TodoUser todoUser) {
		this.todoUser = todoUser;
	}


}
