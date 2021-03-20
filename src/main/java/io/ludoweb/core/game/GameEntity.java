package io.ludoweb.core.game;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.ludoweb.core.borrowing.BorrowingEntity;
import lombok.Data;

@Entity
@Data
public class GameEntity {

	String ageCategory;

	boolean borrowable;

	@OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE, orphanRemoval = true)
	List<BorrowingEntity> borrowings;

	String durationCategory;

	@Column(nullable = false, unique = true)
	String externalId;

	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false)
	String name;

	boolean onSite;

	String playerCount;

	String type;
}
