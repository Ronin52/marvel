package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.ronin52.marvel.dto.CharacterSaveDto;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = "comics")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "characters")
public class CharacterEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
    private String image;
    @ManyToMany()
    @JoinTable(name = "character_comics",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "comics_id"))
    private Collection<ComicsEntity> comics;

    public static CharacterEntity from(CharacterSaveDto dto) {
        return new CharacterEntity(
                UUID.randomUUID(),
                dto.getName(),
                dto.getDescription(),
                dto.getImage(),
                Collections.emptyList()
        );
    }

    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", comics=[" + comicsTostring(comics) +"]" +
                "}";
    }

    private String comicsTostring(Collection<ComicsEntity> comics) {
        final StringBuilder builder = new StringBuilder();
        for (ComicsEntity comicsEntity : comics) {
            builder.append("{id=")
                    .append(comicsEntity.getId())
                    .append(", title=")
                    .append(comicsEntity.getTitle())
                    .append("}");
        }
        return builder.toString();
    }
}
