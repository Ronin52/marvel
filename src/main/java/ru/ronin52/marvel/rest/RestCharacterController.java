package ru.ronin52.marvel.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.CharacterDtoWithComics;
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
    private final EntityService<CharacterDto, CharacterDtoWithComics> service;
    private final RelationService relationService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterDtoWithComics save(@RequestBody CharacterDtoWithComics characterDtoWithComics) {
        return service.save(characterDtoWithComics);
    }

    @PostMapping("/bind/:{characterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindComics(@PathVariable UUID characterId, @RequestBody List<ComicsDto> comicsDtoList) {
        for (ComicsDto dto : comicsDtoList) {
            relationService.bindCharacterAndComicsById(characterId, dto.getId());
        }
    }

    @GetMapping
    public List<CharacterDto> getAll() {
        return service.getAll();
    }


    @GetMapping(value = "/page", params = {"p", "count"})
    public List<CharacterDto> getPage(@RequestParam int p, @RequestParam int count) {
        return service.getPage(p, count);
    }

    @GetMapping(value = "/filter", params = "f")
    public List<CharacterDto> getSortedByName(@RequestParam String f) {
        if (f.equals("name")) {
            return service.getSortedByName();
        }
        return service.getAll();
    }

    @GetMapping(value = "/search", params = {"field", "name", "description"})
    public List<CharacterDto> searchByNameAndDescription(@RequestParam String field, @RequestParam String name, @RequestParam String description) {
        if (field.equals("name")) {
            return service.findByName(name);
        }
        if (field.equals("description")) {
            return service.findByDescription(description);
        }
        if (field.equals("all")) {
            return Stream.concat(service.findByName(name).stream(), service.findByDescription(description).stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        return service.getAll();
    }

    @GetMapping("/:{id}")
    public CharacterDto getById(@PathVariable UUID id) {
        return service.getByIdWithoutCollection(id);
    }

    @GetMapping("/:{id}/comics")
    public CharacterDtoWithComics getComics(@PathVariable UUID id) {
        return service.getByIdWithCollection(id);
    }

    @DeleteMapping("/remove/:{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable UUID id) {
        service.removeById(id);
    }

}
