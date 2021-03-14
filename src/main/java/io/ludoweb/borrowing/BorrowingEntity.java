package io.ludoweb.borrowing;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.ludoweb.game.GameEntity;
import io.ludoweb.user.UserEntity;
import lombok.Data;

@Entity
@Data
public class BorrowingEntity {

	String externalId;

	@ManyToOne
	GameEntity game;

	@Id
	@GeneratedValue
	Long id;

	String startDate;

	@ManyToOne
	UserEntity user;
}
