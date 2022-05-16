package com.example.guild.repository.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Company {
    @Id
    long id;
    String name;
}
