package ru.ronin52.marvel.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.ComicsRepository;
import ru.ronin52.marvel.service.impl.ComicsServiceImpl;
import ru.ronin52.marvel.service.impl.RelationServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ComicsServiceImplTest {
    private ComicsRepository repository;
    private RelationService relationService;
    private EntityService<ComicsDto, ComicsDtoWithCharacters> service;

    @BeforeEach
    void init() {
        repository = mock(ComicsRepository.class);
        relationService = mock(RelationServiceImpl.class);
        service = new ComicsServiceImpl(repository, relationService);
    }

    @Test
    void createCharacterWithEmptyCharactersList() {
        UUID id = UUID.randomUUID();

        ComicsEntity entity = new ComicsEntity();
        entity.setId(id);
        entity.setCharacters(Collections.emptyList());

        ComicsDtoWithCharacters dto = new ComicsDtoWithCharacters();
        dto.setCharacters(Collections.emptyList());

        when(repository.save(any(ComicsEntity.class)))
                .thenReturn(entity);

        assertEquals(id, service.save(dto).getId());
    }

    @Test
    void createCharacterWithCharactersList() {
        UUID id = UUID.randomUUID();

        ComicsEntity entity = new ComicsEntity();
        entity.setId(id);
        entity.setCharacters(Collections.emptyList());

        List<CharacterDto> characterDtos = List.of(new CharacterDto());

        ComicsDtoWithCharacters dto = new ComicsDtoWithCharacters();
        dto.setCharacters(characterDtos);

        when(repository.save(any(ComicsEntity.class)))
                .thenReturn(entity);
        when(relationService.bindCharacterAndComicsById(any(UUID.class),any(UUID.class)))
                .thenReturn(true);

        assertEquals(id, service.save(dto).getId());
    }

    @Test
    void save() {
        UUID id = UUID.randomUUID();

        ComicsEntity entity = new ComicsEntity();
        entity.setId(id);
        entity.setCharacters(Collections.emptyList());

        ComicsDtoWithCharacters dto = new ComicsDtoWithCharacters();
        dto.setId(id);
        dto.setCharacters(Collections.emptyList());

        when(repository.save(any(ComicsEntity.class)))
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
        when(repository.findAllByTitleContainsIgnoreCase(anyString()))
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
        ComicsEntity entity = new ComicsEntity();
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

        assertThrows(ComicsNotFoundException.class, () -> service.getByIdWithoutCollection(id));
    }

    @Test
    void whenGetByIdWithCollectionThenReturnDto() {
        UUID id = UUID.randomUUID();
        ComicsEntity entity = new ComicsEntity();
        entity.setId(id);
        entity.setCharacters(Collections.emptyList());
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.of(entity));

        assertEquals(id, service.getByIdWithCollection(id).getId());
    }

    @Test
    void whenGetByIdWitHCollectionThenThrowException() {
        UUID id = UUID.randomUUID();
        when(repository.findById(any(UUID.class)))
                .thenReturn(Optional.empty());

        assertThrows(ComicsNotFoundException.class, () -> service.getByIdWithCollection(id));
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