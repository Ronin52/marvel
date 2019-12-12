package ru.ronin52.marvel.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.exception.CharacterNotFoundException;
import ru.ronin52.marvel.exception.ComicsNotFoundException;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.repository.ComicsRepository;
import ru.ronin52.marvel.service.RelationService;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {
    private final CharacterRepository characterRepository;
    private final ComicsRepository comicsRepository;

    @Override
    public boolean bindCharacterAndComicsById(UUID characterId, UUID comicsId) {
        CharacterEntity characterEntity = characterRepository.findById(characterId).orElseThrow(CharacterNotFoundException::new);
        ComicsEntity comicsEntity = comicsRepository.findById(comicsId).orElseThrow(ComicsNotFoundException::new);

        Collection<ComicsEntity> comics = characterEntity.getComics();
        if (comics.contains(comicsEntity)) {
            return false;
        }
        comics.add(comicsEntity);
        characterEntity.setComics(comics);

        Collection<CharacterEntity> characters = comicsEntity.getCharacters();
        if (characters.contains(characterEntity)) {
            return false;
        }
        characters.add(characterEntity);
        comicsEntity.setCharacters(characters);

        characterRepository.save(characterEntity);
        comicsRepository.save(comicsEntity);
        return true;
    }
}
