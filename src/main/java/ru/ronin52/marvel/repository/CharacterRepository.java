package ru.ronin52.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ronin52.marvel.entity.CharacterEntity;

import java.util.UUID;

public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {
}
