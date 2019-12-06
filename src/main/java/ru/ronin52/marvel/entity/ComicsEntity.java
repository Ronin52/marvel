package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComicsEntity {
    private int id;
    private String title;
    private String description;
    private ImageEntity image;
    //TODO: подумать, про реализацию списка героев комикса
}
