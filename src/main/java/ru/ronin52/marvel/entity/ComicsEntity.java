package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Data
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
    private Set<CharacterEntity> characters;
    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", description='" + description + "'" +
                ", characters=[" + characterToString(characters) +"]" +
                "}";
    }

    private String characterToString(Set<CharacterEntity> characters) {
        final StringBuilder builder = new StringBuilder();
        for (CharacterEntity characterEntity : characters) {
            builder.append("{id=")
                    .append(characterEntity.getId())
                    .append(", name=")
                    .append(characterEntity.getName())
                    .append("}");
        }
        return builder.toString();
    }
}
