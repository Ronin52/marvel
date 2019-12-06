package ru.ronin52.marvel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ronin52.marvel.entity.ComicsEntity;
import ru.ronin52.marvel.repository.ComicsRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComicsService {
    private final ComicsRepository repository;

    public ComicsEntity save(ComicsEntity entity){
        return repository.save(entity);
    }
}
