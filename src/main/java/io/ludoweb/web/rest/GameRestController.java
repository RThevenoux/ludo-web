package io.ludoweb.web.rest;

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

import io.ludoweb.core.game.GameInput;
import io.ludoweb.core.game.GameService;
import io.ludoweb.core.game.GameView;

@RestController
@RequestMapping("api/game")
public class GameRestController {

	@Autowired
	GameService service;

	@PostMapping("batch")
	public void batchCreateOrUpdate(@RequestBody @Valid List<GameInput> inputs) {
		for (GameInput input : inputs) {
			service.createOrUpdate(input);
		}
	}

	@PostMapping
	public GameView createOrUpdate(@RequestBody @Valid GameInput input) {
		return service.createOrUpdate(input);
	}

	@DeleteMapping("{externalId}")
	public void delete(@PathVariable String externalId) {
		service.deleteByExternalId(externalId);
	}

	@GetMapping("{externalId}")
	public Optional<GameView> find(@PathVariable String externalId) {
		return service.findByExternalId(externalId);
	}

	@GetMapping
	public List<GameView> listAll() {
		return service.listAll();
	}
}
