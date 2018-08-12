package io.katam.todolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoListService {

	@Autowired
	private TodoListRepository todoListRepository;
	
//	private TodoUser user_1 = new TodoUser(1,"kerem","kerematam@gmail.com","123123");
//	private TodoUser user_2 = new TodoUser(2,"mehmet","mehmet@gmail.com","123123"); 
//	private TodoUser user_3 = new TodoUser(3,"ali","ali@gmail.com","123123"); 
//	private List<TodoUser> todoUsers = Arrays.asList(user_1,user_2,user_3);
		

	
	public List<TodoList> getAllTodoList(Long userId) {
		// TODO Auto-generated method stub
		//return todoUsers;
	//	List<TodoList> todoLists = new ArrayList<>();
	//	todoListRepository.findByUserId(userId).forEach(todoLists::add);
	//	return todoLists;
		
		return todoListRepository.findByTodoUserId(userId);
	}
	
	public Optional<TodoList> getTodoList(Long todolistId) {
		// TODO Auto-generated method stub
		return todoListRepository.findById(todolistId);
	}
	
	public void addTodoList(TodoList todoList) {
		todoListRepository.save(todoList);
	}
		
	public void deleteTodoList(Long todolistId) {
		todoListRepository.deleteById(todolistId);
	}

	public void updateTodoList(TodoList todoList) {
		todoListRepository.save(todoList);		
	}



}
