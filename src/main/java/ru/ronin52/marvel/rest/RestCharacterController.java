package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.repository.CharacterRepository;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.ArrayList;
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
    public void save(){
        CharacterEntity character = characterService.save(new CharacterEntity(UUID.randomUUID(), "","", new HashSet<>()));
        ComicsEntity comics = comicsService.save(new ComicsEntity(UUID.randomUUID(), "1", "", new HashSet<>()));
        ComicsEntity comics1 = comicsService.save(new ComicsEntity(UUID.randomUUID(), "2", "", new HashSet<>()));

        Set<ComicsEntity> com = character.getComics();
        com.add(comics);
        com.add(comics1);
        character.setComics(com);
        CharacterEntity save = characterService.save(character);

//        Set<CharacterEntity> cha = comics.getCharacters();
//        cha.add(character);
//        comics.setCharacters(cha);
//        comicsService.save(comics);

        System.out.println(characterService.findById(save.getId()));
        System.out.println(comicsService.findById(comics.getId()));
        System.out.println(comicsService.findById(comics1.getId()));
    }
}
