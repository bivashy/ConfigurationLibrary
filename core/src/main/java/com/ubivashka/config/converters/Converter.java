package com.ubivashka.config.converters;

import java.util.function.Function;

@Deprecated
public class Converter<D, E> implements IConverter<D, E>{

	private final Function<D, E> fromDto;
	private final Function<E, D> fromEntity;

	public Converter(final Function<D, E> fromDto, final Function<E, D> fromEntity) {
		this.fromDto = fromDto;
		this.fromEntity = fromEntity;
	}

	@Override
	public final E convertFromDto(final D dto) {
		return fromDto.apply(dto);
	}

	@Override
	public final D convertFromEntity(final E entity) {
		return fromEntity.apply(entity);
	}
}