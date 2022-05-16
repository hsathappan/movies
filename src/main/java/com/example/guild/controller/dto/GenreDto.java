package com.example.guild.controller.dto;

import lombok.Value;

@Value
public class GenreDto {
    long id;
    String name;
    int year;
    double popularity;
}
