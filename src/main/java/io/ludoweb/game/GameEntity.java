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

	@Id
	@GeneratedValue
	Long id;

	String externalId;

	String name;

	@OneToMany(mappedBy = "game")
	List<BorrowingEntity> borrowings;
}
