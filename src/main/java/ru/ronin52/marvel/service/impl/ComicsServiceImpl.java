package ru.ronin52.marvel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.ComicsRepository;
import ru.ronin52.marvel.service.EntityService;
import ru.ronin52.marvel.service.RelationService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComicsServiceImpl implements EntityService<ComicsDto, ComicsDtoWithCharacters> {
    private final ComicsRepository repository;
    private final RelationService relationService;

    @Override
    public ComicsDtoWithCharacters save(ComicsDtoWithCharacters dto) {
        if(dto.getId() == null) {
            dto.setId(UUID.randomUUID());
            ComicsDtoWithCharacters saved = ComicsDtoWithCharacters.from(repository.save(ComicsEntity.from(dto)));
            if (!dto.getCharacters().isEmpty()){
                for (CharacterDto dtoCharacter : dto.getCharacters()) {
                    relationService.bindCharacterAndComicsById(dtoCharacter.getId(),saved.getId());
                }
            }
            return saved;
        }
        return ComicsDtoWithCharacters.from(repository.save(ComicsEntity.from(dto)));
    }

    @Override
    public List<ComicsDto> findByName(String q) {
        return repository.findAllByTitleContainsIgnoreCase(q).stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicsDto> getPage(int page, int count) {
        return repository.getPage(page, count).stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicsDto> getSortedByName() {
        return repository.getSortedByName().stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ComicsDto> findByDescription(String q) {
        return repository.findAllByDescriptionContainsIgnoreCase(q).stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public ComicsDto getByIdWithoutCollection(UUID id) {
        return ComicsDto.from(repository.findById(id).orElseThrow(ComicsNotFoundException::new));
    }

    @Override
    public ComicsDtoWithCharacters getByIdWithCollection(UUID id) {
        return ComicsDtoWithCharacters.from(repository.findById(id).orElseThrow(ComicsNotFoundException::new));
    }

    @Override
    public List<ComicsDto> getAll() {
        return repository.findAll().stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    @Override
    public void removeById(UUID id) {
        repository.deleteById(id);
    }
}
