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
		data.setMail(entity.getMail());
		data.setPhone(entity.getPhone());
		data.setUsername(entity.getUsername());
		return data;
	}

	public List<UserInput> list() {
		return repo.findAll().stream().map(this::convert).collect(Collectors.toList());
	}

	public UserInput create(UserInput data) {
		if (repo.existsByUsername(data.getUsername())) {
			return null;
		}

		UserEntity entity = new UserEntity();
		entity.setMail(data.getMail());
		entity.setPassword(null);
		entity.setPhone(data.getPhone());
		entity.setUsername(data.getUsername());

		UserEntity saved = repo.save(entity);
		return convert(saved);
	}

	public UserInput updateUserData(UserInput data) {
		UserEntity entity = repo.findByUsername(data.getUsername());
		if (entity != null) {
			entity.setMail(data.getMail());
			entity.setPhone(data.getPhone());
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
