package io.ludoweb.borrowing;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ludoweb.game.GameEntity;
import io.ludoweb.game.GameService;
import io.ludoweb.user.UserEntity;
import io.ludoweb.user.UserService;

@Service
@Transactional
public class BorrowingService {

	@Autowired
	BorrowingRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	GameService gameService;

	@Autowired
	BorrowingConverter converter;

	public Optional<BorrowingEntity> getBorrowingByExternalID(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public BorrowingResult createOrUpdate(BorrowingInput input) {
		Optional<GameEntity> optGame = gameService.findEntityByExternalId(input.getGameExternalId());
		Optional<UserEntity> optUser = userService.findEntityByExternalId(input.getUserExternalId());

		if (!optGame.isPresent() || !optUser.isPresent()) {
			return BorrowingResult.fail(optUser.isPresent(), optGame.isPresent());
		}
		GameEntity game = optGame.get();
		UserEntity user = optUser.get();

		Optional<BorrowingEntity> optBorrowing = repo.findByExternalId(input.getExternalId());

		if (optBorrowing.isPresent()) {
			BorrowingEntity entity = optBorrowing.get();
			fill(entity, input, game, user);

		} else {
			BorrowingEntity entity = new BorrowingEntity();
			entity.setExternalId(input.getExternalId());
			fill(entity, input, game, user);

			repo.save(entity);
		}

		return BorrowingResult.success();
	}

	private void fill(BorrowingEntity entity, BorrowingInput input, GameEntity game, UserEntity user) {
		entity.setGame(game);
		entity.setStartDate(input.getStartDate());
		entity.setUser(user);
	}

	public long getActiveBorrowingCount() {
		return repo.count();
	}

	public Optional<BorrowingView> getByExternalId(String externalId) {
		return repo.findByExternalId(externalId).map(converter::convert);
	}

	public List<BorrowingView> listAll() {
		return converter.convert(repo.findAll());
	}

	public void deleteByExternalId(String externalId) {
		repo.deleteByExternalId(externalId);
	}
}
