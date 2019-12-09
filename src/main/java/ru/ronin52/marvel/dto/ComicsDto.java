package ru.ronin52.marvel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ronin52.marvel.entity.ComicsEntity;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComicsDto {
    private UUID id;
    private String title;
    private String description;
    private String image;

    public static ComicsDto from(ComicsEntity entity) {
        return new ComicsDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getImage()
        );
    }
}
