package io.ludoweb.borrowing;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ludoweb.game.GameEntity;
import io.ludoweb.game.GameService;
import io.ludoweb.user.UserEntity;
import io.ludoweb.user.UserService;

@Service
public class BorrowingService {

	@Autowired
	BorrowingRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	GameService gameService;

	public Optional<BorrowingEntity> getBorrowingByExternalID(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public void createOrUpdate(BorrowingInput input) {
		Optional<GameEntity> game = gameService.findEntityByExternalId(input.getGameExternalId());
		Optional<UserEntity> user = userService.findEntityByExternalId(input.getUserExternalId());
	}

	public long getActiveBorrowingCount() {
		return 42;
	}
}
