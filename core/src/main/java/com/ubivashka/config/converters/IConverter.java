package com.ubivashka.config.converters;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
public interface IConverter<D, E> {
	public E convertFromDto(final D dto);

	public D convertFromEntity(final E entity);

	default List<E> createFromDtos(final Collection<D> dtos) {
		return dtos.stream().map(this::convertFromDto).collect(Collectors.toList());
	}

	default List<D> createFromEntities(final Collection<E> entities) {
		return entities.stream().map(this::convertFromEntity).collect(Collectors.toList());
	}

	default Class<?> getDtoClass() {
		return null;
	}

	default Class<?> getEntityClass() {
		return null;
	}
}
