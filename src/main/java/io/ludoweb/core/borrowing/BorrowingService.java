package io.ludoweb.core.borrowing;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.ludoweb.core.game.GameEntity;
import io.ludoweb.core.game.GameService;
import io.ludoweb.core.user.member.MemberEntity;
import io.ludoweb.core.user.member.MemberService;

@Service
@Transactional
public class BorrowingService {

	@Autowired
	BorrowingConverter converter;

	@Autowired
	GameService gameService;

	@Autowired
	BorrowingRepository repo;

	@Autowired
	MemberService memberService;

	public SaveBorrowingResult createOrUpdate(BorrowingInput input) {
		Optional<GameEntity> optGame = gameService.findEntityByExternalId(input.getGameExternalId());
		Optional<MemberEntity> optMember = memberService.findEntityByExternalId(input.getMemberExternalId());

		if (!optGame.isPresent() || !optMember.isPresent()) {
			return SaveBorrowingResult.fail(optMember.isPresent(), optGame.isPresent());
		}
		GameEntity game = optGame.get();
		MemberEntity member = optMember.get();

		Optional<BorrowingEntity> optBorrowing = repo.findByExternalId(input.getExternalId());

		if (optBorrowing.isPresent()) {
			BorrowingEntity entity = optBorrowing.get();

			// Remove old relation
			entity.getGame().getBorrowings().remove(entity);
			entity.getMember().getBorrowings().remove(entity);

			fill(entity, input, game, member);

		} else {
			BorrowingEntity entity = new BorrowingEntity();
			entity.setExternalId(input.getExternalId());
			fill(entity, input, game, member);

			repo.save(entity);
		}

		return SaveBorrowingResult.success();
	}

	public void deleteByExternalId(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	private void fill(BorrowingEntity entity, BorrowingInput input, GameEntity game, MemberEntity member) {
		entity.setGame(game);
		entity.setStartDate(input.getStartDate());
		entity.setMember(member);
	}

	public long getActiveBorrowingCount() {
		return repo.count();
	}

	public Optional<BorrowingEntity> getBorrowingByExternalID(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public Optional<BorrowingView> getByExternalId(String externalId) {
		return repo.findByExternalId(externalId).map(converter);
	}

	public List<BorrowingView> listAll() {
		return converter.convert(repo.findAll());
	}
}
