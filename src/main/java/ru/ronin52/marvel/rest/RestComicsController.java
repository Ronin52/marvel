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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comics")
public class RestComicsController {
    private final EntityService<ComicsDto, ComicsDtoWithCharacters, ComicsSaveDto> service;
    private final RelationService relationService;
    private int elementsOnPage = 5;

    @PostMapping("/save/comics")
    public ComicsDto save(@RequestBody ComicsSaveDto dto) {
        return service.save(dto);
    }

    @PostMapping("/bind/{comicsId}")
    public void bindCharacters(@PathVariable UUID comicsId, @RequestBody List<CharacterDto> dtoList) {
        for (CharacterDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(dto.getId(), comicsId);
        }
    }

    @GetMapping
    public List<ComicsDto> getAll() {
        return service.getAll();
    }

    @GetMapping(params = {"page", "count"})
    public List<ComicsDto> getPage(@RequestParam int page, @RequestParam int count) {
        return service.getPage(page, count);
    }

    @GetMapping(params = "title")
    public List<ComicsDto> searchByTitle(@RequestParam String title) {
        return service.findByName(title);
    }

    @GetMapping(params = "description")
    public List<ComicsDto> searchByDescription(@RequestParam String description) {
        return service.findByDescription(description);
    }

    @GetMapping(params = {"title", "description"})
    public List<ComicsDto> searchByTitleAndDescription(@RequestParam String title, @RequestParam String description) {
        return Stream.concat(service.findByName(title).stream(), service.findByDescription(description).stream())
                .distinct()
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ComicsDto getById(@PathVariable UUID id) {
        return service.getByIdWithoutCollection(id);
    }

    @GetMapping("/{id}/characters")
    public ComicsDtoWithCharacters getCharacters(@PathVariable UUID id) {
        return service.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/comics/{id}")
    public void removeById(@PathVariable UUID id) {
        service.removeById(id);
    }
}
