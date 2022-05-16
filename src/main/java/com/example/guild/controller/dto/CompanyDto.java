package com.example.guild.controller.dto;

import lombok.Value;

@Value
public class CompanyDto {
    long id;
    String name;
    int year;
    long budget;
    long revenue;
}
