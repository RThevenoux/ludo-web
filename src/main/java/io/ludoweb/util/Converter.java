package io.ludoweb.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface Converter<T, R> extends Function<T, R> {

	public default List<R> convert(Collection<T> entities) {
		return entities.stream().map(this).collect(Collectors.toList());
	}
}
