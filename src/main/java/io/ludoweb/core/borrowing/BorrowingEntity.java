package io.ludoweb.core.borrowing;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import io.ludoweb.core.game.GameEntity;
import io.ludoweb.core.user.member.MemberEntity;
import lombok.Data;

@Entity
@Data
public class BorrowingEntity {

	@Column(nullable = false)
	LocalDate endDate;

	@Column(nullable = false, unique = true)
	String externalId;

	@ManyToOne(optional = false)
	GameEntity game;

	@Id
	@GeneratedValue
	Long id;

	@ManyToOne(optional = false)
	MemberEntity member;

	@Column(nullable = false)
	LocalDate startDate;
}
