package mrs.isa.team12.clinical.center.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrs.isa.team12.clinical.center.model.RegisteredUser;
import mrs.isa.team12.clinical.center.service.interfaces.UserService;

//Controller for receiving client requests from user input form
@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/*
	 url: GET localhost:8081/api/users
	 HTTP request for viewing users
	 returns ResponseEntity object
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RegisteredUser>> getAllUsers() {
		
		List<RegisteredUser> users = userService.findAll();
		
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	/*
	 url: POST localhost:8081/api/users/addRegisteredUser
	 HTTP request for adding new user
	 receives RegisteredUser object
	 returns ResponseEntity object
	 */
	@PostMapping(value = "/addRegisteredUser",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> createRegisteredUser(@RequestBody RegisteredUser user) {
		
		RegisteredUser newUser = userService.save(user);
		
		System.out.println(user.getName());
		
		if (newUser == null) {
			return new ResponseEntity<RegisteredUser>(newUser, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

}
