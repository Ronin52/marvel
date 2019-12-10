package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;
import ru.ronin52.marvel.service.ComicsService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comics")
public class RestComicsController {
    private final ComicsService comicsService;

    @GetMapping
    public List<ComicsDto> getAll(){
        return comicsService.getAll();
    }

    @GetMapping("/{id}")
    public ComicsDto getById(@PathVariable UUID id) {
        return comicsService.getByIdWithoutCharacters(id);
    }

    @GetMapping("/{id}/characters")
    public ComicsDtoWithCharacters getCharacters(@PathVariable UUID id) {
        return comicsService.getByIdWithCharacters(id);
    }
}
