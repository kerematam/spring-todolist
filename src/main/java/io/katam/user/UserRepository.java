package io.katam.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<TodoUser,Long> {

	public TodoUser findByUsernameAndPassword(String username, String password);
	

	
	// getAllUsers() 
	
	// getUser(Long id)
	
	// updateUser(TodoUser u)
	
	// deleteUser(Long id)

}
