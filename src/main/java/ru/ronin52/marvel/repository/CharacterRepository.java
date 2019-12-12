package ru.ronin52.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ronin52.marvel.entity.CharacterEntity;

import java.util.List;
import java.util.UUID;

public interface CharacterRepository extends JpaRepository<CharacterEntity, UUID> {
    List<CharacterEntity> findAllByNameContainsIgnoreCase(String q);

    List<CharacterEntity> findAllByDescriptionContainsIgnoreCase(String q);

    @Query(value = "SELECT id, name, description, image FROM characters OFFSET (:page - 1) * (:count) LIMIT (:count)", nativeQuery = true)
    List<CharacterEntity> getPage(@Param("page") int page, @Param("count") int count);

    @Query(value = "SELECT id, name, description, image FROM characters ORDER BY name", nativeQuery = true)
    List<CharacterEntity> getSortedByName();
}
