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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class RestCharacterController {
    private final EntityService<CharacterDto, CharacterDtoWithComics, CharacterSaveDto> service;
    private final RelationService relationService;
    private int elementsOnPage = 5;

    @PostMapping("/save")
    public CharacterDto save(@RequestBody CharacterSaveDto dto) {
        return service.save(dto);
    }

    @PostMapping("/bind/{characterId}")
    public void bindComics(@PathVariable UUID characterId, @RequestBody List<ComicsDto> dtoList) {
        for (ComicsDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(characterId, dto.getId());
        }
    }

    @GetMapping
    public List<CharacterDto> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"page", "count"})
    public List<CharacterDto> getPage(@RequestParam int page, @RequestParam int count) {
        return service.getPage(page, count);
    }

    @GetMapping(params = "name")
    public List<CharacterDto> searchByName(@RequestParam String name) {
        return service.findByName(name);
    }

    @GetMapping(params = "description")
    public List<CharacterDto> searchByDescription(@RequestParam String description) {
        return service.findByDescription(description);
    }

    @GetMapping(params = {"name", "description"})
    public List<CharacterDto> searchByNameAndDescription(@RequestParam String name, @RequestParam String description) {
        return Stream.concat(service.findByName(name).stream(), service.findByDescription(description).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable UUID id) {
        return service.getByIdWithoutCollection(id);
    }

    @GetMapping("/{id}/comics")
    public CharacterDtoWithComics getComics(@PathVariable UUID id) {
        return service.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/{id}")
    public void removeById(@PathVariable UUID id) {
        service.removeById(id);
    }

}
