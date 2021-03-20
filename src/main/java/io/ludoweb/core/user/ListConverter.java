package io.ludoweb.core.user;

import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListConverter implements AttributeConverter<List<String>, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static final TypeReference<List<String>> typeRef = new TypeReference<List<String>>() {
	};

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
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
	public List<String> convertToEntityAttribute(String dbData) {
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
