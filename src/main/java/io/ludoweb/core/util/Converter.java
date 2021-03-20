package io.ludoweb.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Converter<T, R> extends Function<T, R> {

	public default List<R> convert(Collection<T> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}

		return entities.stream().map(this).collect(Collectors.toList());
	}
}
