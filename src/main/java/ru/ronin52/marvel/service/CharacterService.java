package ru.ronin52.marvel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.entity.CharacterEntity;
import ru.ronin52.marvel.repository.CharacterRepository;

@Service
@RequiredArgsConstructor
public class CharacterService {
    private final CharacterRepository repository;

    public void save(){
        repository.save(new CharacterEntity(0,"","","",null));
    }
}
