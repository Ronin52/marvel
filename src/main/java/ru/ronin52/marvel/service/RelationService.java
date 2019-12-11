package ru.ronin52.marvel.service;

import java.util.UUID;

public interface RelationService {
    boolean bindCharacterAndComicsById(UUID characterId, UUID comicsId);
}
