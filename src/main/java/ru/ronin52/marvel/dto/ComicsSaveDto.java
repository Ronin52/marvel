package ru.ronin52.marvel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComicsSaveDto {
    private String title;
    private String description;
    private String image;
}
