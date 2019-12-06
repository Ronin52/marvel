package ru.ronin52.marvel.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ronin52.marvel.service.CharacterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class RestCharacterController {
    private final CharacterService service;

    @GetMapping
    public void save(){
        service.save();
    }
}
