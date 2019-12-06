package ru.ronin52.marvel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageEntity {
    private int id;
    private String name;
    private String extention;
}
