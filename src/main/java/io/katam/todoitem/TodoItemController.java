package io.katam.todoitem;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.katam.tododepitem.TodoDepItem;
import io.katam.tododepitem.TodoDepItemService;
import io.katam.todolist.TodoList;
import io.katam.user.TodoUser;
import io.katam.user.UserService;

@RestController
public class TodoItemController {

	@Autowired
	private TodoItemService todoItemService;
	
	@Autowired
	private TodoDepItemService todoDepItemService;
	
	
	@Autowired
	private UserService userService;
	
	
	// sample response entity usage
	@RequestMapping("/test")
	public ResponseEntity<String> get() {		
	    return new ResponseEntity<>("false", HttpStatus.OK);
	}
	
	
	// get all todoItem
	@RequestMapping("/users/{userid}/todolists/{todolistid}/items")
	public List<TodoItem> getAllTodoItems(@PathVariable Long todolistid) {
		
		//if(!userService.isAuth())
		//	return null;

		return todoItemService.getAllTodoItems(todolistid);
	}
	
	//get single todoItem
	@RequestMapping("/users/{userId}/todolists/{todolistId}/items/{todoitemId}")
	public Optional<TodoItem> getTodoItem(@PathVariable Long todoitemId) {
		return todoItemService.getTodoItem(todoitemId);		
	}
	
	
	
	// add todoItem
	@RequestMapping(method=RequestMethod.POST, 
			value="/users/{userId}/todolists/{todolistId}/items")
	public void addTodoItem(@RequestBody TodoItem todoItem,
			@PathVariable Long userId,
			@PathVariable Long todolistId) {
		TodoList postTodoList = new TodoList(todolistId,"",null);
		todoItem.setTodoList(postTodoList);
		todoItemService.addTodoItem(todoItem);
	}
	


	
	// update todoItem
	@RequestMapping(method=RequestMethod.PUT, 
			value="/users/{userId}/todolists/{todolistId}/items/{todoitemId}")
	public ResponseEntity<String> updateTodoItem( 
			@RequestBody TodoItem todoItem, 
			@PathVariable Long userId, 
			@PathVariable Long todolistId,
			@PathVariable Long todoitemId) {
		
	//	System.out.println("todoItem.getIsDone()");
	//	System.out.println(todoItem.getIsDone());
		
		
		// request for done event
		if(todoItem.getIsDone()) {
			// check if any dependency
			//System.out.println()
			
			List<TodoDepItem> deplist = todoDepItemService.getAllTodoDepItems(todoitemId);
			System.out.println(deplist);
			
			for(TodoDepItem depItem : deplist) {
				
				if(!depItem.getTodoDepItem().getIsDone())
					return new ResponseEntity<>("false", HttpStatus.OK);
				
				/*
	            System.out.println(depItem.getId());
	            
	            Optional<TodoItem> fulldepItem = todoItemService.getTodoItem(depItem.getId());

	            // check if any dependency is not accomplished
	            if(!fulldepItem.get().getIsDone()) {
	            	System.out.println(!fulldepItem.get().getIsDone());
	            	return new ResponseEntity<>("false", HttpStatus.OK);	            
	            }*/
	        }
	
		}
		
		TodoList postTodoList = new TodoList(todolistId,"",null);
		todoItem.setTodoList(postTodoList);
		todoItemService.updateTodoItem(todoItem);
		return new ResponseEntity<>("true", HttpStatus.OK);
	
	}
	
	/*
	 @RequestMapping("/handle")
	 public ResponseEntity<String> handle() {
	   URI location = ...;
	   HttpHeaders responseHeaders = new HttpHeaders();
	   responseHeaders.setLocation(location);
	   responseHeaders.set("MyResponseHeader", "MyValue");
	   return new ResponseEntity<String>("Hello World", responseHeaders, HttpStatus.CREATED);
	 }*/
	
		
	// delete todoItem
	@RequestMapping(method=RequestMethod.DELETE, value="users/{userId}/todolists/{todolistId}/items/{todoitemId}")
	public void deleteTodoList(@PathVariable Long userId, @PathVariable Long todoitemId) {
		todoItemService.deleteTodoItem(todoitemId); 
	}
	
	
}
