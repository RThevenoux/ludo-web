package com.example.tick;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TickService {

	@Autowired
	private TickRepository repo;

	public List<TickEntity> loadTicks() {

		TickEntity tick = new TickEntity();
		tick.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
		repo.save(tick);

		return repo.findAll();

	}

}
