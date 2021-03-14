package io.ludoweb.borrowing;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/borrowing")
public class BorrowingRestController {

	@Autowired
	BorrowingService service;

	@PostMapping
	public BorrowingResult createOrUpdate(@RequestBody @Valid BorrowingInput input) {
		return service.createOrUpdate(input);
	}

	@DeleteMapping("{externalId}")
	public void delete(@PathVariable String externalId) {
		service.deleteByExternalId(externalId);
	}

	@GetMapping("{externalId}")
	public Optional<BorrowingView> get(@PathVariable String externalId) {
		return service.getByExternalId(externalId);
	}

	@GetMapping
	public List<BorrowingView> listAll() {
		return service.listAll();
	}
}
