package io.ludoweb.web.rest;

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

import io.ludoweb.core.user.member.PasswordWrapper;
import io.ludoweb.core.user.member.MemberInput;
import io.ludoweb.core.user.member.MemberService;
import io.ludoweb.core.user.member.MemberView;

@RestController
@RequestMapping("api/member")
public class MemberRestController {

	@Autowired
	MemberService service;

	@PostMapping("batch")
	public void batchCreateOrUpdate(@RequestBody @Valid List<MemberInput> inputs) {
		for (MemberInput input : inputs) {
			service.createOrUpdate(input);
		}
	}

	@PostMapping
	public MemberView createOrUpdate(@RequestBody @Valid MemberInput input) {
		return service.createOrUpdate(input);
	}

	@DeleteMapping("{externalId}")
	public void delete(@PathVariable String externalId) {
		service.delete(externalId);
	}

	@GetMapping
	public List<MemberView> getUsers() {
		return service.list();
	}

	@PutMapping("{externalId}/password")
	public boolean updatePassword(@PathVariable String externalId,
			@RequestBody @Valid PasswordWrapper passwordWrapper) {
		return service.updateMemberPassword(externalId, passwordWrapper.getPassword());
	}

}
