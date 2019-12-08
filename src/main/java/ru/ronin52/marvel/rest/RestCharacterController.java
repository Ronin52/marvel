package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RestCharacterController {
    private final CharacterRepository characterService;
    private final ComicsRepository comicsService;

    @GetMapping
    public CharacterEntity save(){
        UUID c1;
        UUID cm1;
        {
            CharacterEntity character = characterService.save(new CharacterEntity(UUID.randomUUID(), "","","", new HashSet<>()));
            ComicsEntity comics = comicsService.save(new ComicsEntity(UUID.randomUUID(), "1", "","", new HashSet<>()));
            c1 = character.getId();
            cm1 = comics.getId();
        }
        {
            CharacterEntity character = characterService.findById(c1).orElseThrow(RuntimeException::new);
            ComicsEntity comicsEntity = comicsService.findById(cm1).orElseThrow(RuntimeException::new);
            Set<ComicsEntity> comics = character.getComics();
            comics.add(comicsEntity);
            character.setComics(comics);
            characterService.save(character);
        }
        {
            CharacterEntity character = characterService.findById(c1).orElseThrow(RuntimeException::new);
            ComicsEntity comicsEntity = comicsService.findById(cm1).orElseThrow(RuntimeException::new);
            Set<CharacterEntity> characters = comicsEntity.getCharacters();
            characters.add(character);
            comicsEntity.setCharacters(characters);
            comicsService.save(comicsEntity);
        }
        return characterService.findById(c1).orElseThrow(RuntimeException::new);
    }
}
