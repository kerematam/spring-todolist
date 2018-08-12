package io.katam.todolist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TodoListRepository extends CrudRepository<TodoList,Long> {
	

	public List<TodoList> findByTodoUserId(Long todoUserId); //getTodoListByUser(Long userId);

	//public List<TodoDepItem> findByTodouserId(Long userId);

	//public Iterable<TodoDepItem> findByUserId(Long userId);

	//public List<TodoDepItem> findByUserId(Long userId);


}
