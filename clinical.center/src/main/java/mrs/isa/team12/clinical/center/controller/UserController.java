package mrs.isa.team12.clinical.center.controller;

import java.util.Collection;

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
import mrs.isa.team12.clinical.center.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RegisteredUser>> getUsers() {
		Collection<RegisteredUser> users = userService.findAll();
		return new ResponseEntity<Collection<RegisteredUser>>(users, HttpStatus.OK);
	}
	
	@PostMapping(value = "/addPatient",
				 consumes = MediaType.APPLICATION_JSON_VALUE, 
				 produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegisteredUser> createGreeting(@RequestBody RegisteredUser user) {
		RegisteredUser newUser = userService.create(user);
		System.out.println(user.getName());
		if (newUser == null) {
			return new ResponseEntity<RegisteredUser>(newUser, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<RegisteredUser>(newUser, HttpStatus.CREATED);
	}

}
