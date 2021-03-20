package io.ludoweb.core.game;

import lombok.Data;

@Data
public class GameView {

	String ageCategory;

	boolean borrowable;

	String durationCategory;

	String externalId;

	String name;

	boolean onSite;

	String playerCount;

	String status;

	String type;
}
