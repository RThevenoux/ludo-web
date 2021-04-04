package io.ludoweb.core.util;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface Converter<T, R> extends Function<T, R> {

	public default List<R> convert(Collection<T> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}

		return convert(entities.stream());
	}

	public default List<R> convert(Iterable<T> entities) {
		if (entities == null) {
			return Collections.emptyList();
		}

		return convert(StreamSupport.stream(entities.spliterator(), false));
	}

	public default List<R> convert(Stream<T> stream) {
		if (stream == null) {
			return Collections.emptyList();
		}

		return stream.map(this).collect(Collectors.toList());
	}
}
