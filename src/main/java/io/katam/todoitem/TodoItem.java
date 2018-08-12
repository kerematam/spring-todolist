package io.katam.todoitem;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.katam.todolist.TodoList;
import io.katam.user.TodoUser;


@Entity 
public class TodoItem {

	@Id @GeneratedValue private Long id;
	private String name;
	
	@ManyToOne
	private TodoList todoList;

	private long epochTime;
	private Boolean isDone = false;
	
	public TodoItem() {}
	
    public TodoItem(Long id, String name, Long todoListId) {
        super();
        
        if(id != null)
        	this.id = id;
        
        this.name = name;
        this.setTodoList(new TodoList(todoListId, "",null));
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

	public TodoList getTodoList() {
		return todoList;
	}

	public void setTodoList(TodoList todoList) {
		this.todoList = todoList;
	}

	public Boolean getIsDone() {
		return isDone;
	}

	public void setIsDone(Boolean isDone) {
		this.isDone = isDone;
	}

	public long getEpochTime() {
		return epochTime;
	}

	public void setEpochTime(long epochTime) {
		this.epochTime = epochTime;
	}



}
