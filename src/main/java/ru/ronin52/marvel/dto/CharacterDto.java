package ru.ronin52.marvel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.ronin52.marvel.entity.CharacterEntity;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDto {
    private UUID id;
    private String name;
    private String description;
    private String image;

    public static CharacterDto from(CharacterEntity entity) {
        return new CharacterDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getImage()
        );
    }
}
