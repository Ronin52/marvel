package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ronin52.marvel.dto.ComicsDto;
import ru.ronin52.marvel.dto.ComicsDtoWithCharacters;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(exclude = "characters")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comics")
public class ComicsEntity {
    @Id
    private UUID id;
    private String title;
    private String description;
    private String image;
    @ManyToMany(mappedBy = "comics")
    private Collection<CharacterEntity> characters;

    public ComicsEntity(UUID id, String title, String description, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public static ComicsEntity from(ComicsDto dto) {
        return new ComicsEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getImage()
        );
    }

    public static ComicsEntity from(ComicsDtoWithCharacters dto) {
        return new ComicsEntity(
                dto.getId(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getImage(),
                dto.getCharacters().stream()
                        .map(CharacterEntity::from)
                        .collect(Collectors.toList())
        );
    }
}
