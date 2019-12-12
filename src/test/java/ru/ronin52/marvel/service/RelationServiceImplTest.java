package ru.ronin52.marvel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.CharacterNotFoundException;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RelationServiceImplTest {
    private CharacterRepository characterRepository;
    private ComicsRepository comicsRepository;
    private RelationService service;
    private Collection characterComics;
    private CharacterEntity characterEntity;
    private Collection comicsCharacters;
    private ComicsEntity comicsEntity;


    @BeforeEach
    void init() {
        characterRepository = mock(CharacterRepository.class);
        comicsRepository = mock(ComicsRepository.class);
        service = new RelationServiceImpl(characterRepository, comicsRepository);
        characterComics = mock(Collection.class);
        characterEntity = mock(CharacterEntity.class);
        comicsCharacters = mock(Collection.class);
        comicsEntity = mock(ComicsEntity.class);
    }

    @Test
    void whenBindThenSuccess() {
        when(characterRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(characterEntity));
        when(comicsRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(comicsEntity));
        when(characterEntity.getComics())
                .thenReturn(characterComics);
        when(characterComics.contains(comicsEntity))
                .thenReturn(false);
        when(comicsEntity.getCharacters())
                .thenReturn(comicsCharacters);
        when(comicsCharacters.contains(characterEntity))
                .thenReturn(false);

        assertTrue(service.bindCharacterAndComicsById(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void whenBindThenThrowCharacterNotFoundException() {
        when(characterRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(CharacterNotFoundException.class, () -> service.bindCharacterAndComicsById(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void whenBindThenThrowComicsNotFoundException() {
        when(characterRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(characterEntity));
        when(comicsRepository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(ComicsNotFoundException.class, () -> service.bindCharacterAndComicsById(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void whenBindThenComicsContainsInCharacterComics() {
        when(characterRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(characterEntity));
        when(comicsRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(comicsEntity));
        when(characterEntity.getComics())
                .thenReturn(characterComics);
        when(characterComics.contains(comicsEntity))
                .thenReturn(true);

        assertFalse(service.bindCharacterAndComicsById(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void whenBindThenCharacterContainsIntComicsCharacters() {
        when(characterRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(characterEntity));
        when(comicsRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(comicsEntity));
        when(characterEntity.getComics())
                .thenReturn(characterComics);
        when(characterComics.contains(comicsEntity))
                .thenReturn(false);
        when(comicsEntity.getCharacters())
                .thenReturn(comicsCharacters);
        when(comicsCharacters.contains(characterEntity))
                .thenReturn(true);

        assertFalse(service.bindCharacterAndComicsById(UUID.randomUUID(), UUID.randomUUID()));
    }


}