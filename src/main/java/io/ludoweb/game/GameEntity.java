package io.ludoweb.game;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.ludoweb.borrowing.BorrowingEntity;
import lombok.Data;

@Entity
@Data
public class GameEntity {

	@OneToMany(mappedBy = "game")
	List<BorrowingEntity> borrowings;

	String externalId;

	@Id
	@GeneratedValue
	Long id;

	String name;
}
