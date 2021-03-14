package io.ludoweb.user;

import java.util.List;

import javax.validation.Valid;

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

	@PostMapping
	public UserView createOrUpdate(@RequestBody @Valid UserInput data) {
		return service.createOrUpdate(data);
	}

	@DeleteMapping("{externalId}")
	public void delete(@PathVariable String externalId) {
		service.delete(externalId);
	}

	@GetMapping
	public List<UserView> getUsers() {
		return service.list();
	}

	@PutMapping("{externalId}/password")
	public boolean updatePassword(@PathVariable String externalId,
			@RequestBody @Valid PasswordWrapper passwordWrapper) {
		return service.updateUserPassword(externalId, passwordWrapper.getPassword());
	}

}
