package io.ludoweb.borrowing;

import javax.persistence.Column;
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

	@Column(nullable = false, unique = true)
	String externalId;

	@ManyToOne(optional = false)
	GameEntity game;

	@Id
	@GeneratedValue
	Long id;

	@Column(nullable = false)
	String startDate;

	@ManyToOne(optional = false)
	UserEntity user;
}
