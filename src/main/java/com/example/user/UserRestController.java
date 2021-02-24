package com.example.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserRestController {

	@Autowired
	UserService service;

	@GetMapping
	public List<UserInput> getUsers() {
		return service.list();
	}

	@PostMapping
	public UserInput create(@RequestBody UserInput data) {
		return service.create(data);
	}

	@PutMapping
	public UserInput update(@RequestBody UserInput data) {
		return service.updateUserData(data);
	}

	@PutMapping("{username}/password")
	public UserInput updatePassword(@PathVariable String username, @RequestBody PasswordWrapper passwordWrapper) {
		return service.updateUserPassword(username, passwordWrapper.getPassword());
	}

	@DeleteMapping("{username}")
	public void delete(@PathVariable String username) {
		service.delete(username);
	}

}
