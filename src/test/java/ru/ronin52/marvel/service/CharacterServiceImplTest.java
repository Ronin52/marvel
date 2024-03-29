package ru.ronin52.marvel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.CharacterDtoWithComics;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.exception.CharacterNotFoundException;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.service.impl.CharacterServiceImpl;
import ru.ronin52.marvel.service.impl.RelationServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CharacterServiceImplTest {
    private CharacterRepository repository;
    private RelationService relationService;
    private EntityService<CharacterDto, CharacterDtoWithComics> service;

    @BeforeEach
    void init() {
        repository = mock(CharacterRepository.class);
        relationService = mock(RelationServiceImpl.class);
        service = new CharacterServiceImpl(repository, relationService);
    }

    @Test
    void createWithComicsList() {
        UUID id = UUID.randomUUID();

        CharacterEntity entity = new CharacterEntity();
        entity.setId(id);
        entity.setComics(Collections.emptyList());

        List<ComicsDto> comicsDtos = List.of(new ComicsDto());

        CharacterDtoWithComics dto = new CharacterDtoWithComics();
        dto.setComics(comicsDtos);

        when(repository.save(any(CharacterEntity.class)))
                .thenReturn(entity);


        assertEquals(id, service.save(dto).getId());
    }

    @Test
    void createWithEmptyComicsList() {
        UUID id = UUID.randomUUID();

        CharacterEntity entity = new CharacterEntity();
        entity.setId(id);
        entity.setComics(Collections.emptyList());

        CharacterDtoWithComics dto = new CharacterDtoWithComics();
        dto.setComics(Collections.emptyList());

        when(repository.save(any(CharacterEntity.class)))
                .thenReturn(entity);


        assertEquals(id, service.save(dto).getId());
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();

        CharacterEntity entity = new CharacterEntity();
        entity.setId(id);
        entity.setComics(Collections.emptyList());

        CharacterDtoWithComics dto = new CharacterDtoWithComics();
        dto.setId(id);
        dto.setComics(Collections.emptyList());

        when(repository.save(any(CharacterEntity.class)))
                .thenReturn(entity);


        assertEquals(id, service.save(dto).getId());
    }

    @Test
    void getPage() {
        when(repository.getPage(anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), service.getPage(1, 1));
    }

    @Test
    void getSortedByName() {
        when(repository.getSortedByName())
                .thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), service.getSortedByName());
    }

    @Test
    void findByName() {
        when(repository.findAllByNameContainsIgnoreCase(anyString()))
                .thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), service.findByName("name"));
    }

    @Test
    void findByDescription() {
        when(repository.findAllByDescriptionContainsIgnoreCase(anyString()))
                .thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), service.findByDescription("description"));
    }

    @Test
    void whenGetByIdWithoutCollectionThenReturnDto() {
        UUID id = UUID.randomUUID();
        CharacterEntity entity = new CharacterEntity();
        entity.setId(id);
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(entity));

        assertEquals(id, service.getByIdWithoutCollection(id).getId());
    }

    @Test
    void whenGetByIdWithoutCollectionThenThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(CharacterNotFoundException.class, () -> service.getByIdWithoutCollection(id));
    }

    @Test
    void whenGetByIdWithCollectionThenReturnDto() {
        UUID id = UUID.randomUUID();
        CharacterEntity entity = new CharacterEntity();
        entity.setId(id);
        entity.setComics(Collections.emptyList());
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(entity));

        assertEquals(id, service.getByIdWithCollection(id).getId());
    }

    @Test
    void whenGetByIdWitHCollectionThenThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(CharacterNotFoundException.class, () -> service.getByIdWithCollection(id));
    }

    @Test
    void getAll() {
        when(repository.findAll())
                .thenReturn(Collections.emptyList());

        assertEquals(Collections.emptyList(), service.getAll());
    }

    @Test
    void removeById() {
        //for 100% coverage
        service.removeById(UUID.randomUUID());
    }

}