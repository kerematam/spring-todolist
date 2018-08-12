package io.katam.user;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	//public static HttpSession httpSession;
	
	// user login
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public void login(@RequestBody TodoUser todoUser) {
		
		if(userService.isAuth()) {
			userService.httpSession.getAttribute("userId");
		}
		
		userService.handleLogin(todoUser, userService.httpSession);
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/logout")
	public void logout() {
		userService.httpSession.setAttribute("userId", null);
	}
	
	
	@RequestMapping("/isLogin")
	public long isLogin() {
	
		if(userService.isAuth()) {
			return (long) userService.httpSession.getAttribute("userId");
		} else {
			return -1;
		}

	}
		
	// get all users
	@RequestMapping("/users")
	public List<TodoUser> getAllUsers() {
//		System.out.println("GET request geldi");
		return userService.getAllUsers();
	}
	
	//get single user
	@RequestMapping("/users/{id}")
	public Optional<TodoUser> getUser(@PathVariable Long id) {
		return userService.getUser(id);		
	}
	
	// update user
	@RequestMapping(method=RequestMethod.POST, value="/users")
	public void addUser(@RequestBody TodoUser todoUser) {
		userService.addUser(todoUser);
	}
	
	// update user
	@RequestMapping(method=RequestMethod.PUT, value="/users/{id}")
	public void addUser(@PathVariable Long id,@RequestBody TodoUser todoUser) {
		userService.updateUser(id, todoUser);
	}	
	
	// delete user
	@RequestMapping(method=RequestMethod.DELETE, value="users/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id); 
	}
	
	
}
