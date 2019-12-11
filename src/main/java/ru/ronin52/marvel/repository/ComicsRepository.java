package ru.ronin52.marvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ronin52.marvel.entity.ComicsEntity;

import java.util.List;
import java.util.UUID;

public interface ComicsRepository extends JpaRepository<ComicsEntity, UUID> {
    List<ComicsEntity> findAllByTitleLikeIgnoreCase(String q);
    List<ComicsEntity> findAllByDescriptionLikeIgnoreCase(String q);

    @Query(value = "SELECT id, title, description, image FROM comics OFFSET (:page - 1) * (:count) LIMIT (:count)",nativeQuery = true)
    List<ComicsEntity> getPage(@Param("page") int page,@Param("count") int elements);
}
