package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import ru.ronin52.marvel.dto.*;
import ru.ronin52.marvel.service.CharacterService;
import ru.ronin52.marvel.service.ComicsService;
import ru.ronin52.marvel.service.RelationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/editor")
public class RestEditorController {
    private final CharacterService characterService;
    private final ComicsService comicsService;
    private final RelationService relationService;

    @PostMapping("/save/character")
    public CharacterDto saveCharacter(@RequestBody CharacterSaveDto dto) {
        return characterService.save(dto);
    }

    @PostMapping("/save/comics")
    public ComicsDto saveComics(@RequestBody ComicsSaveDto dto) {
        return comicsService.save(dto);
    }

    @PostMapping("/bind/character/{id}")
    public void bindCharacterAndComics(@PathVariable UUID id, @RequestBody List<ComicsDto> dtoList) {
        for (ComicsDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(id,dto.getId());
        }
    }

    @PostMapping("/bind/comics/{id}")
    public void bindComicsAndCharacters(@PathVariable UUID id, @RequestBody List<CharacterDto> dtoList) {
        for (CharacterDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(dto.getId(),id);
        }
    }

    @DeleteMapping("/remove/character/{id}")
    public void removeCharacterById(@PathVariable UUID id){
        characterService.removeById(id);
    }

    @DeleteMapping("/remove/comics/{id}")
    public void removeComicsById(@PathVariable UUID id){
        comicsService.removeById(id);
    }
}
