package com.example.tick;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class TickEntity {

	@Id
	@GeneratedValue
	Long id;

	Timestamp timestamp;
}
