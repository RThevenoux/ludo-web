package io.ludoweb.core.util;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractJsonConverter<T> implements AttributeConverter<T, String> {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String convertToDatabaseColumn(T attribute) {
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
	public T convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}

		try {
			return objectMapper.readValue(dbData, getTypeRef());
		} catch (JsonProcessingException e) {
			log.error("Fail to read JSON", e);
			return null;
		}
	}

	protected abstract TypeReference<T> getTypeRef();
}
