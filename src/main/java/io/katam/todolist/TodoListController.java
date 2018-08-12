package io.katam.todolist;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.katam.user.TodoUser;

@RestController
public class TodoListController {

	@Autowired
	private TodoListService todoListService;
	
	// get all todolist
	@RequestMapping("/users/{userid}/todolists")
	public List<TodoList> getAllUsers(@PathVariable Long userid) {
//		System.out.println("GET request geldi");
		return todoListService.getAllTodoList(userid);
	}
	
	//get single todolist
	@RequestMapping("/users/{userId}/todolists/{todolistId}")
	public Optional<TodoList> getUser(@PathVariable Long todolistId) {
		return todoListService.getTodoList(todolistId);		
	}
	
	// add todolist
	@RequestMapping(method=RequestMethod.POST, value="/users/{userId}/todolists")
	public void addTodoList(@RequestBody TodoList todoList, @PathVariable Long userId) {
		TodoUser postUser = new TodoUser(userId,"","","");
		todoList.setUser(postUser);
		todoListService.addTodoList(todoList);
	}
	
	// update user
	@RequestMapping(method=RequestMethod.PUT, value="/users/{userId}/todolists/{todolistId}")
	public void updateTodoList( @RequestBody TodoList todoList, @PathVariable Long userId, @PathVariable Long todolistId) {
		TodoUser postUser = new TodoUser(userId,"","","");
		todoList.setUser(postUser);
		todoListService.updateTodoList(todoList);
	}	 
	
	// delete user
	@RequestMapping(method=RequestMethod.DELETE, value="users/{userId}/todolists/{todolistId}")
	public void deleteTodoList(@PathVariable Long userId, @PathVariable Long todolistId) {
		todoListService.deleteTodoList(todolistId); 
	}
	
	
}
