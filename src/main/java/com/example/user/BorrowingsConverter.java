package com.example.user;

import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BorrowingsConverter implements AttributeConverter<List<Borrowing>, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final TypeReference<List<Borrowing>> typeRef = new TypeReference<List<Borrowing>>() {
	};

	@Override
	public String convertToDatabaseColumn(List<Borrowing> attribute) {
		if (attribute == null) {
			return null;
		}
		try {
			return objectMapper.writeValueAsString(attribute);
		} catch (JsonProcessingException e) {
			log.error("Fail to write JSON", e);
			return null;
		}
	}

	@Override
	public List<Borrowing> convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		try {

			return objectMapper.readValue(dbData, typeRef);
		} catch (JsonProcessingException e) {
			log.error("Fail to read JSON", e);
			return null;
		}
	}

}
