package io.katam.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	
	public HttpSession httpSession;
	
//	private TodoUser user_1 = new TodoUser(1,"kerem","kerematam@gmail.com","123123");
//	private TodoUser user_2 = new TodoUser(2,"mehmet","mehmet@gmail.com","123123"); 
//	private TodoUser user_3 = new TodoUser(3,"ali","ali@gmail.com","123123"); 
//	private List<TodoUser> todoUsers = Arrays.asList(user_1,user_2,user_3);
	
	
	public boolean isAuth() {
		if( httpSession == null) {
			httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
			return false;
		}
		else {
			try {
				if(httpSession.getAttribute("userId") == null){
	          		// ? https://stackoverflow.com/questions/2311429/httpservletrequest-create-new-session-change-session-id
	        		httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
	        		httpSession.invalidate();
					return false;
				} else 
					return true;
		
			} catch (Exception e) {
          		// ? https://stackoverflow.com/questions/2311429/httpservletrequest-create-new-session-change-session-id
        		httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
        		httpSession.invalidate();
				System.out.println("userId is not set");
				System.out.println(e);
				return false;
			}
			
					
		} 
			
			
			
		
	}

	public List<TodoUser> getAllUsers() {
		// TODO Auto-generated method stub
		//return todoUsers;
		List<TodoUser> todoUsers = new ArrayList<>();
		userRepository.findAll().forEach(todoUsers::add);
		return todoUsers;
	}
	
	public Optional<TodoUser> getUser(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}
	
	public void addUser(TodoUser todoUser) {
		userRepository.save(todoUser);
	}
		
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	public void updateUser(Long id, TodoUser todoUser) {
		// TODO Auto-generated method stub
		userRepository.save(todoUser);		
	}

	public void handleLogin(TodoUser todoUser, HttpSession session) {
		
		// check if login
        try {
        	
        	if(httpSession != null) {
            	TodoUser checkUser = userRepository.findByUsernameAndPassword(todoUser.getUsername(),todoUser.getPassword());
            	System.out.println(checkUser.getId());
            	session.setAttribute("userId",checkUser.getId());	
        	} else {       		 
        		System.out.println("Session dropped, recreating");
        		
          		// ? https://stackoverflow.com/questions/2311429/httpservletrequest-create-new-session-change-session-id
        		httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
        		httpSession.invalidate();
        		httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(true);
        	}
   
        } catch (Exception e) {
      		// ? https://stackoverflow.com/questions/2311429/httpservletrequest-create-new-session-change-session-id
    		httpSession = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession(false);
    		httpSession.invalidate();
    		
        	System.out.println("Invalid Login");
        	System.out.println(e);
        }
				
	}
	
	

	
	

}
