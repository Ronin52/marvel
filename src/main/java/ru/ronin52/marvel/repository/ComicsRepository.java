package ru.ronin52.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ronin52.marvel.entity.ComicsEntity;

import java.util.UUID;

public interface ComicsRepository extends JpaRepository<ComicsEntity, UUID> {
}
