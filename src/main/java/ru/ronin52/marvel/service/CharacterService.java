package ru.ronin52.marvel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.CharacterDtoWithComics;
import ru.ronin52.marvel.dto.CharacterSaveDto;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.exception.CharacterNotFoundException;
import ru.ronin52.marvel.repository.CharacterRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository repository;

    public CharacterDto save(CharacterSaveDto dto) {
        return CharacterDto.from(repository.save(CharacterEntity.from(dto)));
    }

    public CharacterDto getCharacterByIdWithoutComics(UUID id) {
        return CharacterDto.from(repository.findById(id)
                .orElseThrow(CharacterNotFoundException::new));
    }

    public CharacterDtoWithComics getCharacterByIdWithComics(UUID id) {
        return CharacterDtoWithComics.from(repository.findById(id)
                .orElseThrow(CharacterNotFoundException::new));
    }

    public List<CharacterDto> getAll() {
        return repository.findAll().stream()
                .map(CharacterDto::from)
                .collect(Collectors.toList());
    }




}
