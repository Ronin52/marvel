package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ronin52.marvel.dto.CharacterSaveDto;
import ru.ronin52.marvel.dto.ComicsSaveDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

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

    public static ComicsEntity from(ComicsSaveDto dto) {
        return new ComicsEntity(
                UUID.randomUUID(),
                dto.getTitle(),
                dto.getDescription(),
                dto.getImage(),
                Collections.emptyList()
        );
    }
}
