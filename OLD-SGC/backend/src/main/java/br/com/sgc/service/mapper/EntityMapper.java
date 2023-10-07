package br.com.sgc.service.mapper;

import java.util.List;

public interface EntityMapper<D,E> {

    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entity);

    List<E> toEntity(List<D> dto);

}
