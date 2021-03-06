package io.ludoweb.config;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class ConfigEntity {

	@Id
	@GeneratedValue
	Long id;

	String mainLogo;
	String title;
}
