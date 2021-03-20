package io.ludoweb.core.game;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GameInput {

	String ageCategory;

	boolean borrowable;

	String durationCategory;

	@NotBlank
	String externalId;

	@NotBlank
	String name;

	boolean onSite;

	String playerCount;

	String type;
}
