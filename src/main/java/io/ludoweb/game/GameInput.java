package io.ludoweb.game;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GameInput {

	@NotBlank
	String externalId;

	@NotBlank
	String name;
}
