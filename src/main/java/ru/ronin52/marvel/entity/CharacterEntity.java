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
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String image;
    //TODO: Подумать про целесообразность хранения id комиксов с героем
    @ManyToMany
    @JoinTable (name="character_comics",
            joinColumns=@JoinColumn (name="character_entity_id"),
            inverseJoinColumns=@JoinColumn(name="comics_entity_id"))
    private List<ComicsEntity> comics;
}
