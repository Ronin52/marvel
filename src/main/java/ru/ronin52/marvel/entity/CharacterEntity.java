package ru.ronin52.marvel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
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
    @JsonIgnore
    @JoinTable(name = "character_comics",
            joinColumns = @JoinColumn(name = "character_id"),
            inverseJoinColumns = @JoinColumn(name = "comics_id"))
    private Set<ComicsEntity> comics;
    @Override
    public String toString() {
        return "CharacterEntity{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", description='" + description + "'" +
                ", comics=[" + comicsTostring(comics) +"]" +
                "}";
    }

    private String comicsTostring(Set<ComicsEntity> comics) {
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
