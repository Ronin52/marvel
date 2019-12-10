package ru.ronin52.marvel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.CharacterNotFoundException;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationService {
    private final CharacterRepository characterRepository;
    private final ComicsRepository comicsRepository;

    public void bindCharacterAndComicsById(UUID characterId, UUID comicsId) {
        CharacterEntity character = characterRepository.findById(characterId).orElseThrow(CharacterNotFoundException::new);
        ComicsEntity comicsEntity = comicsRepository.findById(comicsId).orElseThrow(ComicsNotFoundException::new);

        Collection<ComicsEntity> comics = character.getComics();
        comics.add(comicsEntity);
        character.setComics(comics);

        Collection<CharacterEntity> characters = comicsEntity.getCharacters();
        characters.add(character);
        comicsEntity.setCharacters(characters);

        characterRepository.save(character);
        comicsRepository.save(comicsEntity);
    }
}
