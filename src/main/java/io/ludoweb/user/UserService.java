package io.ludoweb.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

	@Autowired
	UserConverter converter;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository repo;

	public UserView createOrUpdate(UserInput data) {
		Optional<UserEntity> optUser = findEntityByExternalId(data.getExternalId());

		if (optUser.isPresent()) {
			UserEntity entity = optUser.get();
			fill(entity, data);
			return converter.apply(entity);
		} else {
			UserEntity entity = new UserEntity();
			entity.setPassword(null);
			entity.setExternalId(data.getExternalId());
			fill(entity, data);

			UserEntity saved = repo.save(entity);
			return converter.apply(saved);
		}
	}

	public void delete(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	private UserEntity fill(UserEntity entity, UserInput data) {
		entity.setFirstName(data.getFirstName());
		entity.setLastName(data.getLastName());
		entity.setMail(data.getMail());
		entity.setOtherMembers(data.getOtherMembers());
		entity.setPhone(data.getPhone());
		entity.setPlan(data.getPlan());
		entity.setType(data.getType());
		entity.setUsername(data.getUsername());
		return entity;
	}

	public Optional<UserView> findByExternalId(String externalId) {
		return findEntityByExternalId(externalId).map(converter);
	}

	public Optional<UserView> findById(long id) {
		return repo.findById(id).map(converter);
	}

	public Optional<UserEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public UserStats getUserStats(boolean subscriptionPaid) {
		List<UserEntity> users = repo.findBySubscriptionPaid(subscriptionPaid);

		int memberCount = 0;
		for (UserEntity user : users) {
			memberCount += (1 + user.getOtherMembers().size());
		}

		return new UserStats(memberCount, users.size());
	}

	public List<UserView> list() {
		return converter.convert(repo.findAll());
	}

	public boolean updateUserPassword(String externalId, String password) {
		return findEntityByExternalId(externalId)//
				.map(entity -> {
					String encoded = passwordEncoder.encode(password);
					entity.setPassword(encoded);
					return true;
				})//
				.orElse(false);
	}
}
