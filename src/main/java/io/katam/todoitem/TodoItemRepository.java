package io.katam.todoitem;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TodoItemRepository extends CrudRepository<TodoItem,Long> {
	

	//public List<TodoDepItem> findByTodoUserId(Long todoUserId); //getTodoListByUser(Long userId);

	public List<TodoItem> findByTodoListId(Long todoListId);



	//public List<TodoDepItem> findByTodouserId(Long userId);

	//public Iterable<TodoDepItem> findByUserId(Long userId);

	//public List<TodoDepItem> findByUserId(Long userId);


}
