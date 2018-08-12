package io.katam.tododepitem;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.katam.todoitem.TodoItem;
import io.katam.todolist.TodoList;
import io.katam.user.TodoUser;



@RestController
public class TodoDepItemController {

	@Autowired
	private TodoDepItemService todoDepItemService;
	
 
	
	
	// get all todolist
	@RequestMapping("/users/{userid}/todolists/{todolistid}/items/{todoitemId}/depitems")
	public List<TodoDepItem> getAllTodoItems(@PathVariable Long todoitemId) {
//		System.out.println("GET request geldi");
		//System.out.println("todo controller : getAllTodoItems ");

		return todoDepItemService.getAllTodoDepItems(todoitemId);
	}
	
	//get single todolist
	@RequestMapping("/users/{userId}/todolists/{todolistId}/items/{todoitemId}/depitems/{tododepitemId}")
	public Optional<TodoDepItem> getTodoItem(@PathVariable Long tododepitemId) {
		return todoDepItemService.getTodoDepItem(tododepitemId);		
	}
	
	
	
	// add todolist
	@RequestMapping(method=RequestMethod.POST, 
			value="/users/{userId}/todolists/{todolistId}/items/{todoitemId}/{tododepitemId}/depitems")
	public ResponseEntity<String> addTodoItem(@RequestBody TodoDepItem todoDepItem,
			@PathVariable Long userId,
			@PathVariable Long todolistId,
			@PathVariable Long todoitemId,
			@PathVariable Long tododepitemId) {
		
		TodoItem postTodoItem = new TodoItem(todoitemId,"",null);
		todoDepItem.setTodoItem(postTodoItem);
		
		TodoItem depTodoItem = new TodoItem(tododepitemId,"",null);
		todoDepItem.setTodoDepItem(depTodoItem);
		
		
		 
		
		
		if(!todoDepItemService.checkIfAlreadyDepended(depTodoItem)) {
			
			if (todoDepItemService.checkDuplicate(postTodoItem, depTodoItem))
			{
				return new ResponseEntity<>("Dependency already exists.", HttpStatus.FORBIDDEN);
			} else {
				// do not allow dependency itself
				if(todoitemId != tododepitemId) {
					todoDepItemService.addTodoDepItem(todoDepItem);
					return new ResponseEntity<>("true", HttpStatus.OK);
				}
				else
					return new ResponseEntity<>("Item dependency cannot be set to itself.", HttpStatus.FORBIDDEN);
			}
		} else {
			//entity.put("msg", "Dependency already exists.");
			return new ResponseEntity<>("Item with dependency cannot be dependency of another to prevent dependency loop.", HttpStatus.FORBIDDEN);
			//return new ResponseEntity<>(entity, HttpStatus.FORBIDDEN);
		}
		
	}
	

	// update user
	@RequestMapping(method=RequestMethod.PUT, 
			value="/users/{userId}/todolists/{todolistId}/items/{todoitemId}/{tododepitemId}/depitems/{tododepitemId}")
	public void updateTodoItem( 
			@RequestBody TodoDepItem todoDepItem, 
			@PathVariable Long userId, 
			@PathVariable Long todolistId,
			@PathVariable Long todoitemId,
			@PathVariable Long tododepitemId) {
		
		TodoItem postTodoItem = new TodoItem(todoitemId,"",null);
		todoDepItem.setTodoItem(postTodoItem);
		
		TodoItem depTodoItem = new TodoItem(tododepitemId,"",null);
		todoDepItem.setTodoDepItem(depTodoItem);
		
		if (todoDepItemService.checkDuplicate(postTodoItem, depTodoItem))
		{
			System.out.println("repeated dependency");			
		} else {
			todoDepItemService.updateTodoDepItem(todoDepItem);
		}
		

	}
	
		
	// delete user
	@RequestMapping(method=RequestMethod.DELETE, value="users/{userId}/todolists/{todolistId}/items/{todoitemId}/depitems/{tododepitemId}")
	public void deleteTodoList( @PathVariable Long tododepitemId) {
		todoDepItemService.deleteTodoDepItem(tododepitemId); 
	}
	
	
}
