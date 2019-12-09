package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.dto.*;
import ru.ronin52.marvel.service.CharacterService;
import ru.ronin52.marvel.service.ComicsService;
import ru.ronin52.marvel.service.RelationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class RestCharacterController {
    private final CharacterService characterService;
    private final ComicsService comicsService;
    private final RelationService relationService;

    @GetMapping("/character")
    public CharacterDtoWithComics characters(){
        CharacterDto characterDto = characterService.save(new CharacterSaveDto("America 1", "", ""));
        ComicsDto comicsDto1 = comicsService.save(new ComicsSaveDto("Infinity War", "", ""));
        ComicsDto comicsDto2 = comicsService.save(new ComicsSaveDto("Infinity War 2", "", ""));
        relationService.BindCharacterAndComicsById(characterDto.getId(),comicsDto1.getId());
        relationService.BindCharacterAndComicsById(characterDto.getId(),comicsDto2.getId());
        return characterService.getCharacterByIdWithComics(characterDto.getId());
    }
    @GetMapping("/comics")
    public ComicsDtoWithCharacters comics(){
        CharacterDto characterDto1 = characterService.save(new CharacterSaveDto("America 2", "", ""));
        CharacterDto characterDto2 = characterService.save(new CharacterSaveDto("America 3", "", ""));
        ComicsDto comicsDto = comicsService.save(new ComicsSaveDto("Infinity War 3", "", ""));
        relationService.BindCharacterAndComicsById(characterDto1.getId(),comicsDto.getId());
        relationService.BindCharacterAndComicsById(characterDto2.getId(),comicsDto.getId());
        return comicsService.getComicsByIdWithCharacters(comicsDto.getId());
    }

    @GetMapping("/allcharacters")
    public List<CharacterDto> getAllCharacters(){
        return characterService.getAll();
    }

    @GetMapping("allcomics")
    public List<ComicsDto> getAllComics() {
        return comicsService.getAll();
    }
}
