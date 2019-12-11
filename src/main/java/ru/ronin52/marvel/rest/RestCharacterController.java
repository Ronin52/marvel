package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.CharacterDtoWithComics;
import ru.ronin52.marvel.dto.CharacterSaveDto;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.service.EntityService;
import ru.ronin52.marvel.service.RelationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class RestCharacterController {
    private final EntityService<CharacterDto, CharacterDtoWithComics, CharacterSaveDto> characterService;
    private final RelationService relationService;

    @PostMapping("/save")
    public CharacterDto save(@RequestBody CharacterSaveDto dto) {
        return characterService.save(dto);
    }

    @PostMapping("/bind/{characterId}")
    public void bindComics(@PathVariable UUID characterId, @RequestBody List<ComicsDto> dtoList) {
        for (ComicsDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(characterId, dto.getId());
        }
    }

    @GetMapping
    public List<CharacterDto> getAll() {
        return characterService.getAll();
    }

    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable UUID id) {
        return characterService.getByIdWithoutCollection(id);
    }

    @GetMapping("/{id}/comics")
    public CharacterDtoWithComics getComics(@PathVariable UUID id) {
        return characterService.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/{id}")
    public void removeById(@PathVariable UUID id) {
        characterService.removeById(id);
    }

}
