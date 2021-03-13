package io.ludoweb.game;

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
@RequestMapping("api/game")
public class GameRestController {

	@Autowired
	GameService service;

	@GetMapping
	public List<GameView> listAll() {
		return service.listAll();
	}

	@GetMapping("{externalId}")
	public Optional<GameView> find(@PathVariable String externalId) {
		return service.findByExternalId(externalId);
	}

	@DeleteMapping("{externalId}")
	public void delete(@PathVariable String externalId) {
		service.deleteByExternalId(externalId);
	}

	@PostMapping
	public GameView createOrUpdate(@RequestBody @Valid GameInput input) {
		return service.createOrUpdate(input);
	}
}
