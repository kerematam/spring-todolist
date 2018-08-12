package io.katam.tododepitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.katam.todoitem.TodoItem;

@Service
public class TodoDepItemService {

	@Autowired
	private TodoDepItemRepository todoDepItemRepository;
	

	public List<TodoDepItem> getAllTodoDepItems(Long todoitemId) {
		// TODO Auto-generated method stub
		//return null;
		
		return todoDepItemRepository.findByTodoItemId(todoitemId);
	}
	
	public Optional<TodoDepItem> getTodoDepItem(Long tododepitemId) {
		return todoDepItemRepository.findById(tododepitemId);
	}

	boolean checkDuplicate(TodoItem todoItem, TodoItem todoDepItem) {
				
		try {
			todoDepItemRepository.findByTodoItemAndTodoDepItem(todoItem, todoDepItem).getId();
			
			return true;
		} catch (Exception e)
		{
			return false;
		}
		

	}
	
	
	boolean checkIfAlreadyDepended(TodoItem todoDepItem) {
	
		try {
//			System.out.println(todoDepItemRepository.findByTodoDepItem(todoItem).getId());
			System.out.println(todoDepItemRepository.findByTodoItem(todoDepItem).getId());
			
			return true;
		}catch (Exception e)
		{
			System.out.println("master bulamadı");
			return false;
		}
		
	}
	
	
	
	public void addTodoDepItem(TodoDepItem todoDepItem) {
		todoDepItemRepository.save(todoDepItem);
	}

	public void updateTodoDepItem(TodoDepItem todoDepItem) {
		todoDepItemRepository.save(todoDepItem);		
	}
		
	public void deleteTodoDepItem(Long tododepitemtId) {
		todoDepItemRepository.deleteById(tododepitemtId);
	}











}
