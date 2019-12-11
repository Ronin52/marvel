package ru.ronin52.marvel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;
import ru.ronin52.marvel.dto.ComicsSaveDto;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComicsServiceImpl implements EntityService<ComicsDto, ComicsDtoWithCharacters, ComicsSaveDto> {
    private final ComicsRepository repository;

    public ComicsDto save(ComicsSaveDto dto) {
        return ComicsDto.from(repository.save(ComicsEntity.from(dto)));
    }

    public ComicsDto getByIdWithoutCollection(UUID id) {
        return ComicsDto.from(repository.findById(id).orElseThrow(ComicsNotFoundException::new));
    }

    public ComicsDtoWithCharacters getByIdWithCollection(UUID id) {
        return ComicsDtoWithCharacters.from(repository.findById(id).orElseThrow(ComicsNotFoundException::new));
    }

    public List<ComicsDto> getAll() {
        return repository.findAll().stream()
                .map(ComicsDto::from)
                .collect(Collectors.toList());
    }

    public void removeById(UUID id) {
        repository.deleteById(id);
    }
}
