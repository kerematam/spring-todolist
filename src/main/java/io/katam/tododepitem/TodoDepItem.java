package io.katam.tododepitem;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.katam.todoitem.TodoItem;
import io.katam.todolist.TodoList;
import io.katam.user.TodoUser;


@Entity 
public class TodoDepItem {

	@Id @GeneratedValue private Long id;
	
	@ManyToOne
	private TodoItem todoItem;
	
	@ManyToOne
	private TodoItem todoDepItem;
	
	public TodoDepItem() {}
	
    public TodoDepItem(Long id, String name, Long todoItemId, Long todoDepItemId) {
        super();
        this.id = id;
        this.setTodoItem(new TodoItem(todoItemId, "",null));
        this.setTodoDepItem(new TodoItem(todoDepItemId, "",null));
    }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TodoItem getTodoItem() {
		return todoItem;
	}

	public void setTodoItem(TodoItem todoItem) {
		this.todoItem = todoItem;
	}

	public TodoItem getTodoDepItem() {
		return todoDepItem;
	}

	public void setTodoDepItem(TodoItem todoDepItem) {
		this.todoDepItem = todoDepItem;
	}


    




}
