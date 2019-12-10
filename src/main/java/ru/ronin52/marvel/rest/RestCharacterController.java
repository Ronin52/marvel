package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.dto.CharacterDto;
import ru.ronin52.marvel.dto.CharacterDtoWithComics;
import ru.ronin52.marvel.service.CharacterService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
public class RestCharacterController {
    private final CharacterService characterService;

    @GetMapping
    public List<CharacterDto> getAll(){
        return characterService.getAll();
    }

    @GetMapping("/{id}")
    public CharacterDto getById(@PathVariable UUID id) {
        return characterService.getByIdWithoutComics(id);
    }

    @GetMapping("/{id}/comics")
    public CharacterDtoWithComics getComics(@PathVariable UUID id) {
        return characterService.getByIdWithComics(id);
    }

}
