package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ComicsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private String image;
    //TODO: подумать, про реализацию списка героев комикса
    @ManyToMany
    @JoinTable (name="character_comics",
            joinColumns=@JoinColumn (name="comics_entity_id"),
            inverseJoinColumns=@JoinColumn(name="character_entity_id"))
    private List<CharacterEntity> characters;
}
