package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/save/comics")
    @ResponseStatus(HttpStatus.CREATED)
    public ComicsDto save(@RequestBody ComicsSaveDto dto) {
        return service.save(dto);
    }

    @PostMapping("/bind/:{comicsId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindCharacters(@PathVariable UUID comicsId, @RequestBody List<CharacterDto> dtoList) {
        for (CharacterDto dto : dtoList) {
            relationService.bindCharacterAndComicsById(dto.getId(), comicsId);
        }
    }

    @GetMapping
    public List<ComicsDto> getAll() {
        return service.getAll();
    }

    @GetMapping(value = "/page", params = {"p", "count"})
    public List<ComicsDto> getPage(@RequestParam int p, @RequestParam int count) {
        return service.getPage(p, count);
    }

    @GetMapping(value = "/filter", params = "f")
    public List<ComicsDto> getSortedByName(@RequestParam String f) {
        if (f.equals("title")) {
            return service.getSortedByName();
        }
        return service.getAll();
    }

    @GetMapping(value = "/search", params = {"field", "name", "description"})
    public List<ComicsDto> searchByTitleAndDescription(@RequestParam String field, @RequestParam String title, @RequestParam String description) {
        if (field.equals("title")) {
            return service.findByName(title);
        }
        if (field.equals("description")) {
            return service.findByDescription(description);
        }
        if (field.equals("all")) {
            return Stream.concat(service.findByName(title).stream(), service.findByDescription(description).stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        return service.getAll();
    }

    @GetMapping("/:{id}")
    public ComicsDto getById(@PathVariable UUID id) {
        return service.getByIdWithoutCollection(id);
    }

    @GetMapping("/:{id}/characters")
    public ComicsDtoWithCharacters getCharacters(@PathVariable UUID id) {
        return service.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/comics/:{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable UUID id) {
        service.removeById(id);
    }
}
