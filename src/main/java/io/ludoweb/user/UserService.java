package io.ludoweb.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import io.ludoweb.borrowing.BorrowingConverter;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository repo;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	BorrowingConverter borrowingConverter;

	private UserView convert(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		boolean isPassword = !StringUtils.isEmpty(entity.getPassword());

		UserView data = new UserView();
		data.setBorrowings(borrowingConverter.convert(entity.getBorrowings()));
		data.setExternalId(entity.getExternalId());
		data.setFirstName(entity.getFirstName());
		data.setLastName(entity.getLastName());
		data.setMail(entity.getMail());
		data.setOtherMembers(entity.getOtherMembers());
		data.setPassword(isPassword);
		data.setPhone(entity.getPhone());
		data.setPlan(entity.getPlan());
		data.setType(entity.getType());
		data.setUsername(entity.getUsername());
		return data;
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

	public List<UserView> list() {
		return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
	}

	public UserView create(UserInput data) {
		if (repo.existsByExternalId(data.getExternalId())) {
			return null;
		}

		UserEntity entity = new UserEntity();
		entity.setPassword(null);
		entity.setExternalId(data.getExternalId());
		fill(entity, data);

		UserEntity saved = repo.save(entity);
		return convert(saved);
	}

	public Optional<UserView> updateUserData(UserInput data) {
		return findEntityByExternalId(data.getExternalId())//
				.map(entity -> fill(entity, data))//
				.map(this::convert);
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

	public void delete(String externalId) {
		repo.deleteByExternalId(externalId);
	}

	public UserStats getUserStats(boolean subscriptionPaid) {
		List<UserEntity> users = repo.findBySubscriptionPaid(subscriptionPaid);

		int memberCount = 0;
		for (UserEntity user : users) {
			memberCount += (1 + user.getOtherMembers().size());
		}

		return new UserStats(memberCount, users.size());
	}

	public Optional<UserEntity> findEntityByExternalId(String externalId) {
		return repo.findByExternalId(externalId);
	}

	public Optional<UserView> findByExternalId(String externalId) {
		return findEntityByExternalId(externalId).map(this::convert);
	}

	public Optional<UserView> findById(long id) {
		return repo.findById(id).map(this::convert);
	}
}
