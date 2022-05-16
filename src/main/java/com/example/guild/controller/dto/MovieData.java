package com.example.guild.controller.dto;

import com.example.guild.repository.entity.Company;
import com.example.guild.repository.entity.Genre;
import com.opencsv.bean.CsvBindByName;
import lombok.Data;

import java.util.List;

@Data
public class MovieData {
    @CsvBindByName
    long id;
    @CsvBindByName
    long budget;
    @CsvBindByName
    String genres;
    @CsvBindByName
    double popularity;
    @CsvBindByName
    String production_companies;
    @CsvBindByName
    String release_date;
    @CsvBindByName
    long revenue;
    @CsvBindByName
    String title;
}
