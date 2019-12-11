package ru.ronin52.marvel.service;

import java.util.List;
import java.util.UUID;

public interface EntityService <D, C, S> {
    D save(S dto);
    List<D> getPage(int page, int count);
    List<D> findByName(String q);
    List<D> findByDescription(String q);
    D getByIdWithoutCollection(UUID id);
    C getByIdWithCollection(UUID id);
    List<D> getAll();
    void removeById(UUID id);
}