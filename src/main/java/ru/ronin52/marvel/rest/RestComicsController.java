package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;
import ru.ronin52.marvel.dto.ComicsSaveDto;
import ru.ronin52.marvel.service.EntityService;
import ru.ronin52.marvel.service.RelationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comics")
public class RestComicsController {
    private final EntityService<ComicsDto, ComicsDtoWithCharacters, ComicsSaveDto> comicsService;
    private final RelationService relationService;

    @PostMapping("/save/comics")
    public ComicsDto save(@RequestBody ComicsSaveDto dto) {
        return comicsService.save(dto);
    }

    @PostMapping("/bind/{comicsId}")
    public void bindCharacters(@PathVariable UUID comicsId, @RequestBody List<CharacterDto> dtoList) {
        for (CharacterDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(dto.getId(), comicsId);
        }
    }

    @GetMapping
    public List<ComicsDto> getAll() {
        return comicsService.getAll();
    }

    @GetMapping("/{id}")
    public ComicsDto getById(@PathVariable UUID id) {
        return comicsService.getByIdWithoutCollection(id);
    }

    @GetMapping("/{id}/characters")
    public ComicsDtoWithCharacters getCharacters(@PathVariable UUID id) {
        return comicsService.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/comics/{id}")
    public void removeById(@PathVariable UUID id) {
        comicsService.removeById(id);
    }
}
