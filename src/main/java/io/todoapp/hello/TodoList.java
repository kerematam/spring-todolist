package io.todoapp.hello;

import org.springframework.data.annotation.Id;

public class TodoList {

	@Id private String id;
	private String userName;
	private String emailAddress;
	private String password;
	
}
