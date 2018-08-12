package io.katam.todoitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoItemService {

	@Autowired
	private TodoItemRepository todoItemRepository;
	
//	private TodoUser user_1 = new TodoUser(1,"kerem","kerematam@gmail.com","123123");
//	private TodoUser user_2 = new TodoUser(2,"mehmet","mehmet@gmail.com","123123"); 
//	private TodoUser user_3 = new TodoUser(3,"ali","ali@gmail.com","123123"); 
//	private List<TodoUser> todoUsers = Arrays.asList(user_1,user_2,user_3);
		
	public List<TodoItem> getAllTodoItems(Long todolistid) {
		// TODO Auto-generated method stub
		//return null;
		
		return todoItemRepository.findByTodoListId(todolistid);
	}
	

	
	
	public Optional<TodoItem> getTodoItem(Long todoitemId) {
		return todoItemRepository.findById(todoitemId);
	}
	
	
	public void addTodoItem(TodoItem todoItem) {
		todoItemRepository.save(todoItem);
	}

	public void updateTodoItem(TodoItem todoItem) {
		todoItemRepository.save(todoItem);		
	}
		
	public void deleteTodoItem(Long todoitemtId) {
		todoItemRepository.deleteById(todoitemtId);
	}











}
