package io.ludoweb.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository repo;
	@Autowired
	PasswordEncoder passwordEncoder;

	private UserView convert(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		boolean isPassword = !StringUtils.isEmpty(entity.getPassword());

		UserView data = new UserView();
		data.setBorrowings(entity.getBorrowings());
		data.setFirstName(entity.getFirstName());
		data.setLastName(entity.getLastName());
		data.setMail(entity.getMail());
		data.setOtherMembers(entity.getOtherMembers());
		data.setPassword(isPassword);
		data.setPhone(entity.getPhone());
		data.setPlan(entity.getPlan());
		data.setSubscriptionPaid(entity.isSubscriptionPaid());
		data.setType(entity.getType());
		data.setUsername(entity.getUsername());
		return data;
	}

	private void fill(UserEntity entity, UserInput data) {
		entity.setBorrowings(data.getBorrowings());
		entity.setFirstName(data.getFirstName());
		entity.setLastName(data.getLastName());
		entity.setMail(data.getMail());
		entity.setOtherMembers(data.getOtherMembers());
		entity.setPhone(data.getPhone());
		entity.setPlan(data.getPlan());
		entity.setSubscriptionPaid(data.getSubscriptionPaid());
		entity.setType(data.getType());
	}

	public List<UserView> list() {
		return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
	}

	public UserView create(UserInput data) {
		if (repo.existsByUsername(data.getUsername())) {
			return null;
		}

		UserEntity entity = new UserEntity();
		entity.setPassword(null);
		entity.setUsername(data.getUsername());
		fill(entity, data);

		UserEntity saved = repo.save(entity);
		return convert(saved);
	}

	public UserView updateUserData(UserInput data) {
		UserEntity entity = repo.findByUsername(data.getUsername());
		if (entity != null) {
			fill(entity, data);
		}
		return convert(entity);
	}

	public UserView updateUserPassword(String username, String password) {
		UserEntity entity = repo.findByUsername(username);
		if (entity != null) {
			String encoded = passwordEncoder.encode(password);
			entity.setPassword(encoded);
		}
		return convert(entity);
	}

	public void delete(String username) {
		repo.deleteByUsername(username);
	}

	public UserView get(String username) {
		UserEntity entity = repo.findByUsername(username);
		return convert(entity);
	}

	public UserStats getUserStats(boolean subscriptionPaid) {
		List<UserEntity> users = repo.findBySubscriptionPaid(subscriptionPaid);

		int memberCount = 0;
		for (UserEntity user : users) {
			memberCount += (1 + user.getOtherMembers().size());
		}

		return new UserStats(memberCount, users.size());
	}

	public long getBorrowingCount() {
		return repo.findAll().stream()//
				.flatMap(user -> user.getBorrowings().stream())//
				.count();
	}
}
