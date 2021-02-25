package com.example.user;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository repo;
	@Autowired
	PasswordEncoder passwordEncoder;

	private UserInput convert(UserEntity entity) {
		if (entity == null) {
			return null;
		}

		UserInput data = new UserInput();
		data.setBorrowings(entity.getBorrowings());
		data.setFirstName(entity.getFirstName());
		data.setLastName(entity.getLastName());
		data.setMail(entity.getMail());
		data.setOtherMembers(entity.getOtherMembers());
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
		entity.setSubscriptionPaid(data.isSubscriptionPaid());
		entity.setType(data.getType());
	}

	public List<UserInput> list() {
		return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
	}

	public UserInput create(UserInput data) {
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

	public UserInput updateUserData(UserInput data) {
		UserEntity entity = repo.findByUsername(data.getUsername());
		if (entity != null) {
			fill(entity, data);
		}
		return convert(entity);
	}

	public UserInput updateUserPassword(String username, String password) {
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
}