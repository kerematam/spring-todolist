package io.katam.tododepitem;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.katam.todoitem.TodoItem;
import io.katam.user.TodoUser;

public interface TodoDepItemRepository extends CrudRepository<TodoDepItem,Long> {
	

	//public List<TodoDepItem> findByTodoUserId(Long todoUserId); //getTodoListByUser(Long userId);

	

	public List<TodoDepItem> findByTodoItemId(Long todoitemId);

	public TodoDepItem findByTodoItemAndTodoDepItem(TodoItem item, TodoItem depItem);

	public TodoDepItem findByTodoDepItem(TodoItem item);
	
	
	public TodoDepItem findByTodoItem(TodoItem item);
	//public List<TodoDepItem> findByTodouserId(Long userId);

	//public Iterable<TodoDepItem> findByUserId(Long userId);

	//public List<TodoDepItem> findByUserId(Long userId);


}
